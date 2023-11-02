package uz.itschool.handybook.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.load
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.itschool.handybook.R
import uz.itschool.handybook.adapter.BookAdapter
import uz.itschool.handybook.databinding.FragmentHomeBinding
import uz.itschool.handybook.adapter.FilterAdapter
import uz.itschool.handybook.model.Book
import uz.itschool.handybook.model.Filter
import uz.itschool.handybook.model.MainBook
import uz.itschool.handybook.retrofit.APIClient
import uz.itschool.handybook.retrofit.APIService
import java.util.Locale.Category


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var api = APIClient.getInstance().create(APIService::class.java)
    lateinit var binding: FragmentHomeBinding
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
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        getBooks(requireContext())
        getCategories(requireContext())

        return binding.root
    }
private fun getCategories(context: Context){
    api.getAllCategories().enqueue(object :Callback<List<Filter>>{
        override fun onResponse(call: Call<List<Filter>>, response: Response<List<Filter>>) {
            val categories = response.body()!!
            binding.filter.setHasFixedSize(true)
            binding.filter.adapter = FilterAdapter(categories, context, object :FilterAdapter.OnCLick{
                override fun onCLick(category: String) {
                    if(category == ""){
                        api.getAllBooks().enqueue(object :Callback<List<Book>>{
                            override fun onResponse(
                                call: Call<List<Book>>,
                                response: Response<List<Book>>
                            ) {
                                var books = response.body()!!
                                binding.booksRv.setHasFixedSize(true)
                                binding.booksRv.adapter = BookAdapter(books, object :BookAdapter.OnClick{
                                    override fun onItemClick(book: Book) {
                                        val bundle = Bundle()
                                        bundle.putSerializable("item", book)
                                        findNavController().navigate(R.id.action_main_to_moreFragment, bundle)
                                    }

                                }, requireContext())
                            }

                            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                                Log.d("TAG", "onFailure:$t")
                            }

                        })
                    }else{

                        api.getBooksByCategory(category).enqueue(object :Callback<List<Book>>{
                            override fun onResponse(
                                call: Call<List<Book>>,
                                response: Response<List<Book>>
                            ) {
                                Log.d("TAG", "onResponse: ${response.body()}")
                                if (response.isSuccessful && response.body() != null) {
                                    var books = response.body()!!
                                    binding.booksRv.setHasFixedSize(true)
                                    binding.booksRv.adapter =
                                        BookAdapter(books, object : BookAdapter.OnClick {
                                            override fun onItemClick(book: Book) {
                                                val bundle = Bundle()
                                                bundle.putSerializable("item", book)
                                                findNavController().navigate(
                                                    R.id.action_main_to_moreFragment,
                                                    bundle
                                                )
                                            }

                                        }, requireContext())
                                    binding.booksRv.adapter
                                }
                            }

                            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                                Log.d("TAG", "onFailure:$t")
                            }

                        })
                    }
                }

            })
        }

        override fun onFailure(call: Call<List<Filter>>, t: Throwable) {
            Log.d("TAG", "onFailure:$t")
        }


    })
}

    private fun getBooks(context: Context){
        api.getAllBooks().enqueue(object :Callback<List<Book>>{
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                var books = response.body()!!
                binding.booksRv.adapter = BookAdapter(books, object :BookAdapter.OnClick{
                    override fun onItemClick(book: Book) {
                        val bundle = Bundle()
                        bundle.putSerializable("item", book)
                        findNavController().navigate(R.id.action_main_to_moreFragment, bundle)
                    }

                }, context)
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                Log.d("TAG", "onFailure: $t")
            }

        })
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}