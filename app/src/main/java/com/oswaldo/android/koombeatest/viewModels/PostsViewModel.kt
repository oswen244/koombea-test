package com.oswaldo.android.koombeatest.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oswaldo.android.koombeatest.data.OperationResult
import com.oswaldo.android.koombeatest.data.bl.IPostsRetrieve
import com.oswaldo.android.koombeatest.data.models.PostResponse
import com.oswaldo.android.koombeatest.data.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostsViewModel(private val postsRepository: IPostsRetrieve): ViewModel() {
    private val _postsList = MutableLiveData<MutableList<User>>().apply { value = arrayListOf() }
    val postList: LiveData<MutableList<User>> = _postsList

    private val _isViewLoading= MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    fun loadPosts(){
        _isViewLoading.postValue(true)
        viewModelScope.launch {
            val resultList: OperationResult<PostResponse> = withContext(Dispatchers.IO){
                postsRepository.getPosts()
            }
            _isViewLoading.postValue(false)
            when(resultList){
                is OperationResult.Success -> {
                    _postsList.value = resultList.data?.data as MutableList<User>
                }

                is  OperationResult.Error -> {
                    Log.e("ErrorPost", "OperationResult.error")
                }
            }
        }
    }
}