package ai.art.image.generator.avatar.repo

import ai.art.image.generator.avatar.onboarding.OnboardingPresentation

sealed class OnboardingNavigationState {
    data class Force(val view: OnboardingPresentation): OnboardingNavigationState()
    object Next: OnboardingNavigationState()
}