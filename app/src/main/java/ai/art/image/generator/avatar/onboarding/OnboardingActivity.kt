package ai.art.image.generator.avatar.onboarding

import ai.art.image.generator.avatar.MainActivity
import ai.art.image.generator.avatar.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import ai.art.image.generator.avatar.databinding.ActivityOnboardingBinding
import ai.art.image.generator.avatar.repo.OnboardingNavigationState
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch


@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity() {

    var pref: SharedPreferences? = null
    private lateinit var binding: ActivityOnboardingBinding
    private val viewModel by viewModels<OnboardingViewModel>()
    private val disposable = CompositeDisposable()
    var countOfOpens = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pref = this.getSharedPreferences("TABLE", Context.MODE_PRIVATE)


        countOfOpens = pref?.getInt("counts", 0)!!
        pref?.edit()?.putInt("counts", countOfOpens + 1)?.apply()
        if (countOfOpens == 0) {

        }

        binding.videoView.setVideoURI(Uri.parse("android.resource://" + this.packageName + "/" + R.raw.onboarding_background))
        binding.videoView.setOnPreparedListener { mp ->
            mp.isLooping = true
            binding.videoView.start()
        }

                binding.viewPager.adapter = object : FragmentStateAdapter(this) {
                    override fun getItemCount(): Int {
                        return viewModel.values().size
                    }

                    override fun createFragment(position: Int): Fragment {
                        return viewModel.values()[position].getFragment()
                    }
                }

                binding.viewPager.registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {
                    @SuppressLint("SetTextI18n")
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        val enum = viewModel.values()[position]
                        if (savedInstanceState == null) {
                           // viewModel.logEvent(EventDataScope.OnboardingScreen.List(enum.name))
                        }
                        viewModel.showingSubject.onNext(viewModel.values()[position])
                    }
                })


        binding.viewPager.isUserInputEnabled = false

        binding.viewPager.offscreenPageLimit = 1

        binding.buttonContinue.setOnClickListener {
            if (binding.viewPager.currentItem == 5) {
                lifecycleScope.launch {
                    try {
                        if (!isActive) return@launch
                        this@OnboardingActivity.finish()
                        goNext()

                    } catch (e: Exception) { }
                      finally {
                        if (currentCoroutineContext().isActive) {
                            binding.buttonContinue.isClickable = true
                            goNext()
                        }
                    }
                }

            } else {
                goNext()
            }
        }


        disposable += viewModel.nextSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when (it) {
                    is OnboardingNavigationState.Force -> {
                        binding.viewPager.currentItem = viewModel.values().indexOf(it.view)
                    }
                    OnboardingNavigationState.Next -> {
                        goNext()
                    }
                }
            }, {})

    }

    private fun goNext() {
        if (binding.viewPager.currentItem + 1 < viewModel.values().size) {
            if (!viewModel.isSkippedSkanning) {
                binding.viewPager.currentItem = binding.viewPager.currentItem + 1
            } else {
                val value = viewModel.values()[binding.viewPager.currentItem]
                binding.viewPager.currentItem = when (value) {
                    else -> {
                        binding.viewPager.currentItem + 1
                    }
                }
            }
        } else {
            lifecycleScope.launch {
                if (this.isActive)
                pref?.edit()?.putBoolean("onboarding", true)?.apply()
                finish()
            }
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()

    }
}