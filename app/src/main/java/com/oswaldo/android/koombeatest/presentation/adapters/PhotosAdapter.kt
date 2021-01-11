package com.oswaldo.android.koombeatest.presentation.adapters

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.oswaldo.android.koombeatest.R
import com.oswaldo.android.koombeatest.utils.GlideApp
import com.oswaldo.android.koombeatest.utils.Utils
import kotlinx.android.synthetic.main.item_picture.view.*

class PhotosAdapter(private var images: List<String>,
                    private val context: Context,
                    private val listener: PostsAdapter.OnItemClickListener?,
                    private val bigPicture: View): RecyclerView.Adapter<PhotosAdapter.PicturesViewHolder>() {

    class PicturesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.iv_pic
        private val pictureContainer: ConstraintLayout = itemView.pic_container

        fun bind(urlImage: String, context: Context, imagesSize: Int, bigPicture: View, listener: PostsAdapter.OnItemClickListener?){
            GlideApp.with(context)
                .load(urlImage)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .placeholder(ColorDrawable(context.getColor(R.color.shimmer_loading)))
                .into(image)

            if(imagesSize == 2 || imagesSize == 3){
                pictureContainer.layoutParams.height = Utils.resizeImage(pictureContainer)
                pictureContainer.layoutParams.width = Utils.resizeImage(pictureContainer)
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
        holder.bind(images[position], context, images.size, bigPicture, listener)
    }

    override fun getItemCount(): Int {
        if(images.size == 2){
            bigPicture.visibility = View.GONE
        }else{
            bigPicture.visibility = View.VISIBLE
        }
        return images.size
    }

    private fun updateList(): List<String>{
        if(images.size >= 3){
            return images.subList(1, images.size)
        }

        return images
    }
}