package com.example.s1af.ui


import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.s1af.data.response.ItemsItem
import com.example.s1af.data.response.UserGithubResponse
import com.example.s1af.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _github = MutableLiveData<List<ItemsItem>>()
    val github: LiveData<List<ItemsItem>> = _github

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        userGithub(query = "a")
    }

    fun userGithub(query : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(query)
        client.enqueue(object : Callback<UserGithubResponse> {
            override fun onResponse(
                call: Call<UserGithubResponse>,
                response: Response<UserGithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _github.value = response.body()?.items

                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserGithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")

            }
        })
    }
}
