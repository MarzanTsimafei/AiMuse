package ai.art.image.generator.avatar.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ai.art.image.generator.avatar.R
import ai.art.image.generator.avatar.databinding.FragmentAnimeBinding
import ai.art.image.generator.avatar.databinding.FragmentWelcomeBinding
import ai.art.image.generator.avatar.onboarding.DelayOnLifecycle.delayOnLifecycle
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper

class AnimeFragment : Fragment() {


    private var _binding: FragmentAnimeBinding? = null
    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fadeIn : Animation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        val fadeOut : Animation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)
        val grayColor = ContextCompat.getColor(requireContext(), R.color.gray)
        val whiteColor = ContextCompat.getColor(requireContext(), R.color.white)

        binding.anime.setOnClickListener {
            binding.imageView.startAnimation(fadeIn)
            binding.imageView.delayOnLifecycle(300){
                binding.imageView.setImageResource(R.drawable.mask_5)
                binding.imageView.clearAnimation()
                binding.imageView.startAnimation(fadeOut)
            }

            binding.anime.setTextColor(whiteColor)
            binding.disco.setTextColor(grayColor)
            binding.abstracts.setTextColor(grayColor)
            binding.abstractionism.setTextColor(grayColor)
            binding.colorful.setTextColor(grayColor)

            binding.anime.background = resources.getDrawable(R.drawable.selected_frame)
            binding.disco.background = resources.getDrawable(R.drawable.unselected_frame)
            binding.abstracts.background = resources.getDrawable(R.drawable.unselected_frame)
            binding.abstractionism.background = resources.getDrawable(R.drawable.unselected_frame)
            binding.colorful.background = resources.getDrawable(R.drawable.unselected_frame)
        }
        binding.disco.setOnClickListener {
            binding.anime.setTextColor(grayColor)
            binding.disco.setTextColor(whiteColor)
            binding.abstracts.setTextColor(grayColor)
            binding.abstractionism.setTextColor(grayColor)
            binding.colorful.setTextColor(grayColor)
            binding.imageView.startAnimation(fadeIn)
            binding.imageView.delayOnLifecycle(300){
                binding.imageView.setImageResource(R.drawable.mask_2)
                binding.imageView.clearAnimation()
                binding.imageView.startAnimation(fadeOut)
            }
            binding.anime.background = resources.getDrawable(R.drawable.unselected_frame)
            binding.disco.background = resources.getDrawable(R.drawable.selected_frame)
            binding.abstracts.background = resources.getDrawable(R.drawable.unselected_frame)
            binding.abstractionism.background = resources.getDrawable(R.drawable.unselected_frame)
            binding.colorful.background = resources.getDrawable(R.drawable.unselected_frame)
        }
        binding.abstracts.setOnClickListener {
            binding.anime.setTextColor(grayColor)
            binding.disco.setTextColor(grayColor)
            binding.abstracts.setTextColor(whiteColor)
            binding.abstractionism.setTextColor(grayColor)
            binding.colorful.setTextColor(grayColor)
            binding.imageView.startAnimation(fadeIn)
            binding.imageView.delayOnLifecycle(300){
                binding.imageView.setImageResource(R.drawable.mask_1)
                binding.imageView.clearAnimation()
                binding.imageView.startAnimation(fadeOut)
            }
            binding.anime.background = resources.getDrawable(R.drawable.unselected_frame)
            binding.disco.background = resources.getDrawable(R.drawable.unselected_frame)
            binding.abstracts.background = resources.getDrawable(R.drawable.selected_frame)
            binding.abstractionism.background = resources.getDrawable(R.drawable.unselected_frame)
            binding.colorful.background = resources.getDrawable(R.drawable.unselected_frame)
        }
        binding.abstractionism.setOnClickListener {
            binding.anime.setTextColor(grayColor)
            binding.disco.setTextColor(grayColor)
            binding.abstracts.setTextColor(grayColor)
            binding.abstractionism.setTextColor(whiteColor)
            binding.colorful.setTextColor(grayColor)
            binding.imageView.startAnimation(fadeIn)
            binding.imageView.delayOnLifecycle(300){
                binding.imageView.setImageResource(R.drawable.mask_3)
                binding.imageView.clearAnimation()
                binding.imageView.startAnimation(fadeOut)
            }
            binding.anime.background = resources.getDrawable(R.drawable.unselected_frame)
            binding.disco.background = resources.getDrawable(R.drawable.unselected_frame)
            binding.abstracts.background = resources.getDrawable(R.drawable.unselected_frame)
            binding.abstractionism.background = resources.getDrawable(R.drawable.selected_frame)
            binding.colorful.background = resources.getDrawable(R.drawable.unselected_frame)
        }
        binding.colorful.setOnClickListener {
            binding.anime.setTextColor(grayColor)
            binding.disco.setTextColor(grayColor)
            binding.abstracts.setTextColor(grayColor)
            binding.abstractionism.setTextColor(grayColor)
            binding.colorful.setTextColor(whiteColor)
            binding.imageView.startAnimation(fadeIn)
            binding.imageView.delayOnLifecycle(300){
                binding.imageView.setImageResource(R.drawable.mask_4)
                binding.imageView.clearAnimation()
                binding.imageView.startAnimation(fadeOut)
            }
            binding.anime.background = resources.getDrawable(R.drawable.unselected_frame)
            binding.disco.background = resources.getDrawable(R.drawable.unselected_frame)
            binding.abstracts.background = resources.getDrawable(R.drawable.unselected_frame)
            binding.abstractionism.background = resources.getDrawable(R.drawable.unselected_frame)
            binding.colorful.background = resources.getDrawable(R.drawable.selected_frame)
        }
    }

}