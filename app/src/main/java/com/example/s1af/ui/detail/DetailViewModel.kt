package com.example.s1af.ui.detail

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.s1af.data.response.DetailUserResponse
import com.example.s1af.data.retrofit.ApiConfig
import com.example.s1af.data.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    val detail = MutableLiveData<DetailUserResponse>()

    private val _isLoading1 = MutableLiveData<Boolean>()
    val isLoading1: LiveData<Boolean> = _isLoading1

    fun detailUser(username: String){
        _isLoading1.value = true
        val apiService = ApiConfig.getApiService()
        val call = apiService.getDetailUser(username)
        call.enqueue(object: Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading1.value = false
                if (response.isSuccessful) {
                    val data = response.body()
                    detail.value = data
                }
            }
                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                }
            })

        }

    fun getDetailUser() : LiveData<DetailUserResponse> {
        return detail
    }

}

