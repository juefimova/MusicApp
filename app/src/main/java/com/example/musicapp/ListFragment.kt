package com.example.musicapp

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.FragmentListBinding
import com.example.musicapp.model.*
import java.util.*
import kotlin.concurrent.timer

class ListFragment : Fragment() {

    private lateinit var adapterAutoComplete: AutoCompleteAdapter
    private var itemsSongsAll: MutableList<RoomMusic> = mutableListOf()
    private lateinit var timer: Timer


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

    //private var adapter: MusicListAdapter? = null

    private var adapterRock: MusicRockListAdapter? = null
    private var adapterPop: MusicPopListAdapter? = null
    private var adapterRap: MusicRapListAdapter? = null


    companion object {
        private const val RECYCLER_ITEM_SPACE = 0
    }


    val song1 = RockType(1, "HONEY", "Måneskin", R.drawable.pic1)
    val song2 = RockType(2, "505", "Arctic Monkeys", R.drawable.pic2)
    val song3 = PopType(3, "Radio", "Lana Del Rey", R.drawable.pic3)
    val song4 = RockType(4, "Phobia", "Nothing But Thieves", R.drawable.pic4)
    val song5 = PopType(5, "Flawless", "The Neighbourhood", R.drawable.pic5)
    val song6 = RockType(6, "hometown", "cleopatrick", R.drawable.pic6)
    val song7 = RockType(7, "Figure It Out", "Royal Blood", R.drawable.pic7)
    val song8 = PopType(8, "Breezeblocks", "alt-J", R.drawable.pic8)
    val song9 = PopType(9, "Umbrella", "Rihanna", R.drawable.pic8) //
    val song10 = PopType(10, "Toxic", "Britney Spears", R.drawable.pic8)//
    val song11 = PopType(11, "Hits Different", "Taylor Swift", R.drawable.pic8)//
    val song12 = RapType(12, "Mockingbird", "Eminem", R.drawable.pic8)//
    val song13 = RapType(13, "Swimming Pools", "Kendrick Lamar", R.drawable.pic8)//
    val song14 = RapType(14, "Agora Hills", "Doja Cat", R.drawable.pic8)//


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

            /*if (song.size == 0) {
                dataBase.insertSongs(*songs)

            }*/

            if (rockSong.size == 0) {
                databaseRock.insertSongs(*rock)
            }

            if (popSong.size == 0) {
                dataBasePop.insertSongs(*pop)
            }

            if (rapSong.size == 0) {
                databaseRap.insertSongs(*rap)
            }

            adapterRock = MusicRockListAdapter(databaseRock.getAll().toMutableList()) {
                findNavController().navigate(
                    ListFragmentDirections.toSong(it)
                )
            }

            adapterPop = MusicPopListAdapter(dataBasePop.getAll().toMutableList()) {
                findNavController().navigate(
                    ListFragmentDirections.toSong(it)
                )

            }

            adapterRap = MusicRapListAdapter(databaseRap.getAll().toMutableList()) {
                findNavController().navigate(
                    ListFragmentDirections.toSong(it)
                )
            }

            recyclerView.adapter = adapterRock


            picRock.setOnClickListener {
                recyclerView.adapter = adapterRock
            }

            picPop.setOnClickListener {
                recyclerView.adapter = adapterPop
            }

            picRap.setOnClickListener {
                recyclerView.adapter = adapterRap
            }

            itemsSongsAll = dataBase.getAll().toMutableList()
            adapterAutoComplete = AutoCompleteAdapter(requireContext(), itemsSongsAll)
            actvSearch.setAdapter(adapterAutoComplete)
            actvSearch.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable?) {
                    Log.d("->", "afterTextChanged")
                    if (actvSearch.isPerformingCompletion()) {
                        return
                    } else {
                        binding.pbSearch.visibility = View.VISIBLE

                        timer = Timer()
                        timer.schedule(object : TimerTask() {
                            override fun run() {
                                if (!s.isNullOrBlank()) {
                                    activity?.runOnUiThread {
                                        getDataSongsList(s.toString())
                                    }
                                } else {
                                    activity?.runOnUiThread {
                                        binding.pbSearch.visibility = View.GONE
                                    }
                                }
                            }
                        }, 500)
                    }

                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    Log.d("beforeTextChanged", "yep")
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    Log.d("onTextChanged", "yep")
                    if (::timer.isInitialized) {
                        timer.cancel()
                    }
                }

            })

            actvSearch.setOnItemClickListener{ parent, _, position, _ ->
                val selectedItem = parent.adapter.getItemId(position) as RoomMusic
                actvSearch.setText(selectedItem.name, false)
                hideKeyboard()
                actvSearch.clearFocus()
                Log.d("selectedItem ->", selectedItem.name)

            }


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getDataSongsList(keyword: String) {
        itemsSongsAll = dataBase.getAll().toMutableList()
        Log.d("itemsBookAll -> ", itemsSongsAll.toString())
        val filteredList = itemsSongsAll.filter{
            it.name.contains(keyword, ignoreCase = true)
        }
        itemsSongsAll.clear()
        itemsSongsAll.addAll(filteredList)
        adapterAutoComplete.filterList(filteredList.toMutableList())
        binding.pbSearch.visibility = View.GONE
    }

    fun hideKeyboard() {
        val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
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