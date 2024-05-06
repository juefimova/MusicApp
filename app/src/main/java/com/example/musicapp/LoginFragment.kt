package com.example.musicapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.musicapp.databinding.FragmentLoginBinding
import com.example.musicapp.model.RoomUserDao

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "null"
        }
    private val dataBase: RoomUserDao by lazy {
        requireContext()
            .appDatabase
            .roomUserDao()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentLoginBinding.inflate(inflater, container, false)
            .also {
                _binding = it
            }.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.run {
            signup.setOnClickListener {
                findNavController().navigate(
                    LoginFragmentDirections.toSign()
                )
            }

            btnLogin.setOnClickListener{




                try {
                    val email = email.text.toString()
                    val password = password.text.toString()
                    val user = dataBase.findByName(email, password)

                    ID = user.id

                    if (email != user.email) {
                        findNavController().navigate(
                            LoginFragmentDirections.toErrorUser()
                        )
                    } else if (email == user.email && password == user.password) {
                        findNavController().navigate(
                            LoginFragmentDirections.toApp()
                        )

                    }
                } catch (i: NullPointerException) {
                    findNavController().navigate(
                        LoginFragmentDirections.toErrorUser()
                    )
                }
            }




            /*forgot.setOnClickListener {
                findNavController().navigate(
                    FragmentLoginDirections.toClick5()

                )
            }*/
        }

    }

    companion object {
        var ID: Long = 0
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}
