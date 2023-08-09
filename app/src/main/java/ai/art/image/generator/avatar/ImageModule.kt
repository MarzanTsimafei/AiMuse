package ai.art.image.generator.avatar

import ai.art.image.generator.avatar.repo.LocaleRepository
import android.content.Context
import com.captain.show.repository.events.AnalyticsModule
import com.captain.show.repository.events.EventFacade
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ImageModule {

    @Provides
    @Singleton
    fun providesLocaleRepository(@ApplicationContext context: Context): LocaleRepository {
        return LocaleRepository(context)
    }


    @Provides
    @Singleton
    fun providesAnalyticsGateway(@ApplicationContext context: Context): AnalyticsModule {
        return AnalyticsModule.withBuilder(context)
            .setWillRejecting(false)
            .addFirebaseLogger()
            .addFacebook(false)
            .addAppsFlyer("7U86Mpebp9suzCJnzwXCcL")
            .addFlurry("TZ3CRXQ7Z4J8FSP8N74K")
            .addMixPanel(
                "a4a3fcffc56eb3a9f2a092e2d8817bba",
                AnalyticsModule.getUserId(context, "7U86Mpebp9suzCJnzwXCcL")
            )
            .build()
    }


    @Provides
    @Singleton
    fun providesEventController(analyticsGateway: AnalyticsModule): EventFacade {
        return analyticsGateway.getEventFacade()
    }
}

