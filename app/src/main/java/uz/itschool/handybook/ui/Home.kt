package uz.itschool.handybook.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.itschool.handybook.databinding.FragmentHomeBinding
import uz.itschool.handybook.model.Filter
import uz.itschool.handybook.R
import uz.itschool.handybook.adapter.FilterAdapter
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
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {
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
        api.getMainBook().enqueue(object :retrofit2.Callback<MainBook>{
            override fun onResponse(call: Call<MainBook>, response: Response<MainBook>) {
                binding.mainBookImg.load(response.body()?.image)
                binding.mainBookName.text = "${response.body()?.author}ning '${response.body()?.name}' asari"
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
                                    TODO("Not yet implemented")
                                }

                                override fun onFailure(call: Call<BookList>, t: Throwable) {
                                    TODO("Not yet implemented")
                                }

                            })
                        }
                    }

                })
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                TODO("Not yet implemented")
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
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}