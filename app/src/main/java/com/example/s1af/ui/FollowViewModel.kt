package com.example.s1af.ui


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.s1af.data.response.ItemsItem
import com.example.s1af.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel: ViewModel() {

   private val _followers: MutableLiveData<List<ItemsItem>> = MutableLiveData()
    val followers: LiveData<List<ItemsItem>> = _followers

   private val _following: MutableLiveData<List<ItemsItem>> = MutableLiveData()
   val following: LiveData<List<ItemsItem>> = _following

    private val _isLoading2 = MutableLiveData<Boolean>()
    val isLoading2: LiveData<Boolean> = _isLoading2

     fun showFollowers(username: String) {
         _isLoading2.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {

            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading2.value = false
                if (response.isSuccessful) {
                    _followers.value = response.body()
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                val username = "ContohUsername"
                Log.d("username", "Nilai username: $username")

            }
        })
    }

    fun showFollowing(username: String) {
        _isLoading2.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {

            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading2.value = false
                if (response.isSuccessful) {
                    _following.value = response.body()
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                val username = "ContohUsername"
                Log.d("username", "Nilai username: $username")

            }
        })
    }



}