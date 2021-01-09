package com.oswaldo.android.koombeatest.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.oswaldo.android.koombeatest.R
import com.oswaldo.android.koombeatest.data.models.User
import com.oswaldo.android.koombeatest.utils.Utils.parseDate
import kotlinx.android.synthetic.main.item_post.view.*

class PostsAdapter(var postList: List<User>): RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val profilePic: ImageView = itemView.iv_profile_pic
        private val name: TextView = itemView.tv_user_name
        private val email: TextView = itemView.tv_user_email
        private val date: TextView = itemView.tv_date
        private val firstPic: ImageView = itemView.iv_big_one

        fun bind(post: User){
            Glide.with(itemView.context).load(post.profile_pic).diskCacheStrategy(DiskCacheStrategy.DATA).into(
                profilePic
            )
            name.text = post.name
            email.text = post.email
            date.text = parseDate(post.post.date.replace(" (Colombia Standard Time)", ""),
                "EEE MMM dd yyyy HH:mm:ss Z", "MMM dd")
            Glide.with(itemView.context).load(post.post.pics[0]).diskCacheStrategy(DiskCacheStrategy.DATA).into(
                firstPic
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    override fun getItemCount(): Int = postList.size

    fun updateList(newPostList: List<User>){
        postList = newPostList
        notifyDataSetChanged()
    }
}