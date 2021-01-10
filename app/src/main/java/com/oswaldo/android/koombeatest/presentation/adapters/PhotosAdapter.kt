package com.oswaldo.android.koombeatest.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.oswaldo.android.koombeatest.R
import kotlinx.android.synthetic.main.item_picture.view.*

class PhotosAdapter(private val images: List<String>,
                    private val context: Context,
                    private val listener: PostsAdapter.OnItemClickListener?,
                    private val imageSize: Int?): RecyclerView.Adapter<PhotosAdapter.PicturesViewHolder>() {

    class PicturesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.iv_pic
        private val pictureContainer: ConstraintLayout = itemView.pic_container

        fun bind(urlImage: String, context: Context, imageSize: Int?, listener: PostsAdapter.OnItemClickListener?){
            Glide.with(context).load(urlImage).diskCacheStrategy(DiskCacheStrategy.DATA).into(image)

            imageSize?.let {
                pictureContainer.layoutParams.height = imageSize
                pictureContainer.layoutParams.width = imageSize
            }

            image.setOnClickListener {
                listener?.onPicClick(urlImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicturesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_picture, parent, false)
        return PicturesViewHolder(view)
    }

    override fun onBindViewHolder(holder: PicturesViewHolder, position: Int) {
        holder.bind(images[position], context, imageSize, listener)
    }

    override fun getItemCount(): Int = images.size
}