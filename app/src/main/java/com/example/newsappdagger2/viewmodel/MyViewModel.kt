package com.example.newsappdagger2.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsappdagger2.databace.entity.UserEntity
import com.example.newsappdagger2.recourse.PagerResource
import com.example.newsappdagger2.recourse.UserResource
import com.example.newsappdagger2.repository.UserRepository
import com.example.newsappdagger2.utils.NetworkHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


class MyViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    fun getCategory(): StateFlow<UserResource> {
        val stateFlow = MutableStateFlow<UserResource>(UserResource.Loading)
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                userRepository.getUsers().catch {
                    stateFlow.emit(UserResource.Error(it.message ?: ""))
                }.collect { res ->
                    if (res.isSuccessful) {
                        val body = res.body()?.articles
                        stateFlow.emit(UserResource.Success(body))
                    } else {
                        stateFlow.emit(UserResource.Error("No internet connection"))
                    }
                }
            } else {
                stateFlow.emit(UserResource.Error("No internet connection"))
            }
        }
        return stateFlow
    }

    fun getPagerCategory(string: String): StateFlow<PagerResource> {
        val state = MutableStateFlow<PagerResource>(PagerResource.Loading)
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                userRepository.getCategory(string).catch {
                    state.emit(PagerResource.Error(it.message ?: ""))
                }.collect { res ->
                    if (res.isSuccessful) {
                        val body = res.body()?.articles
                        state.emit(PagerResource.Success(body))
                    } else {
                        state.emit(PagerResource.Error(res.message() ?: ""))
                    }
                }
            } else {
                state.emit(PagerResource.Error("No internet connection"))
            }
        }
        return state
    }
}
//val list = ArrayList<UserEntity>()
//                        try {
//                            body?.forEach {
//                                list.add(
//                                    UserEntity(
//                                        author = it.author,
//                                        description = it.description,
//                                        title = it.title,
//                                        urlToImage = it.urlToImage,
//                                        content = it.content,
//                                        publishedAt = it.publishedAt,
//                                        url = it.url
//                                    )
//                                )
//                            }
//                        } catch (e: Exception) {
//                            Log.d(TAG, "getCategory: ${e.message}")
//                        }