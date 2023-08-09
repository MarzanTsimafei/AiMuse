package ai.art.image.generator.avatar.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ai.art.image.generator.avatar.R
import ai.art.image.generator.avatar.databinding.FragmentLoginBinding
import ai.art.image.generator.avatar.databinding.FragmentWelcomeBinding
import android.graphics.Color
import android.graphics.Typeface
import android.os.Handler
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView


class WelcomeFragment : Fragment() {

    private var currentPage = 8

    private var images = mutableListOf(R.drawable.img_1, R.drawable.img_2, R.drawable.img_3)
    private var imagesTop = mutableListOf(R.drawable.img_1_top, R.drawable.img_2_top, R.drawable.img_3_top)
    private var currentPageTop =  1260
    private val handler: Handler = Handler()
    private val handlerTop: Handler = Handler()
    var count = 0
    private var _binding: FragmentWelcomeBinding? = null
    val binding get() = _binding!!


    private val runTop: Runnable = object : Runnable {
        override fun run() {
            if(count==0){
                binding.recycleViewTop.scrollToPosition(1261)
            }
            count+=1
            currentPageTop--
            binding.recycleViewTop.smoothScrollToPosition(currentPageTop)
            if (currentPageTop < 0)
                currentPageTop = imagesTop.size - 1
            handlerTop.postDelayed(this, 100)
        }
    }

    private val run: Runnable = object : Runnable {
        override fun run() {
            binding.recycleView.smoothScrollToPosition(currentPage++)
            handler.postDelayed(this, 100)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spannable = SpannableStringBuilder(binding.textView6.text)
        val startIndex = binding.textView6.text.lastIndexOf(" ") + 1

        val boldSpan = StyleSpan(Typeface.BOLD)
        val colorSpan = ForegroundColorSpan(Color.parseColor("#5392E9"))

        spannable.setSpan(boldSpan, startIndex, binding.textView6.text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(colorSpan, startIndex, binding.textView6.text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.textView6.setText(spannable, TextView.BufferType.SPANNABLE)
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(run)
    }

    override fun onResume() {
        super.onResume()


        binding.recycleView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.recycleView)
        val adapter = InfiniteScrollingAdapter(images)
        binding.recycleView.adapter = adapter
        binding.recycleView.layoutManager = SmoothScrollLayoutManager(requireActivity())
        handler.postDelayed(run, 100)




        binding.recycleViewTop.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true).apply {
            stackFromEnd = true
        }
        val snapHelperTop = LinearSnapHelper()
        snapHelperTop.attachToRecyclerView(binding.recycleViewTop)
        val adapterTop = InfiniteScrollingAdapter(imagesTop)
        binding.recycleViewTop.adapter = adapterTop
        binding.recycleViewTop.layoutManager = SmoothScrollLayoutManager(requireActivity())
        handlerTop.postDelayed(runTop, 100)
    }
}