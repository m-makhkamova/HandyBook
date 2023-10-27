package uz.itschool.handybook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.itschool.handybook.databinding.FilterItemBinding
import uz.itschool.handybook.model.Filter

class FilterAdapter(
    var filterList: MutableList<Filter>,
    var myBook: MyBook,
    var context: Context
) : RecyclerView.Adapter<FilterAdapter.MyHolder>() {

    class MyHolder(binding: FilterItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var name = binding.name
        var back = binding.back
        var container = binding.container
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            FilterItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        var filter = filterList[position]
        holder.name.text = filter.name

        holder.itemView.setOnClickListener {
            myBook.onItemClick(filter)
        }
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    interface MyBook {
        fun onItemClick(filter: Filter)
    }
}