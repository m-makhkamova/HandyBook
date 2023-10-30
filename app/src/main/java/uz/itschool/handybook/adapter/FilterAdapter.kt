package uz.itschool.handybook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import uz.itschool.handybook.R
import uz.itschool.handybook.databinding.FilterItemBinding
import uz.itschool.handybook.model.Filter

class FilterAdapter(
    var categories: List<String>,
    var context: Context,
    private val categoryClicked: OnCLick
) : RecyclerView.Adapter<FilterAdapter.MyHolder>() {

    var current = 0
    class MyHolder(binding: FilterItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val cardView: CardView = itemView.findViewById(R.id.back)
        val name: TextView = itemView.findViewById(R.id.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            FilterItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        if (position == 0) {
            holder.name.text = "All"
        } else {
            holder.name.text = categories[position - 1].capitalize()
        }
        if (current == position) {
            holder.cardView.setCardBackgroundColor(context.resources.getColor(R.color.dark_blue))
            holder.name.setTextColor(context.resources.getColor(R.color.white))
        }
        else {
            holder.cardView.setCardBackgroundColor(context.resources.getColor(R.color.white))
            holder.name.setTextColor(context.resources.getColor(R.color.dark_blue))
        }
        holder.cardView.setOnClickListener {
            if (position != current) {
                notifyItemChanged(current)
                current = position
                notifyItemChanged(current)
                if (position == 0) {
                    categoryClicked.onCLick("")
                }
                else{
                    categoryClicked.onCLick(categories[position - 1])
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    interface OnCLick{
        fun onCLick(category: String)
    }
}