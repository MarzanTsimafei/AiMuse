package ai.art.image.generator.avatar.subscription

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.captain.show.repository.events.EventFacade
import com.captain.show.repository.events.EventPrototypeInterface
import com.example.base.PaywallDto
import com.example.base.PurchaseService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SubscriptionViewModel @Inject constructor(

    private val purchaseService: PurchaseService, private val eventService: EventFacade,
) : ViewModel() {


    val weeklySubscriptionChoosed = 0


//    override fun logEvent(event: EventPrototypeInterface) {
//        eventService.logEvent(event)
//    }

    suspend fun getSubscriptionDetails(): PaywallDto {
        return purchaseService.getPaywall("default")
    }

    suspend fun launchSubscription(
        activity: AppCompatActivity,
    ) {
        val subscriptionChoosed = getSubscriptionDetails().products[weeklySubscriptionChoosed]
        purchaseService.purchaseProduct(activity, subscriptionChoosed)
    }
}