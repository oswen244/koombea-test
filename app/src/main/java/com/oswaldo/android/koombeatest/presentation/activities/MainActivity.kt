package com.oswaldo.android.koombeatest.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.oswaldo.android.koombeatest.databinding.ActivityMainBinding
import com.oswaldo.android.koombeatest.presentation.adapters.PostsAdapter
import com.oswaldo.android.koombeatest.presentation.fragments.ImageFragment
import com.oswaldo.android.koombeatest.utils.Utils
import com.oswaldo.android.koombeatest.viewModels.PostsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PostsAdapter
    private val postsViewModel: PostsViewModel by viewModel<PostsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupObservers()
    }

    private fun setupObservers() {
        postsViewModel.postList.observe(this, Observer {
            adapter.updateList(it)
            binding.refreshView.isRefreshing = false
            binding.rvPosts.visibility = View.VISIBLE

        })

        postsViewModel.isViewLoading.observe(this, Observer {
            if(it){
                binding.rvPosts.visibility = View.GONE
                Utils.shimmerStart(binding.shimmerLoading)
            }else{
                Utils.shimmerStop(binding.shimmerLoading)
            }
        })
    }

    private fun setupView() {
        adapter = PostsAdapter(postsViewModel.postList.value!!)
        binding.rvPosts.layoutManager = LinearLayoutManager(this)
        binding.rvPosts.adapter = adapter

        binding.refreshView.setOnRefreshListener {
            postsViewModel.loadPosts()
        }

        adapter.setOnclickListener(object : PostsAdapter.OnItemClickListener{
            override fun onFirstPicClick(image: String) {
                ImageFragment.show(supportFragmentManager, image)
            }

            override fun onPicClick(image: String) {
                ImageFragment.show(supportFragmentManager, image)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        postsViewModel.loadPosts()
    }
}