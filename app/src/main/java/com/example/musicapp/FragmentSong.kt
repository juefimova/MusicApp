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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.musicapp.databinding.FragmentSongBinding
import com.example.musicapp.model.*

class FragmentSong : Fragment() {
    private var _binding: FragmentSongBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "null"
        }
    private val databaseRock: RockTypeDao by lazy {
        requireContext()
            .appDatabase
            .rockTypeDao()
    }

    private val databaseRap: RapTypeDao by lazy {
        requireContext()
            .appDatabase
            .rapTypeDao()
    }

    private val dataBasePop: PopTypeDao by lazy {
        requireContext()
            .appDatabase
            .popTypeDao()
    }

    private val args by navArgs<FragmentSongArgs>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentSongBinding.inflate(inflater, container, false)
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


    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { permissionGranted ->
        if (permissionGranted)
            resultLauncherGetContacts.launch(intent)
    }


    val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)

    @SuppressLint("Range")
    val resultLauncherGetContacts = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            var uri: Uri = it.data?.data ?: return@registerForActivityResult
            Log.d("uri->", uri.toString() + "\n" + it.data?.toString())
            val cursor: Cursor = activity?.contentResolver?.query(
                uri, null, null, null, null
            ) ?: return@registerForActivityResult
            if (cursor.moveToFirst()) {
                val id: String =
                    cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
                val hasPhone: String =
                    cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                if (hasPhone.equals("1")) {
                    Log.d(
                        "Phone.CONTENT_URI -> ",
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI.toString()
                    )
                    val cursorPhones: Cursor = activity?.contentResolver?.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                        null,
                        null
                    ) ?: return@registerForActivityResult
                    cursorPhones.moveToFirst()
                    val number =
                        cursorPhones.getString(cursorPhones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    val name =
                        cursorPhones.getString(cursorPhones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    Log.d("DATA:", "number: $number ; name:$name")


                    val friend = RoomUserFriends(0, LoginFragment.ID.toString(), name, number)
                    if (friend != dataBaseContact.findByName(name, number)) {
                        dataBaseContact.insertFriend(friend)
                    }

                }
            }
        }
    }

    private fun requestPermission(permission: String) {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                permissionLauncher.launch(permission)

            }


            else -> {

                permissionLauncher.launch(permission)

            }

        }

    }

    private var adapterRock: MusicRockListAdapter? = null
    private var adapterPop: MusicPopListAdapter? = null
    private var adapterRap: MusicRapListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val counter1 = args.keyCounter1
        val counter2 = args.keyCounter2
        val counter3 = args.keyCounter3
        binding.run {

            description.setOnClickListener {
                findNavController().navigate(
                    FragmentSongDirections.toDescription(
                        counter1, counter2, counter3
                    )
                )

                /*Log.d("description", "clicked")
                adapterRock = MusicRockListAdapter(databaseRock.getAll().toMutableList()) {
                    findNavController().navigate(
                        FragmentSongDirections.toDescription(
                            it,
                            PopType(0, "0", "0", 0, "0", "0", "0"),
                            RapType(0, "0", "0", 0, "0", "0", "0")
                        )
                    )
                }

                adapterPop = MusicPopListAdapter(dataBasePop.getAll().toMutableList()) {
                    findNavController().navigate(
                        FragmentSongDirections.toDescription(
                            RockType(0, "0", "0", 0, "0", "0", "0"),
                            it,
                            RapType(0, "0", "0", 0, "0", "0", "0")
                        )
                    )

                }

                adapterRap = MusicRapListAdapter(databaseRap.getAll().toMutableList()) {
                    findNavController().navigate(
                        FragmentSongDirections.toDescription(
                            RockType(0, "0", "0", 0, "0", "0", "0"),
                            PopType(0, "0", "0", 0, "0", "0", "0"),
                            it
                        )
                    )
                }*/

            }

            share.setOnClickListener {
                Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "Hello!! You should definetely listen to ${name.text} by ${singer.text} :)"
                    )
                    putExtra(Intent.EXTRA_SUBJECT, "subject")
                }.also {
                    val chooserIntent =
                        Intent.createChooser(it, "message for my friend")
                    startActivity(chooserIntent)
                }
            }


            parentFragmentManager.setFragmentResultListener(
                "result",
                viewLifecycleOwner
            ) { _, bundle ->

            }


            if(counter2.id == 0.toLong() && counter3.id == 0.toLong()) {
                name.text = counter1.name
                singer.text = counter1.singer
                pic.setImageResource(counter1.image)
            }

            if(counter1.id == 0.toLong() && counter3.id == 0.toLong()) {
                name.text = counter2.name
                singer.text = counter2.singer
                pic.setImageResource(counter2.image)
            }

            if(counter1.id == 0.toLong() && counter2.id == 0.toLong()) {
                name.text = counter3.name
                singer.text = counter3.singer
                pic.setImageResource(counter3.image)
            }


            val name = name.text
            val id = LoginFragment.ID
            if (dataBasePerson.getUserMusic(id, name.toString()).size == 1) {
                save.setColorFilter(resources.getColor(R.color.purple_200))
            } else {
                save.setColorFilter(resources.getColor(R.color.black))
                Log.d("зашли в else", dataBasePerson.getUserMusic(id, name.toString()).toString())
            }




            save.setOnClickListener {

                try {

                    val song = PersonMusic(0, id, name.toString())
                    var songs = arrayOf(song)
                    Log.d("USER_ID", dataBasePerson.getUserMusic(id, name.toString()).toString())


                    /*if(dataBase.getUserBooks(id, name.toString()).size == 1) {
                        save.setColorFilter(resources.getColor(R.color.purple_200))
                    }
                    else  {
                        save.setColorFilter(resources.getColor(R.color.black))
                        Log.d("зашли в else1", dataBase.getUserBooks(id, name.toString()).toString())

                    }*/

                    if (dataBasePerson.getUserMusic(id, name.toString()).size == 1) {
                        save.setColorFilter(resources.getColor(R.color.black))
                        dataBasePerson.deleteMusicIDuserName(id, name.toString())
                    } else {

                        save.setColorFilter(resources.getColor(R.color.purple_200))
                        dataBasePerson.insertMusic(*songs)
                    }


                } catch (i: NullPointerException) {

                }

            }


        }

    }

    companion object {
        val PERMISSION = Manifest.permission.READ_CONTACTS
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }


}

