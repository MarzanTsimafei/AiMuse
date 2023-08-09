package ai.art.image.generator.avatar.repo

import android.content.Context
import androidx.annotation.ArrayRes
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import java.util.Locale

open class LocaleViewModel(protected val localeRepository: LocaleRepository) : ViewModel(), LocalizationInterface {

    override fun getString(@StringRes resource: Int): String {
        return localeRepository.originalContext.getString(resource)
        //return localeRepository.getString(resource)
    }

    override fun getStringArray(@ArrayRes resource: Int): Array<String> {
        return localeRepository.originalContext.resources.getStringArray(resource)
        //   return localeRepository.getStringArray(resource)
    }

    override fun getQuantityAutoFormat(resource: Int, quantity: Int): String {
        return localeRepository.getQuantityAutoFormat(resource, quantity)
    }

    override fun getQuantityFormat(resource: Int, quantity: Int, vararg objects: Any): String {
        return localeRepository.getQuantityFormat(resource, quantity, objects)
    }

    override fun getQuantity(resource: Int, quantity: Int): String {
        return localeRepository.getQuantity(resource, quantity)
    }

    override val localizedContext: Context
        get() {
            return localeRepository.localizedContext
        }

    override val sharedLocale: Locale
        get() = localeRepository.sharedLocale

}