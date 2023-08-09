package ai.art.image.generator.avatar.repo

import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.annotation.AnyThread
import androidx.annotation.ArrayRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Locale

class LocaleRepository(private val context: Context): LocalizationInterface {

    companion object {
        private const val PARAM_FOUNDATION = ":app:core:language"
        private const val PREF_NAME = "Core_App"
    }

    private var foundationLocale: String? = null
    private var foundationSharedLocale: Locale? = null
    private var localizationContextStored: Context? = null

    private val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    val localeSubject = BehaviorSubject.create<String>()

    val localeFlow = MutableStateFlow<ArtImageLanguages>(ArtImageLanguages.ENGLISH)

    @get:AnyThread
    val locale: String
        get() {
            if (foundationLocale == null)
                foundationLocale = sharedPreferences.getString(PARAM_FOUNDATION, null)
            if (foundationLocale == null) {
                val tryFindLearned = maybeDefaults()
                val resultLanguage = if (tryFindLearned == null) { "en" } else { tryFindLearned.language }
                foundationLocale = resultLanguage
                sharedPreferences.edit()
                    .putString(PARAM_FOUNDATION, resultLanguage)
                    .apply()
            }

            if (!localeSubject.hasValue() && foundationLocale != null)
            {
                localeSubject.onNext(foundationLocale!!)
                localeFlow.value = ArtImageLanguages.values().firstOrNull { it.toString() == foundationLocale } ?: ArtImageLanguages.ENGLISH
            }

            if (foundationSharedLocale == null && foundationLocale != null) foundationSharedLocale = Locale(foundationLocale!!, "")
            return foundationLocale!!
        }

    @get:AnyThread
    override val sharedLocale: Locale
        get() {
            if (foundationSharedLocale == null) {
                foundationSharedLocale = Locale(locale, "")
            }
            return foundationSharedLocale!!
        }

    private val localizationContext: Context
        get() {
            if (localizationContextStored == null) {
                localizationContextStored = LocalizableContextWrapper.wrap(context, locale)
            }
            return localizationContextStored!!
        }


    override fun getString(@StringRes resource: Int): String {
        return localizationContext.getString(resource)
    }

    override fun getStringArray(@ArrayRes resource: Int): Array<String> {
        return localizationContext.resources.getStringArray(resource)
    }

    override fun getQuantityAutoFormat(@PluralsRes resource:Int, quantity: Int): String {
        return localizationContext.resources.getQuantityString(resource, quantity, quantity)
    }

    override fun getQuantityFormat(@PluralsRes resource:Int, quantity: Int, vararg objects: Any): String {
        return localizationContext.resources.getQuantityString(resource, quantity, objects)
    }

    override fun getQuantity(@PluralsRes resource:Int, quantity: Int): String {
        return localizationContext.resources.getQuantityString(resource, quantity)
    }

    override val localizedContext: Context
        get() = getAvailableConfiguration()

    fun hasResolved(): Boolean {
        return if (maybeDefaults() != null) {
            true
        } else !(maybeDefaults() == null && !sharedPreferences.contains(PARAM_FOUNDATION))
    }

    @Suppress("DEPRECATION")
    @AnyThread
    private fun maybeDefaults(): Locale? {
        val resourcesLocale: Array<Locale?>
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val localeList = LocaleList.getDefault()
            resourcesLocale = arrayOfNulls(localeList.size())
            for (localesPointer in 0 until localeList.size()) {
                resourcesLocale[localesPointer] = localeList[localesPointer]
            }
        } else resourcesLocale = arrayOf(context.resources.configuration.locale)
        return isContainsLocale(resourcesLocale)
    }

    fun commitLocale(newFoundation: String) {
        foundationLocale = newFoundation
        foundationSharedLocale = Locale(newFoundation, "")
        localizationContextStored = LocalizableContextWrapper.wrap(context, foundationLocale!!)
        localeFlow.value = ArtImageLanguages.values().firstOrNull { it.toString() == newFoundation } ?: ArtImageLanguages.ENGLISH
        sharedPreferences.edit()
            .putString(PARAM_FOUNDATION, newFoundation)
            .apply()
        localeSubject.onNext(newFoundation)
    }

    private fun isContainsLocale(localization: Array<Locale?>): Locale? {
        for (locale in localization) {
            val language = locale?.language ?: "en"
            val applicationLocales = ArtImageLanguages.values()
            for (bucket in applicationLocales) {
                if (bucket.toString() == language) return Locale(bucket.toString())
            }
        }
        return null
    }

    val originalContext: Context
        get() {
            return context
        }

    fun createConfiguration(context: Context, language: String): Context {
        return LocalizableContextWrapper.wrap(context, language)
    }

    fun getAvailableConfiguration() : Context = localizationContext

    init {
        locale
    }
}