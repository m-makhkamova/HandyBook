package uz.itschool.handybook.ui

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
import uz.itschool.handybook.model.BookList
import uz.itschool.handybook.model.MainBook
import uz.itschool.handybook.retrofit.APIClient
import uz.itschool.handybook.retrofit.APIService


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
        val binding = FragmentHomeBinding.inflate(inflater, container, false)

        var api = APIClient.getInstance().create(APIService::class.java)

        api.getMainBook().enqueue(object :Callback<MainBook>{
            override fun onResponse(call: Call<MainBook>, response: Response<MainBook>) {
                binding.mainBookImg.load(response.body()?.image)
                binding.mainBookName.text = "${response.body()?.author}ning '${response.body()?.name}' asari"
                Log.d("TAG", "on:${response.body()}")
            }

            override fun onFailure(call: Call<MainBook>, t: Throwable) {
                Log.d("TAG", "onFailure:$t")
            }

        })

        api.getAllCategories().enqueue(object :Callback<List<String>>{
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                val categories = response.body()!!
                binding.filter.adapter = FilterAdapter(categories, requireContext(), object :FilterAdapter.OnCLick{
                    override fun onCLick(category: String) {
                        if(category == ""){
                            api.getAllBooks().enqueue(object :Callback<BookList>{
                                override fun onResponse(
                                    call: Call<BookList>,
                                    response: Response<BookList>
                                ) {
                                    var books = response.body()?.books!!
                                    binding.booksRv.adapter = BookAdapter(books, object :BookAdapter.OnClick{
                                        override fun onItemClick(book: Book) {
                                            val bundle = Bundle()
                                            bundle.putSerializable("item", book)
                                            findNavController().navigate(R.id.action_main_to_moreFragment, bundle)
                                        }

                                    }, requireContext())
                                }

                                override fun onFailure(call: Call<BookList>, t: Throwable) {
                                    Log.d("TAG", "onFailure:$t")
                                }

                            })
                        }else{
                            api.getBooksByCategory(category).enqueue(object :Callback<BookList>{
                                override fun onResponse(
                                    call: Call<BookList>,
                                    response: Response<BookList>
                                ) {
                                    var books = response.body()?.books!!
                                    binding.booksRv.adapter = BookAdapter(books, object :BookAdapter.OnClick{
                                        override fun onItemClick(book: Book) {
                                            val bundle = Bundle()
                                            bundle.putSerializable("item", book)
                                            findNavController().navigate(R.id.action_main_to_moreFragment, bundle)
                                        }

                                    }, requireContext())
                                }

                                override fun onFailure(call: Call<BookList>, t: Throwable) {
                                    Log.d("TAG", "onFailure:$t")
                                }

                            })
                        }
                    }

                })
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Log.d("TAG", "onFailure:$t")
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