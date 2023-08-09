package ai.art.image.generator.avatar.onboarding

import ai.art.image.generator.avatar.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class InfiniteScrollingAdapter(private val images: MutableList<Int>)
    : RecyclerView.Adapter<InfiniteScrollingAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Integer.MAX_VALUE
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = images[position % images.size]
        holder.imageView.setImageResource(image)
    }
}