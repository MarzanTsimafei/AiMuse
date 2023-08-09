package ai.art.image.generator.avatar.onboarding

import android.view.View
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.findViewTreeLifecycleOwner
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

object DelayOnLifecycle {
    fun View.delayOnLifecycle(
        durationInMillis: Long,
        dispatcher : CoroutineDispatcher = Dispatchers.Main,
        block: () -> Unit
    ): Job? = findViewTreeLifecycleOwner()?.let { lifecycleOwner ->
        lifecycleOwner.lifecycle.coroutineScope.launch(dispatcher){
            delay(durationInMillis)
            if(isActive){
                block()
            }
        }
    }
}