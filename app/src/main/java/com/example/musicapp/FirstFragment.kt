package com.example.musicapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.musicapp.databinding.FragmentFirstBinding

class FirstFragment: Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "null"
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        return FragmentFirstBinding.inflate(inflater, container, false)
            .also {
                _binding = it
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        binding.run {

            getStarted.setOnClickListener{
                findNavController().navigate(
                    FirstFragmentDirections.toLogin()
                )
            }



        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}