package ai.art.image.generator.avatar.repo

import android.content.Context
import androidx.annotation.ArrayRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import java.util.Locale

interface LocalizationInterface {
    fun getString(@StringRes resource: Int): String
    fun getStringArray(@ArrayRes resource: Int): Array<String>
    fun getQuantityAutoFormat(@PluralsRes resource:Int, quantity: Int): String
    fun getQuantityFormat(@PluralsRes resource:Int, quantity: Int, vararg objects: Any): String
    fun getQuantity(@PluralsRes resource:Int, quantity: Int): String
    val localizedContext: Context
    val sharedLocale: Locale
}