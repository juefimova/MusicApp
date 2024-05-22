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
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.FragmentListBinding
import com.example.musicapp.model.*
import java.util.*

class ListFragment : Fragment() {

    private lateinit var adapterAutoCompleteRock: AutoCompleteAdapterRock
    private lateinit var adapterAutoCompletePop: AutoCompleteAdapterPop
    private lateinit var adapterAutoCompleteRap: AutoCompleteAdapterRap
    private var itemsSongsRock: MutableList<RockType> = mutableListOf()
    private var itemsSongsPop: MutableList<PopType> = mutableListOf()
    private var itemsSongsRap: MutableList<RapType> = mutableListOf()

    var musicType = "Rock"

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


    val song1 = RockType(1, "HONEY", "Måneskin", R.drawable.pic1, "24 787 115",
        "With their raw, energetic and distorted sound Mäneskin" +
            " are meeting contemporary tastes and bringing back" +
            " rock'n'roll at the top of international charts.\n" +
            "They started as buskers playing in the streets" +
            " of Rome in 2015 and, within a few years, made" +
            " it to be the first Italian artist to spread worldwide.\n" +
            "Victoria (bass), Damiano (lead singer), Thomas (guitar)" +
            " and Ethan (drums), all in their twenties, are rebooting" +
            " rock 'n' roll for a new generation of listeners.", "${R.drawable.pic1},${R.drawable.pic1}," +
                "${R.drawable.pic1},${R.drawable.pic1}")
    val song2 = RockType(2, "505", "Arctic Monkeys", R.drawable.pic2, "50 056 736",
        "Arctic Monkeys is an indie rock band formed" +
            " in Sheffield, South Yorkshire, England in 2002 after meeting at Stocksbridge High School." +
            " The band consists of Alex Turner (vocals, guitar, piano), Jamie Cook (guitar), " +
            "Nick O'Malley (backing vocals, bass) and Matt Helders (drums, vocals).", "${R.drawable.pic2},${R.drawable.pic2}")
    val song3 = PopType(3, "Radio", "Lana Del Rey", R.drawable.pic3, "55 154 759",
    "Lana Del Rey is an American singer, songwriter and record producer, whose real name" +
            " is Elizabeth Woolridge Grant. Del Rey rose to fame in 2011 with her debut single “Video Games”" +
            " and, shortly after, the album “Born to Die.” She is most well known" +
            " for her melancholic style of pop music.", "${R.drawable.pic3},${R.drawable.pic3}, ${R.drawable.pic3}")
    val song4 = RockType(4, "Phobia", "Nothing But Thieves", R.drawable.pic4, "4 456 598",
    "Hailing from Southend-on-Sea in Essex, England, Nothing But Thieves make" +
            " passionate guitar-based rock that balances" +
            " indie rock artfulness with a pop sensibility.", "${R.drawable.pic4},${R.drawable.pic4}")
    val song5 = PopType(5, "Flawless", "The Neighbourhood", R.drawable.pic5, "40 187 333",
    "The Neighbourhood (sometimes rendered as \"THE NBHD\") is an American alternative" +
            " rock band that originated in Newbury Park, California in 2011. The band's members" +
            " currently consist of singer Jesse Rutherford, guitarists Jeremy Freedman and Zach Abels," +
            " and bassist Mikey Margott. They are currently signed to Columbia Records.", "${R.drawable.pic5},${R.drawable.pic5},${R.drawable.pic5}")
    val song6 = RockType(6, "hometown", "cleopatrick", R.drawable.pic6, "424 853",
    "guitar band", "${R.drawable.pic6},${R.drawable.pic6}")
    val song7 = RockType(7, "Figure It Out", "Royal Blood", R.drawable.pic7, "2 897 523",
    "Royal Blood is an English rock duo formed in Worthing in 2011. The current lineup consists" +
            " of Mike Kerr (vocals, bass guitar, piano) and Ben Thatcher (drums). Their signature sound is" +
            " built around Kerr's bass playing style, which sees him using various effects pedals and " +
            "amps to make his bass guitar sound like an electric guitar and bass guitar at the same time." +
            " The duo were signed by Warner Chappell Music in 2013 and have since released four studio albums:" +
            " Royal Blood (2014), How Did We Get So Dark? (2017), Typhoons (2021), and Back to the Water Below (2023).",
        "${R.drawable.pic7},${R.drawable.pic7}")
    val song8 = PopType(8, "Breezeblocks", "alt-J", R.drawable.pic8,
    "10 180 405", "Alt-J (stylised as alt-J, real name Δ) are an English" +
                " indie rock band formed in 2007 in Leeds. Their lineup includes Joe Newman" +
                " (guitar/lead vocals), Thom Sonny Green (drums), Gus Unger-Hamilton (keyboards/vocals)," +
                " and formerly Gwil Sainsbury (guitar/bass).", "${R.drawable.pic8},${R.drawable.pic8}")
    val song9 = PopType(9, "Umbrella", "Rihanna", R.drawable.pic9, "84 555 520",
    "Rihanna (born February 20, 1988, St. Michael parish, Barbados) is a Barbadian" +
            " pop and rhythm-and-blues (R&B) singer who became a worldwide star in the early" +
            " 21st century. She is known for her distinctive and versatile voice and for her" +
            " fashionable appearance. She is also known for her beauty and fashion lines.",
        "${R.drawable.pic9},${R.drawable.pic9},${R.drawable.pic9}") //
    val song10 = PopType(10, "Toxic", "Britney Spears", R.drawable.pic10,
    "38 975 967", "Britney Jean Spears (born December 2, 1981) is an American singer." +
                " Often referred to as the \"Princess of Pop\", she is credited with influencing" +
                " the revival of teen pop during the late 1990s and early 2000s." +
                " Spears has sold over 150 million records worldwide, making her" +
                " one of the world's best-selling music artists.", "${R.drawable.pic10},${R.drawable.pic10},${R.drawable.pic10}")
    val song11 = PopType(11, "Hits Different", "Taylor Swift", R.drawable.pic11,
    "113 731 730","Taylor Alison Swift (born December 13, 1989)" +
            " is an American singer-songwriter. A subject of widespread public " +
            "interest with a vast fanbase, she has influenced the music industry" +
            ", popular culture, and politics through her songwriting, " +
            "artistry, entrepreneurship, and advocacy.", "${R.drawable.pic11},${R.drawable.pic11},${R.drawable.pic11}")
    val song12 = RapType(12, "Mockingbird", "Eminem", R.drawable.pic12,
    "66 665 341", "Eminem is an American rapper, songwriter," +
                " record producer, record executive and actor. He is one of the" +
                " most successful artists of the 21st century. In addition to his" +
                " solo career, Eminem was a member of the hip hop group D12.", "${R.drawable.pic12},${R.drawable.pic12}")
    val song13 = RapType(13, "Swimming Pools", "Kendrick Lamar", R.drawable.pic13, "72 910 646",
    "Kendrick Lamar Duckworth (born June 17, 1987) is an American " +
            " and songwriter. Often regarded as one of the greatest rappers" +
            " of all time, he is the only musician outside of the classical" +
            " and jazz genres to be awarded the Pulitzer Prize for Music.", "${R.drawable.pic13},${R.drawable.pic13}")
    val song14 = RapType(14, "Agora Hills", "Doja Cat", R.drawable.pic14, "58 195 152",
    "Amala Ratna Zandile Dlamini (Zulu pronunciation: [ˈzandile ˈɮamini];" +
            " born October 21, 1995), known professionally as Doja Cat (/ˈdoʊdʒə/)," +
            " is an American rapper, singer, and songwriter based in Los Angeles, California." +
            " She began making and releasing music on SoundCloud as a teenager.", "${R.drawable.pic14},${R.drawable.pic14}")


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


