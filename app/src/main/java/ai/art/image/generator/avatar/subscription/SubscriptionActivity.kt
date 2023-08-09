package ai.art.image.generator.avatar.subscription

import ai.art.image.generator.avatar.R
import ai.art.image.generator.avatar.databinding.ActivitySubscriptionBinding
import android.annotation.SuppressLint
import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.net.Uri
import android.os.Handler
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

@AndroidEntryPoint
class SubscriptionActivity : AppCompatActivity() {

    private val viewModel by viewModels<SubscriptionViewModel>()
    private lateinit var binding: ActivitySubscriptionBinding
    private val handler = Handler()
    private val swipeTimer = Timer()
    var pref: SharedPreferences? = null


    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubscriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pref = this.getSharedPreferences("TABLE", Context.MODE_PRIVATE)

        pref?.edit()?.putBoolean("afterOnboarding", false)?.apply()


        lifecycleScope.launch {
            try {
                val details = viewModel.getSubscriptionDetails()

                if (!isActive) return@launch
                val weekly = details.products[0]
                if(weekly.freeTrialPeriod != null){
                    binding.buttonContinue.text = getString(R.string.get) + " " + weekly.localizedFreeTrialPeriod + " " + getString(R.string.free)
                    binding.labelTrial.text = getString(R.string.start_your) + " " + weekly.localizedFreeTrialPeriod + " " + getString(R.string.free_trial)
                    binding.subLabelTrial.text =  getString(R.string.then) + " " + weekly.localizedPrice + " " + getString(R.string.per)+ " " + weekly.localizedSubscriptionPeriod
                    binding.closeButtonSubscription.visibility = View.VISIBLE


                    val spannable = SpannableString(getString(R.string.first_label_week) + " " +  weekly.localizedFreeTrialPeriod + " " +  getString(R.string.first_label_week2) + " " + weekly.localizedPrice + " " + getString(R.string.second_label_week))

                    val start = spannable.indexOf(weekly.localizedPrice)
                    val end = start + weekly.localizedPrice.length

                    spannable.setSpan(ForegroundColorSpan(Color.WHITE), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    spannable.setSpan(StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                    binding.textSubDescription.text = spannable

                } else {
                    binding.buttonContinue.text = getString(R.string.activate_subscribe)
                    binding.textSubDescription.text = getString(R.string.label_month_first)+ " " + weekly.localizedPrice+ " " + getString(R.string.per)+ " " + weekly.localizedSubscriptionPeriod + ". \n" + getString(R.string.label_period_cancel)

                    binding.labelTrial.text = weekly.localizedPrice + " " + getString(R.string.per)+ " " +  weekly.localizedSubscriptionPeriod
                    binding.subLabelTrial.text =  weekly.localizedSubscriptionPeriod + " " +  getString(R.string.subscription)
                }
                binding.termsOfUse.visibility = View.VISIBLE
                binding.privacePolicy.visibility = View.VISIBLE
                binding.buttonContinue.visibility = View.VISIBLE
                 binding.gifView.visibility = View.GONE
                binding.textSubDescription.visibility = View.VISIBLE
                binding.labelTrial.visibility = View.VISIBLE
                binding.subLabelTrial.visibility = View.VISIBLE

            } catch (e: Exception) {
                if (isActive) {
                    //processException(e)
                }
            }
        }

        binding.termsOfUse.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.privacePolicy.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.termsOfUse.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://moonly.club/termsofuse"))
            startActivity(browserIntent)
        }
        binding.privacePolicy.setOnClickListener{
            val browserIntentPrivacy = Intent(Intent.ACTION_VIEW, Uri.parse("https://moonly.club/privacy"))
            startActivity(browserIntentPrivacy)
        }

        binding.privacePolicy.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.termsOfUse.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        val terms_of_use = Intent(Intent.ACTION_VIEW, Uri.parse("https://palm-reader.online/terms-of-use"))
        val privace_policy = Intent(Intent.ACTION_VIEW, Uri.parse("https://palm-reader.online/privacy-policy"))
        binding.privacePolicy.setOnClickListener {
            startActivity(privace_policy)
        }
        binding.termsOfUse.setOnClickListener {
            startActivity(terms_of_use)
        }

        binding.closeButtonSubscription.setOnClickListener{
            onBackPressed()
        }

        binding.buttonContinue.setOnClickListener {
            lifecycleScope.launch {
                try {
                    lifecycleScope.launch {
                        try {
                            viewModel.launchSubscription(this@SubscriptionActivity as AppCompatActivity)
                            if (!isActive) return@launch
                            this@SubscriptionActivity.finish()
                        } catch (e: Exception) {
                                Toast.makeText(this@SubscriptionActivity, e.message, Toast.LENGTH_LONG)
                                    .show()
                        }
                    }
                } finally {
                    if (currentCoroutineContext().isActive) {
                        binding.buttonContinue.isClickable = true
                    }
                }
            }
        }
    }
}