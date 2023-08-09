package ai.art.image.generator.avatar

import ai.art.image.generator.avatar.databinding.ActivityMainBinding
import ai.art.image.generator.avatar.onboarding.OnboardingActivity
import ai.art.image.generator.avatar.subscription.SubscriptionActivity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

private lateinit var binding: ActivityMainBinding
var pref: SharedPreferences? = null

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pref = this.getSharedPreferences("TABLE", Context.MODE_PRIVATE)

        when (pref?.getBoolean("onboarding", false)!!) {
            true -> {
                startActivity(Intent(this@MainActivity, SubscriptionActivity::class.java))
            }
            false -> {
                startActivity(Intent(this@MainActivity, OnboardingActivity::class.java))

            }
        }
    }
}