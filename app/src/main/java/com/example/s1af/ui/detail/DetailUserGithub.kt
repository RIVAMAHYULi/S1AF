package com.example.s1af.ui.detail



import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.s1af.databinding.ActivityDetailUserGithubBinding
import com.example.s1af.ui.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class DetailUserGithub : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserGithubBinding
    private lateinit var detailViewModel: DetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserGithubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username") ?: ""





        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]

        detailViewModel.isLoading1.observe(this) { isLoading1 ->
            showLoading1(isLoading1)
        }





        detailViewModel.detailUser(username)
        detailViewModel.getDetailUser().observe(this) {
            if (it != null) {
                binding.apply {
                    Glide.with(this@DetailUserGithub)
                        .load(it.avatarUrl)
                        .into(avatarView)

                    // Set username
                    textViewName.text = it.login

                    profile.text = it.name

                    // Set jumlah followers dan following
                    followersCountTextView.text = it.followers.toString()
                    followingCountTextView.text = it.following.toString()
                }


            }
        }
        val followAdapter = SectionsPagerAdapter(this, intent)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = followAdapter

        val tabLayout: TabLayout = binding.tabs
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Followers"
                1 -> tab.text = "Following"
                else -> ""
            }

            if (position < 0 || position > 1) {
                Log.e("TabLayoutMediator", "Posisi tab tidak valid: $position")
            }
        }.attach()

    }
    private fun showLoading1(isLoading1 : Boolean) {
        if (isLoading1) {
            binding.progressBardetail.visibility = View.VISIBLE
        } else {
            binding.progressBardetail.visibility = View.GONE
        }
    }
}
    // private fun showLoading2(isLoading : Boolean) {
      //  if (isLoading) {
        //    binding.progressBardetail.visibility = View.VISIBLE
        //} else {
         //  binding.progressBardetail.visibility = View.GONE
       // }
    //}







