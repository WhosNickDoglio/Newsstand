package dev.whosnickdoglio.newsstand.di

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dev.whosnickdoglio.core.di.AppScope
import dev.whosnickdoglio.core.di.IsProduction
import dev.whosnickdoglio.newsstand.BuildConfig

@Module
@ContributesTo(AppScope::class)
object IsProductionModule {

    // TODO need a way to hit prod endpoint with debug builds?
    //  Need some sort of admin panel?
    @Provides
    @IsProduction
    fun provideIsProduction(): Boolean = !BuildConfig.DEBUG
}
