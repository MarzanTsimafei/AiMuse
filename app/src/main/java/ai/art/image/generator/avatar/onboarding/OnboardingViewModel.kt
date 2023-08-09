package ai.art.image.generator.avatar.onboarding

import ai.art.image.generator.avatar.repo.LocaleRepository
import ai.art.image.generator.avatar.repo.LocaleViewModel
import ai.art.image.generator.avatar.repo.OnboardingNavigationState
import android.graphics.Bitmap
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.Calendar
import java.util.Optional
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(

    localeRepository: LocaleRepository
) :
    LocaleViewModel(localeRepository) {

    var isSkippedSkanning = false

    val showingSubject = BehaviorSubject.create<OnboardingPresentation>()

    fun values(): List<OnboardingPresentation> {
        return OnboardingPresentation.values().toList()
    }

    val nextSubject = PublishSubject.create<OnboardingNavigationState>()
}