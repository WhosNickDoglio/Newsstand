/*
 * MIT License
 *
 *   Copyright (c) 2021 Nicholas Doglio
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
 */

package dev.whosnickdoglio.feedly.networking

import com.slack.eithernet.ApiResult
import com.slack.eithernet.ApiResultCallAdapterFactory
import com.slack.eithernet.ApiResultConverterFactory
import dagger.Lazy
import dev.whosnickdoglio.feedly.models.Collection
import dev.whosnickdoglio.feedly.models.ErrorResponse
import dev.whosnickdoglio.feedly.models.Feed
import dev.whosnickdoglio.feedly.models.params.AddFeedParam
import dev.whosnickdoglio.feedly.models.params.CreateOrUpdateCollectionParam
import dev.whosnickdoglio.feedly.models.params.UpdateBoardParam
import dev.whosnickdoglio.feedly.models.streams.Board
import dev.whosnickdoglio.feedly.models.streams.Stream
import dev.whosnickdoglio.feedly.models.streams.StreamIds
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * A [Retrofit] service for the Feedly API.
 */
@Suppress("LongParameterList", "TooManyFunctions")
interface FeedlyService {

    // <editor-fold desc="Streams endpoints">

    @GET("streams/contents")
    suspend fun getStream(
        @Query("streamId") id: String,
        @Query("count") count: Int? = null,
        @Query("ranked") ranked: String? = null,
        @Query("unreadOnly") unreadOnly: Boolean? = null,
        @Query("newerThan") newerThan: Long? = null,
        @Query("continuation") continuation: String? = null,
        @Query("showMuted") showMuted: Boolean? = null,
    ): ApiResult<Stream, ErrorResponse>

    @GET("streams/ids")
    suspend fun getStreamIds(
        @Query("streamId") id: String,
        @Query("count") count: Int? = null,
        @Query("ranked") string: String? = null,
        @Query("unreadOnly") unreadOnly: Boolean? = null,
        @Query("newerThan") newerThan: Long? = null,
        @Query("continuation") continuation: String? = null,
    ): ApiResult<StreamIds, ErrorResponse>

    // </editor-fold>

    // <editor-fold desc="Feed endpoints">
    /**
     * @param feedId: The corresponding ID for the requested feed.
     * @return A [Feed] based on the provided [feedId].
     */
    @GET("feeds/{feedId}")
    suspend fun getFeedMetadata(
        @Path("feedId") feedId: String,
    ): ApiResult<Feed, ErrorResponse>

    @POST("feeds/.mget")
    suspend fun getFeedsMetadata(
        @Body feedIds: List<String>,
    ): ApiResult<List<Feed>, ErrorResponse>

    //</editor-fold>

    //<editor-fold desc="Boards endpoints">

    @GET("boards")
    suspend fun getBoards(
        @Query("withEnterprise") withEnterprise: Boolean? = null,
    ): ApiResult<List<Board>, ErrorResponse>

    @POST("boards")
    suspend fun updateBoard(@Body update: UpdateBoardParam)

    /**
     *
     * TODO how do I do this?? info from docs
     * Send the new image as a multi-part MIME attachment with the name “cover”.
     * Accepted images are JPG, PNG and GIF. The image will be resized to
     * 840 pixels wide by 250 pixels high (recommended size).
     * https://stackoverflow.com/questions/39953457/how-to-upload-image-file-in-retrofit-2
     */
    @POST("boards/{id}")
    suspend fun updateBoardCoverPhoto(
        @Path("id") id: String,
    )

    // </editor-fold>
    // <editor-fold desc="Personal Collections endpoints">

    @GET("collections")
    suspend fun getCollections(
        @Query("withStats") withStats: Boolean? = null,
        @Query("withEnterprise") withEnterprise: Boolean? = null
    ): ApiResult<List<Collection>, ErrorResponse>

    @GET("collections/{id}")
    suspend fun getCollection(@Path("id") id: String): List<Collection>

    @POST("collections")
    suspend fun createOrUpdateCollection(
        @Body param: CreateOrUpdateCollectionParam
    ): ApiResult<List<Collection>, ErrorResponse>

    @POST("collections/{id}")
    suspend fun uploadCoverImage(
        @Path("id") id: String, // TODO how do I upload a iamge?
    ): ApiResult<List<Collection>, ErrorResponse>

    @PUT("collections/{id}/feeds")
    suspend fun addFeedToCollection(
        @Path("id") id: String,
        @Body param: AddFeedParam
    )

    // </editor-fold>

    companion object {

        private const val PROD_BASE_URL = "https://cloud.feedly.com/v3/"
        private const val SANDBOX_BASE_URL = "https://sandbox7.feedly.com/v3/"

        internal fun create(
            okHttpClient: Lazy<OkHttpClient>,
            isProd: Boolean = true
        ): FeedlyService = Retrofit.Builder()
            .baseUrl(if (isProd) PROD_BASE_URL else SANDBOX_BASE_URL)
            .callFactory { request -> okHttpClient.get().newCall(request) }
            .addConverterFactory(ApiResultConverterFactory)
            .addCallAdapterFactory(ApiResultCallAdapterFactory)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }
}
