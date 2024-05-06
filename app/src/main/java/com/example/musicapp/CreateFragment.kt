package com.example.musicapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.musicapp.databinding.FragmentSignBinding
import com.example.musicapp.model.RoomUser
import com.example.musicapp.model.RoomUserDao

class CreateFragment: Fragment() {
    private var _binding: FragmentSignBinding? = null
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
        return FragmentSignBinding.inflate(inflater, container, false)
            .also {
                _binding = it
            }.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            login.setOnClickListener{

                findNavController().navigate(
                    CreateFragmentDirections.toLogin2()
                )
            }

            account.setOnClickListener{
                val email = email1.text.toString()
                val password = password1.text.toString()
                val confirm = confirm1.text.toString()

                val user = dataBase
                    .findByName(email, password)

                if(user == null) {
                    findNavController().navigate(
                        CreateFragmentDirections.toToErrorEmail()
                    )
                    if(confirm == password) {
                        val fEmail = email1.text.toString()
                        val lPassword = password1.text.toString()
                        val lConfirm = confirm1.text.toString()
                        Log.d("text", "$fEmail $lPassword, $lConfirm")
                        dataBase
                            .insertAll(RoomUser(email = fEmail, password = lPassword))
                        rebuildResult()

                        /*findNavController().navigate(
                            FragmentCreateDirections.toClick7()
                        )*/
                        /*parentFragmentManager.setFragmentResult(
                            "result",
                            bundleOf("email" to email, "password" to password)
                        )*/
                    } else {

                        findNavController().navigate(
                            CreateFragmentDirections.toErrorPassword()
                        )
                    }
                }






                //creating account -> adding data to database
            }

        }


    }

    private fun rebuildResult() {
        binding.run {
            val users =
                dataBase
                    .getAll()
                    .joinToString("\n")
            Log.d("res", users)
            email1.setText("")
            password1.setText("")
            confirm1.setText("")
        }
    }


    /*private fun showCustomDialogCreated() {
        CustomDialogCreated().show(childFragmentManager, null)
        //создан аккаунт
    }

    private fun showCustomDialogError() {
        CustomDialogError().show(childFragmentManager, null)
        //ошибка
    }*/


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}