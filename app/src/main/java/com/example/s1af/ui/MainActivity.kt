package com.example.s1af.ui

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.s1af.data.response.ItemsItem
import com.example.s1af.data.response.UserGithubResponse
import com.example.s1af.data.retrofit.ApiConfig
import com.example.s1af.databinding.ActivityMainBinding
import com.example.s1af.ui.detail.DetailUserGithub
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var mainViewModel: MainViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


        //inisialisasi view model//

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)

        mainViewModel.github.observe(this) { github ->
            userAdapter.submitList(github)
        }

        mainViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        //inisialisasi view model//

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        userAdapter = UserAdapter()
        binding.rvUser.adapter = userAdapter

        //listener
        userAdapter.setOnItemClickListener(object : UserAdapter.OnItemClickListener {
            override fun onItemClick(item: ItemsItem) {
                val intent = Intent(this@MainActivity, DetailUserGithub::class.java)
                intent.putExtra("username",item.login)
                startActivity(intent)
            }

        })



        //SEARCH
        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                mainViewModel.userGithub(query?: " ")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        userGithub(query = "a")
    }





    private fun userGithub(query : String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getUser(query)
        client.enqueue(object : Callback<UserGithubResponse> {
            override fun onResponse(
                call: Call<UserGithubResponse>,
                response: Response<UserGithubResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val user = responseBody.items
                        userAdapter.submitList(user)

                    }
                } else {
                    Log.e(TAG,  "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserGithubResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")

            }
        })
    }
    private fun showLoading(isLoading : Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}