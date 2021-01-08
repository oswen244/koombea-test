package com.oswaldo.android.koombeatest.data.bl

import com.oswaldo.android.koombeatest.data.OperationResult
import com.oswaldo.android.koombeatest.data.models.PostResponse

interface IPostsRetrieve {
    suspend fun getPosts(): OperationResult<PostResponse>
}