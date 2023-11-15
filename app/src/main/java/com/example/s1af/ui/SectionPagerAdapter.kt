package com.example.s1af.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity, private val intent: Intent) : FragmentStateAdapter(activity) {

    val username = intent.getStringExtra("username") ?: ""


    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        val args = Bundle().apply {
            putInt(FollowFragment.ARG_POSITION, position + 1)
            putString(FollowFragment.ARG_USERNAME, username)
        }
        fragment.arguments = args
        return fragment
    }
    override fun getItemCount(): Int {
        return 2
    }
}