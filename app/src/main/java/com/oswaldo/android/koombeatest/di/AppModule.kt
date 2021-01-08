package com.oswaldo.android.koombeatest.di

import com.oswaldo.android.koombeatest.data.bl.IPostsRetrieve
import com.oswaldo.android.koombeatest.data.repository.PostsRepository
import com.oswaldo.android.koombeatest.viewModels.PostsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repoModule = module {
    single<IPostsRetrieve> { PostsRepository() }
}

val viewModelModule = module {
    viewModel { PostsViewModel(get()) }
}