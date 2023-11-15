package com.example.s1af.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.s1af.databinding.FragmentFollowBinding


class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private lateinit var adapter2: UserAdapter

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

    private var position = 0
    private var username = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFollow.layoutManager = LinearLayoutManager(requireContext())
        adapter2 = UserAdapter()
        binding.rvFollow.adapter = adapter2

        val followViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowViewModel::class.java)


        followViewModel.followers.observe(viewLifecycleOwner) { followers ->
            adapter2.submitList(followers)
        }
        followViewModel.following.observe(viewLifecycleOwner) { following ->
            adapter2.submitList(following)
        }

        followViewModel.isLoading2.observe(viewLifecycleOwner) { isLoading2 ->
            showLoading2(isLoading2)
        }



        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME) ?: ""
        }
        if (position == 1) {
            followViewModel.showFollowers(username)
            // binding.testUsername.text = "Get Follower $username "
        } else {
            followViewModel.showFollowing(username)
            //binding.testUsername.text = "Get Following $username"
        }


    }
    private fun showLoading2(isLoading2: Boolean) {
        if (isLoading2) {
            binding.progressBarFollow.visibility = View.VISIBLE
        } else {
            binding.progressBarFollow.visibility = View.GONE
        }
    }
}



