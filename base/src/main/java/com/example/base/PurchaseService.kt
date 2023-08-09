package com.example.base

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.adapty.Adapty
import com.adapty.errors.AdaptyErrorCode
import com.adapty.models.AdaptyAttributionSource
import com.adapty.models.AdaptyPaywallProduct
import com.adapty.models.AdaptyProfile
import com.adapty.models.AdaptyProfileParameters
import com.adapty.utils.AdaptyResult
import com.appsflyer.AppsFlyerLib
import com.captain.show.repository.events.AnalyticsModule
import com.captain.show.repository.events.AppsFlyerEventClient

import com.facebook.appevents.AppEventsLogger
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Singleton
class PurchaseService @Inject constructor(
    private val analyticsGateway: AnalyticsModule,
    @ApplicationContext private val context: Context,
) {

    var isSubscribed = MutableStateFlow(false)

    val amuletPurchased = MutableStateFlow(false)

    val purchaserInfo = MutableStateFlow<AdaptyProfile?>(null)

    private val coroutineScope =
        CoroutineScope(Dispatchers.Main + CoroutineExceptionHandler { _, _ -> } + SupervisorJob())

    fun configure() {
        Adapty.activate(context, AppConfig.ADAPTY_IDENTIFIER, false)
        Adapty.getProfile { result ->
            when (result) {
                is AdaptyResult.Success -> {
                    if (result.value.customerUserId != analyticsGateway.getAppsFlyerUid()) {
                        val uid = analyticsGateway.getAppsFlyerUid()
                        if (uid != null) {
                            Adapty.identify(uid) {
                                coroutineScope.launch {
                                    try {
                                        val appsFlyerId = analyticsGateway.getAppsFlyerUid()
                                        if (appsFlyerId != null) {
                                            updateUserProfile(appsFlyerId, null, null)
                                        }
                                    } catch (e: Exception) {

                                    }
                                }
                            }
                        }
                    }
                }

                is AdaptyResult.Error -> {}
            }
        }
        coroutineScope.launch {
            try {
                val appsFlyerId = analyticsGateway.getAppsFlyerUid()
                if (appsFlyerId != null) {
                    updateUserProfile(appsFlyerId, null, null)
                }
            } catch (e: Exception) {

            }
        }
        AppsFlyerEventClient.conversionState
            .filter { it != null }
            .map { it!! }
            .onEach {
                Adapty.updateAttribution(
                    it,
                    AdaptyAttributionSource.APPSFLYER,
                    AppsFlyerLib.getInstance().getAppsFlyerUID(context)
                ) { _ ->

                }
            }
            .launchIn(coroutineScope)
    }

    suspend fun purchaseProduct(activity: AppCompatActivity, product: AdaptyPaywallProduct) =
        suspendCoroutine<Unit> { cont ->
            Adapty.makePurchase(
                activity, product, null
            ) { result ->
                when (result) {
                    is AdaptyResult.Success -> {
                        val info = result.value


                        if (info != null) {
                            isSubscribed.value = info.subscriptions.values.any { it.isActive }
                            purchaserInfo.value = info
                            cont.resume(Unit)
                        } else {
                            cont.resumeWithException(CancellationException())
                        }
                    }

                    is AdaptyResult.Error -> {
                        val error = result.error
                        if (error.adaptyErrorCode == AdaptyErrorCode.BILLING_SERVICE_UNAVAILABLE || error.adaptyErrorCode == AdaptyErrorCode.BILLING_UNAVAILABLE) {
                            return@makePurchase cont.resumeWithException(
                                DeviceIsNotPreparedException(error)
                            )
                        } else {
                            cont.resumeWithException(result.error)
                        }
                    }
                }
            }
        }

    suspend fun purchaseAmulet(activity: AppCompatActivity, product: AdaptyPaywallProduct) =
        suspendCoroutine<Unit> { cont ->
            Adapty.makePurchase(
                activity, product, null
            ) { result ->
                when (result) {
                    is AdaptyResult.Success -> {
                        val info = result.value
                        if (info != null) {
                            amuletPurchased.value = info.subscriptions.values.any { it.isActive }
                            purchaserInfo.value = info
                            cont.resume(Unit)
                        } else {
                            cont.resumeWithException(CancellationException())
                        }

                    }

                    is AdaptyResult.Error -> {
                        val error = result.error
                        if (error.adaptyErrorCode == AdaptyErrorCode.BILLING_SERVICE_UNAVAILABLE || error.adaptyErrorCode == AdaptyErrorCode.BILLING_UNAVAILABLE) {
                            return@makePurchase cont.resumeWithException(
                                DeviceIsNotPreparedException(error)
                            )
                        } else {
                            cont.resumeWithException(result.error)
                        }
                    }
                }
            }
        }


    suspend fun getPaywall(id: String, autoLogShowing: Boolean = true) =
        suspendCoroutine<PaywallDto> { cont ->
            Adapty.getPaywall(id) { paywallResult ->
                when (paywallResult) {
                    is AdaptyResult.Success -> {
                        val adaptyPaywall = paywallResult.value
                        Adapty.getPaywallProducts(adaptyPaywall) { adaptyPaywallProducts ->
                            when (adaptyPaywallProducts) {
                                is AdaptyResult.Success -> {
                                    if (autoLogShowing) {
                                        Adapty.logShowPaywall(adaptyPaywall)
                                    }
                                    cont.resume(
                                        PaywallDto(
                                            adaptyPaywall,
                                            adaptyPaywallProducts.value
                                        )
                                    )
                                }

                                is AdaptyResult.Error -> {
                                    cont.resumeWithException(adaptyPaywallProducts.error)
                                }
                            }
                        }
                    }

                    is AdaptyResult.Error -> {
                        val error = paywallResult.error
                        if (error.adaptyErrorCode == AdaptyErrorCode.BILLING_SERVICE_UNAVAILABLE || error.adaptyErrorCode == AdaptyErrorCode.BILLING_UNAVAILABLE) {
                            cont.resumeWithException(
                                DeviceIsNotPreparedException(error)
                            )
                        } else {
                            cont.resumeWithException(error)
                        }
                    }
                }
            }
        }


    suspend fun updateUserProfile(customerUserId: String, email: String?, name: String?) =
        suspendCoroutine<Unit> { cont ->
            Adapty.updateProfile(
                AdaptyProfileParameters.Builder()
                    .apply {
                        if (email != null) {
                            withEmail(email)
                        }
                        if (name != null) {
                            val splat = name.split(" ").toMutableList()
                            withFirstName(splat[0])
                            if (splat.size > 1) {
                                splat.removeAt(0)
                                withLastName(splat.joinToString(separator = " "))
                            }
                        }
                        withAmplitudeUserId(customerUserId)
                        withMixpanelUserId(customerUserId)
                        withFacebookAnonymousId(AppEventsLogger.getAnonymousAppDeviceGUID(context))
                    }
                    .build()
            ) { result ->
                if (result != null) {
                    cont.resumeWithException(result)
                } else {
                    cont.resume(Unit)
                }
            }
        }
}