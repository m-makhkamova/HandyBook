package uz.itschool.handybook.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import uz.itschool.handybook.databinding.FragmentMainBinding
import uz.itschool.handybook.util.ShPHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import uz.itschool.handybook.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Main.newInstance] factory method to
 * create an instance of this fragment.
 */
class Main : Fragment(), NavigationView.OnNavigationItemSelectedListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var drawerLayout: DrawerLayout? = null
    private lateinit var bottomMenu: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater, container, false)

        //val user = arguments?.getSerializable("user") as User
        val bundle = Bundle()

        var userList = ShPHelper.getInstance(requireContext()).getUser()
        var img = binding.avatar
//        for (i in userList) {
//            if (i == user) {
//                if (i.url != null) {
//                    img.setImageURI(Uri.parse(i.url))
//                }
//                else{
//                    img.setImageResource(R.drawable.user)
//                }
//            }
//        }

        drawerLayout = binding.drawerLayout
        val navigationView = binding.navView
        val toolbar = binding.toolbar
        bottomMenu = binding.bottomMenu

//        val header = binding.navView.getHeaderView(0)
//        header.findViewById<TextView>(R.id.header_title).text = user.name + " " + user.surname
//        header.findViewById<TextView>(R.id.sub_title).text = user.email
//        for (i in userList) {
//            if (i == user) {
//                if (i.url != null) {
//                    header.findViewById<ImageView>(R.id.avatar).setImageURI(Uri.parse(i.url))
//                }
//                else{
//                    img.setImageResource(R.drawable.user)
//                }
//            }
//        }



        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            requireActivity(), binding.drawerLayout, toolbar, R.string.open_nav,
            R.string.close_nav
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        if (savedInstanceState == null) {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
        loadFragment(HomeFragment())
//        binding.avatar.setOnClickListener {
//            bundle.putSerializable("user", user)
//            findNavController().navigate(R.id.action_main_to_personalFragment, bundle)
//
//        }
        bottomMenu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment())
                    toolbar.title = "Bosh Sahifa"
                    toolbar.setTitleTextAppearance(
                        requireContext(),
                        R.style.MontserratSemiBoldTextAppearance
                    )
                    navigationView.setCheckedItem(R.id.nav_home)
                    true
                }

                R.id.search -> {
                    loadFragment(SearchFragment())
                    toolbar.title = "Qidiruv"
                    toolbar.setTitleTextAppearance(
                        requireContext(),
                        R.style.MontserratSemiBoldTextAppearance
                    )
                    navigationView.setCheckedItem(R.id.nav_search)
                    true
                }


                R.id.saved -> {
                    loadFragment(SavedFragment())
                    toolbar.title = "Saqlangan kitoblar"
                    toolbar.setTitleTextAppearance(
                        requireContext(),
                        R.style.MontserratSemiBoldTextAppearance
                    )
                    navigationView.setCheckedItem(R.id.nav_saved)
                    true
                }

                R.id.settings -> {
                    loadFragment(Settings())
                    toolbar.title = "Tilni o'zgartiring"
                    toolbar.setTitleTextAppearance(
                        requireContext(),
                        R.style.MontserratSemiBoldTextAppearance
                    )
                    navigationView.setCheckedItem(R.id.nav_settings)
                    true
                }

                else -> {
                    loadFragment(HomeFragment())
                    toolbar.title = "Bosh Sahifa"
                    toolbar.setTitleTextAppearance(
                        requireContext(),
                        R.style.MontserratSemiBoldTextAppearance
                    )
                    navigationView.setCheckedItem(R.id.nav_home)
                    true
                }
            }
        }

        return binding.root
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, HomeFragment()).commit()
                bottomMenu.selectedItemId = R.id.home
            }

            R.id.nav_settings -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, Settings()).commit()
                bottomMenu.selectedItemId = R.id.settings
            }

            R.id.nav_saved -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, SavedFragment()).commit()
                bottomMenu.selectedItemId = R.id.saved
            }


            R.id.nav_search -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, SearchFragment()).commit()
                bottomMenu.selectedItemId = R.id.search
            }

            R.id.nav_logout -> findNavController().navigate(R.id.action_main_to_splash)
        }
        drawerLayout!!.closeDrawer(GravityCompat.START)
        return true
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Main.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Main().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}