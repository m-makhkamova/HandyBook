package uz.itschool.handybook.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import uz.itschool.handybook.model.Book
import uz.itschool.handybook.model.BookList
import uz.itschool.handybook.model.CommentList
import uz.itschool.handybook.model.Login
import uz.itschool.handybook.model.MainBook
import uz.itschool.handybook.model.User

interface APIService {

    @GET("/book-api")
    fun getAllBooks(): Call<BookList>

    @GET("/book-api/{id}")
    fun getBook(@Query("id") id:Int): Call<Book>

    @GET("/book-api/main-book")
    fun getMainBook(): Call<MainBook>
    @POST("/book-api/login")
    fun login(@Body login: Login): Call<User>

    @POST("/book-api/register")
    fun register(@Body register: User): Call<User>

    @GET("/book-api/all-category")
    fun getAllCategories(): Call<List<String>>

    @GET("/products/category/{type_name}")
    fun getProductsByCategory(@Query("type_name") type_name: String): Call<BookList>

    @GET("/book-api/comment/{id}")
    fun getComment(@Query("id") id:Int): Call<CommentList>

//    @POST("/comment-api/create")
//    fun writeComment(@Body book_id:Int, user_id:Int, text:String, rating:Double):Call<Comment>

}