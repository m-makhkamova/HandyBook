package uz.itschool.handybook.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import retrofit2.Call
import retrofit2.Response
import uz.itschool.handybook.databinding.FragmentSearchBinding
import uz.itschool.handybook.R
import uz.itschool.handybook.adapter.BookAdapter
import uz.itschool.handybook.model.Book
import uz.itschool.handybook.retrofit.APIClient
import uz.itschool.handybook.retrofit.APIService

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    var searchLast = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSearchBinding.inflate(inflater, container, false)
        var api = APIClient.getInstance().create(APIService::class.java)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText == searchLast) return false
                api.searchByName(newText!!).enqueue(object :retrofit2.Callback<List<Book>> {
                    override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                        val books = response.body()!!
                        binding.searchedRv.adapter = BookAdapter(books, object: BookAdapter.OnClick{
                            override fun onItemClick(book: Book) {
                                val bundle = Bundle()
                                bundle.putSerializable("item", book)
                                findNavController().navigate(
                                    R.id.action_main_to_moreFragment,
                                    bundle)
                            }
                        }, requireContext())
                    }

                    override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                        Log.d("TAG", "onFailure:$t")
                    }


                })
                searchLast = newText

                return true
            }


        })

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Search.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}