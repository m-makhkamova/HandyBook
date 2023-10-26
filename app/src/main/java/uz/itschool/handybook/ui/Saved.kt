package uz.itschool.handybook.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import uz.itschool.handybook.adapter.BookGridAdapter
import uz.itschool.handybook.databinding.FragmentSavedBinding
import uz.itschool.handybook.model.Books
import uz.itschool.handybook.util.ShPHelper
import uz.itschool.handybook.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Saved.newInstance] factory method to
 * create an instance of this fragment.
 */
class Saved : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var listRoman: MutableList<Books>

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
        var binding = FragmentSavedBinding.inflate(inflater, container, false)
        listRoman = ShPHelper.getInstance(requireContext()).getBooks()
        var adapter = BookGridAdapter(listRoman, object : BookGridAdapter.MyBook {
            override fun onItemClick(book: Books) {
                val bundle = bundleOf("book" to book)
                findNavController().navigate(R.id.action_main_to_moreFragment, bundle)
            }
        }, requireContext())
        binding.rv.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding.rv.adapter = adapter
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Saved.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Saved().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}