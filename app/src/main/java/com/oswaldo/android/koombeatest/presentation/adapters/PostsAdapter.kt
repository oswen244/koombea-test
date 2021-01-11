package com.oswaldo.android.koombeatest.presentation.adapters

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.oswaldo.android.koombeatest.R
import com.oswaldo.android.koombeatest.data.models.User
import com.oswaldo.android.koombeatest.utils.GlideApp
import com.oswaldo.android.koombeatest.utils.Utils.parseDate
import com.oswaldo.android.koombeatest.utils.Utils.resizeImage
import kotlinx.android.synthetic.main.item_post.view.*


class PostsAdapter(var postList: List<User>): RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    fun setOnclickListener(listener: OnItemClickListener?){
        onItemClickListener = listener
    }

    interface OnItemClickListener {
        fun onFirstPicClick(image: String)
        fun onPicClick(image: String)
    }

    class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val profilePic: ImageView = itemView.iv_profile_pic
        private val name: TextView = itemView.tv_user_name
        private val email: TextView = itemView.tv_user_email
        private val date: TextView = itemView.tv_date
        private val firstPic: ImageView = itemView.iv_big_one
        private val pics: RecyclerView = itemView.rv_rest_of_the_pics
        private lateinit var picturesAdapter: PhotosAdapter

        fun bind(post: User, listener: OnItemClickListener?){
            GlideApp.with(itemView.context).load(post.profile_pic).diskCacheStrategy(DiskCacheStrategy.DATA).into(
                    profilePic
            )
            name.text = post.name
            email.text = post.email
            date.text = parseDate(post.post.date.replace(" (Colombia Standard Time)", ""),
                    "EEE MMM dd yyyy HH:mm:ss Z", "MMM dd")
            GlideApp.with(itemView.context)
                    .load(post.post.pics[0])
                    .placeholder(ColorDrawable(itemView.context.getColor(R.color.shimmer_loading)))
                    .apply(RequestOptions().override(1080, 1340))
                    .into(firstPic)

            firstPic.setOnClickListener {
                listener?.onFirstPicClick(post.post.pics[0])
            }
            pics.visibility = View.GONE

            if(post.post.pics.size >= 2){
                pics.visibility = View.VISIBLE
                picturesAdapter = PhotosAdapter(post.post.pics, itemView.context, listener, firstPic)
                pics.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                pics.adapter = picturesAdapter
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(postList[position], onItemClickListener)
    }

    override fun getItemCount(): Int = postList.size

    fun updateList(newPostList: List<User>){
        postList = newPostList
        notifyDataSetChanged()
    }
}