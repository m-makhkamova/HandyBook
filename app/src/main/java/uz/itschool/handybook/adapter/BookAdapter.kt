package uz.itschool.handybook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import uz.itschool.handybook.databinding.BookItemBinding
import uz.itschool.handybook.databinding.BookItemGridBinding
import uz.itschool.handybook.model.Book

class BookAdapter(
    var bookList: List<Book>,
    var onClick: OnClick,
    var context: Context
) : RecyclerView.Adapter<BookAdapter.MyHolder>() {

    class MyHolder(binding: BookItemGridBinding) : RecyclerView.ViewHolder(binding.root) {
        var name = binding.bookName
        var img = binding.bookImg
        var author = binding.bookAuthor
        var rating = binding.ratingOfBook
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            BookItemGridBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        var book = bookList[position]
        holder.name.text = book.name
        holder.img.load(book.image)
        holder.author.text = book.author
        holder.itemView.setOnClickListener {
            onClick.onItemClick(book)
        }
        holder.rating.text = book.reyting.toString()

    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    interface OnClick {
        fun onItemClick(book: Book)
    }
}