            recyclerView.layoutManager = GridLayoutManager(view.context, 2)

            val rockSong = databaseRock.getAll()
            val popSong = dataBasePop.getAll()
            val rapSong = databaseRap.getAll()

            recyclerView.adapter = adapterRock
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
                    ListFragmentDirections.toSong(it,
                    PopType(0, "0", "0", 0, "0", "0", "0"),
                    RapType(0,"0", "0", 0, "0", "0", "0")
                        )
                )
            }

            adapterPop = MusicPopListAdapter(dataBasePop.getAll().toMutableList()) {

                findNavController().navigate(
                    ListFragmentDirections.toSong(
                        RockType(0, "0", "0", 0, "0", "0", "0"),
                        it,
                    RapType(0,"0", "0", 0, "0", "0", "0")
                        )
                )

            }

            adapterRap = MusicRapListAdapter(databaseRap.getAll().toMutableList()) {

                findNavController().navigate(
                    ListFragmentDirections.toSong(
                        RockType(0, "0", "0", 0, "0", "0", "0"),
                        PopType(0, "0", "0", 0,"0", "0", "0"),
                        it)
                )
            }



            itemsSongsRock = databaseRock.getAll().toMutableList()
            itemsSongsPop = dataBasePop.getAll().toMutableList()
            itemsSongsRap = databaseRap.getAll().toMutableList()

            adapterAutoCompleteRock = AutoCompleteAdapterRock(requireContext(), itemsSongsRock)
            adapterAutoCompletePop = AutoCompleteAdapterPop(requireContext(), itemsSongsPop)
            adapterAutoCompleteRap = AutoCompleteAdapterRap(requireContext(), itemsSongsRap)


            picRock.setOnClickListener {
                musicType = "Rock"
                recyclerView.adapter = adapterRock
                actvSearch.setAdapter(adapterAutoCompleteRock)
            }

            picPop.setOnClickListener {
                musicType = "Pop"
                recyclerView.adapter = adapterPop
                actvSearch.setAdapter(adapterAutoCompletePop)
            }

            picRap.setOnClickListener {
                musicType = "Rap"
                recyclerView.adapter = adapterRap
                actvSearch.setAdapter(adapterAutoCompleteRap)
            }

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
                val selectedItemRock = parent.adapter.getItem(position) as RockType
                val selectedItemPop = parent.adapter.getItem(position) as PopType
                val selectedItemRap = parent.adapter.getItem(position) as RapType
                if (musicType == "Rock") {
                    actvSearch.setText(selectedItemRock.name, false)
                    hideKeyboard()
                    actvSearch.clearFocus()
                    Log.d("selectedItem ->", selectedItemRock.name)
                    findNavController().navigate(
                        ListFragmentDirections.toSong(selectedItemRock,
                            PopType(0, "0", "0", 0, "0", "0", "0"),
                            RapType(0,"0", "0", 0, "0", "0", "0")
                        )
                    )
                } else if(musicType == "Pop") {
                actvSearch.setText(selectedItemPop.name, false)
                hideKeyboard()
                actvSearch.clearFocus()
                Log.d("selectedItem ->", selectedItemPop.name)
                findNavController().navigate(
                    ListFragmentDirections.toSong(
                        RockType(0, "0", "0", 0, "0", "0", "0"),
                        selectedItemPop,
                        RapType(0,"0", "0", 0, "0", "0", "0")
                    )
                )
            }
                else if(musicType == "Rap") {
                    actvSearch.setText(selectedItemRap.name, false)
                    hideKeyboard()
                    actvSearch.clearFocus()
                    Log.d("selectedItem ->", selectedItemRap.name)
                    findNavController().navigate(
                        ListFragmentDirections.toSong(
                            RockType(0, "0", "0", 0, "0", "0", "0"),
                            PopType(0, "0", "0", 0, "0", "0", "0"),
                            selectedItemRap
                        )
                    )
                }

            }


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getDataSongsList(keyword: String) {
        //разные три списка
        itemsSongsRock = databaseRock.getAll().toMutableList()
        itemsSongsPop = dataBasePop.getAll().toMutableList()
        itemsSongsRap = databaseRap.getAll().toMutableList()
        Log.d("itemsBookAll -> ", itemsSongsRock.toString())

        if(musicType == "Rock") {
            val filteredList = itemsSongsRock.filter{
                it.name.contains(keyword, ignoreCase = true)
            }
            itemsSongsRock.clear()
            itemsSongsRock.addAll(filteredList)

            adapterAutoCompleteRock.filterList(filteredList.toMutableList())
            binding.pbSearch.visibility = View.GONE
        }
        else if(musicType == "Pop") {
            val filteredList = itemsSongsPop.filter{
                it.name.contains(keyword, ignoreCase = true)
            }
            itemsSongsPop.clear()
            itemsSongsPop.addAll(filteredList)

            adapterAutoCompletePop.filterList(filteredList.toMutableList())
            binding.pbSearch.visibility = View.GONE
        }
        else if(musicType == "Rap") {
            val filteredList = itemsSongsRap.filter{
                it.name.contains(keyword, ignoreCase = true)
            }
            itemsSongsRap.clear()
            itemsSongsRap.addAll(filteredList)

            adapterAutoCompleteRap.filterList(filteredList.toMutableList())
            binding.pbSearch.visibility = View.GONE
        }
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