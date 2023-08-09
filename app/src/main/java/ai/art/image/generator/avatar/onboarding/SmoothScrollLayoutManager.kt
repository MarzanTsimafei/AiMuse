package ai.art.image.generator.avatar.onboarding


import android.content.Context
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView



class SmoothScrollLayoutManager(internal var context: Context) : LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false) {

    override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State, position: Int) {
        val smoothScroller = object : LinearSmoothScroller(context) {
            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                return 800f / displayMetrics.densityDpi
            }
        }
        smoothScroller.targetPosition = position
        startSmoothScroll(smoothScroller)
    }
}