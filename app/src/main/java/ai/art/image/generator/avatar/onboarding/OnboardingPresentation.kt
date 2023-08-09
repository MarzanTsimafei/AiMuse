package ai.art.image.generator.avatar.onboarding

import androidx.fragment.app.Fragment

enum class OnboardingPresentation() {
    WELCOME,
    DESCRIBE,
    ANIME;

    fun getFragment(): Fragment {
        return when (this) {
            WELCOME -> WelcomeFragment()
            DESCRIBE -> DescribeFragment()
            ANIME -> AnimeFragment()
        }
    }
}