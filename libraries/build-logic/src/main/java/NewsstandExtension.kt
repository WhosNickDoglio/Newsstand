import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import javax.inject.Inject

/**
 * A Gradle plugin extension used for customizing configuration of modules using `newsstand`
 * convention plugins.
 */
open class NewsstandExtension @Inject constructor(objects: ObjectFactory) {

    /**
     * Determines if Jetpack Compose is enabled for this Android library module.  Defaults to
     * `true`.
     *
     * **Note:** This [Property] is only accounted for when the current module applies the
     * `newsstand-library` plugin.
     */
    val isComposeEnabled: Property<Boolean> = objects.property(Boolean::class.java)
        .convention(true)

    /**
     * Determines if BuildConfigs are generated this Android library module.  Defaults to
     * `false`.
     *
     * **Note:** This [Property] is only accounted for when the current module applies the
     * `newsstand-library` plugin.
     */
    val isBuildConfigEnabled: Property<Boolean> = objects.property(Boolean::class.java)
        .convention(false)

    /**
     * Can be used to supply the compiler with specific arguments for this module.  Defaults to an
     * empty list.
     *
     * **Note:** This [Property] is only accounted for when the current module applies the
     * `newsstand-library` plugin.
     */
    val compilerArguments: ListProperty<String> = objects.listProperty(String::class.java)
        .convention(emptyList())
}
