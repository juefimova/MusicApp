package com.example.musicapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.musicapp.databinding.FragmentSingerBinding
import com.example.musicapp.model.*

class FragmentDescription : Fragment() {
    private var _binding: FragmentSingerBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "null"
        }

    private val args by navArgs<FragmentSongArgs>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentSingerBinding.inflate(inflater, container, false)
            .also {
                _binding = it
            }.root
    }

    private val dataBasePerson: PersonMusicDao by lazy {
        requireContext()
            .appDatabase
            .personMusicDao()
    }

    private val dataBaseContact: RoomUserFriendsDao by lazy {
        requireContext()
            .appDatabase
            .roomUserFriendsDao()
    }

    private val dataBaseMusic: RoomMusicDao by lazy {
        requireContext()
            .appDatabase
            .roomMusicDao()
    }



    var adapter: ViewPagerAdapterFragment? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val counter1 = args.keyCounter1
        val counter2 = args.keyCounter2
        val counter3 = args.keyCounter3
        binding.run {




            parentFragmentManager.setFragmentResultListener(
                "result",
                viewLifecycleOwner
            ) { _, bundle ->

            }


            if(counter2.id == 0.toLong() && counter3.id == 0.toLong()) {
                name.text = counter1.singer
                number.text = counter1.number
                description.text = counter1.description
                adapter = ViewPagerAdapterFragment(
                    counter1.images.split(",").map {
                        it.toInt()
                    }
                )
            }

            if(counter1.id == 0.toLong() && counter3.id == 0.toLong()) {
                name.text = counter2.singer
                number.text = counter2.number
                description.text = counter2.description
                adapter = ViewPagerAdapterFragment(
                    counter2.images.split(",").map {
                        it.toInt()
                    }
                )
            }

            if(counter1.id == 0.toLong() && counter2.id == 0.toLong()) {
                name.text = counter3.singer
                number.text = counter3.number
                description.text = counter3.description
                adapter = ViewPagerAdapterFragment(
                    counter3.images.split(",").map {
                        it.toInt()
                    }
                )
            }

            viewPager.adapter = adapter
            viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

            indicator.setViewPager(viewPager)


        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }


}
