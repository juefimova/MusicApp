package com.example.musicapp

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.FragmentListBinding
import com.example.musicapp.model.*

class ListFragment : Fragment() {


    private var _binding: FragmentListBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "null"
        }


    private val dataBase: RoomMusicDao by lazy {
        requireContext()
            .appDatabase
            .roomMusicDao()
    }

    private val dataBase2: RoomUserDao by lazy {
        requireContext()
            .appDatabase
            .roomUserDao()
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentListBinding.inflate(inflater, container, false)
            .also {
                _binding = it
            }.root
    }

    private var adapter: MusicListAdapter? = null

    companion object {
        private const val RECYCLER_ITEM_SPACE = 0
    }


    val song1 = RoomMusic(1, "HONEY", "Måneskin", R.drawable.pic1)
    val song2 = RoomMusic(2, "505", "Arctic Monkeys", R.drawable.pic2)
    val song3 = RoomMusic(3, "Radio", "Lana Del Rey", R.drawable.pic3)
    val song4 = RoomMusic(4, "Phobia", "Nothing But Thieves", R.drawable.pic4)
    val song5 = RoomMusic(5, "Flawless", "The Neighbourhood", R.drawable.pic5)
    val song6 = RoomMusic(6, "hometown", "cleopatrick", R.drawable.pic6)
    val song7 = RoomMusic(7, "Figure It Out", "Royal Blood", R.drawable.pic7)
    val song8 = RoomMusic(8, "Breezeblocks", "alt-J", R.drawable.pic8)
    val song9 = RoomMusic(9, "Umbrella", "Rihanna", R.drawable.pic8) //
    val song10 = RoomMusic(10, "Toxic", "Britney Spears", R.drawable.pic8)//
    val song11 = RoomMusic(11, "Hits Different", "Taylor Swift", R.drawable.pic8)//
    val song12 = RoomMusic(12, "Mockingbird", "Eminem", R.drawable.pic8)//
    val song13 = RoomMusic(13, "Swimming Pools", "Kendrick Lamar", R.drawable.pic8)//
    val song14 = RoomMusic(14, "Agora Hills", "Doja Cat", R.drawable.pic8)//


    val songs = arrayOf(
        song1,
        song2,
        song3,
        song4,
        song5,
        song6,
        song7,
        song8,
        song9,
        song10,
        song11,
        song12,
        song13
    )

    val rock = arrayOf(song1, song2, song4, song6, song7)
    val pop = arrayOf(song3, song5, song8, song9, song10, song11)
    val rap = arrayOf(song12, song13, song14)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {


            /*search.setOnClickListener {
                (it.actionView as SearchView)
                    .apply {
                        setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                            override fun onQueryTextSubmit(query: String?): Boolean {
                                Log.d("query", "QueryTextSubmit: $query")
                                return true
                            }

                            override fun onQueryTextChange(newText: String?): Boolean {
                                newText?.let { filter(newText) } ?: "error"
                                Log.d("newText", "QueryTextChange: $newText")
                                return false
                            }

                        })
                    }
                true
            }
*/

            recyclerView.layoutManager = GridLayoutManager(view.context, 2)


            val song = dataBase.getAll()

            val rockSong = databaseRock.getAll()
            val popSong = dataBasePop.getAll()
            val rapSong = databaseRap.getAll()

            if (song.size == 0) {
                dataBase.insertSongs(*songs)

            }
            if (rockSong.size == 0) {
                databaseRock.insertSongs(*rock)
            }

            if (popSong.size == 0) {
                dataBasePop.insertSongs(*pop)
            }

            if (rapSong.size == 0) {
                databaseRap.insertSongs(*rap)
            }

            adapter = MusicListAdapter(dataBase.getAll().toMutableList()) {
                /*findNavController().navigate(
                    ListDirections.toFragment(it)
                )*/
            }
            picRock.setOnClickListener{
                adapter = MusicListAdapter(databaseRock.getAll().toMutableList()) {

                }
            }

            picPop.setOnClickListener{
                adapter = MusicListAdapter(pop.toMutableList()) {

                }
            }

            picRap.setOnClickListener{
                adapter = MusicListAdapter(rap.toMutableList()) {

                }
            }

            recyclerView.adapter = adapter

        }
    }

    private fun filter(text: String) {
        val filteredlist: ArrayList<RoomMusic> = ArrayList()
        for (item in songs) {
            if (item.name.lowercase().contains(text.lowercase())) {
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(context, "No Date found..", Toast.LENGTH_SHORT).show()
            adapter?.filterList(filteredlist)
        } else {
            adapter?.filterList(filteredlist)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

fun RecyclerView.addHorizontalSpaceDecoration(space: Int) {
    addItemDecoration(
        object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val position = parent.getChildAdapterPosition(view)
                if (position != 0 && position != parent.adapter?.itemCount) {
                    outRect.top = space
                }
            }
        }
    )
}