package ai.art.image.generator.avatar

import android.app.Application
import android.content.Context
import com.appsflyer.AppsFlyerLib
import com.captain.show.repository.events.AnalyticsModule
import com.example.base.PurchaseService
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import dagger.hilt.android.HiltAndroidApp
import io.branch.referral.Branch
import javax.inject.Inject


@HiltAndroidApp
class ImageApplication : Application(){

    @Inject
    lateinit var analyticsGateway: AnalyticsModule

    @Inject
    lateinit var purchaseService: PurchaseService

    override fun onCreate() {
        super.onCreate()

        Companion.applicationContext = this

        purchaseService.configure()

        analyticsGateway.initializeSdks(this)

        Firebase.initialize(this)


        AppsFlyerLib.getInstance().start(this)
    }

    companion object {
        lateinit var applicationContext: Context
    }

}