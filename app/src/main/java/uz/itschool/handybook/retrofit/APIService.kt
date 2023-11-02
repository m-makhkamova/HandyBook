package uz.itschool.handybook.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import uz.itschool.handybook.model.Book
import uz.itschool.handybook.model.CommentList
import uz.itschool.handybook.model.Filter
import uz.itschool.handybook.model.Login
import uz.itschool.handybook.model.MainBook
import uz.itschool.handybook.model.User

interface APIService {

    @GET("/book-api")
    fun getAllBooks(): Call<List<Book>>

    @GET("/book-api/{id}")
    fun getBook(@Query("id") id:Int): Call<Book>

    @GET("/book-api/main-book")
    fun getMainBook(): Call<MainBook>

    @POST("/book-api/login")
    fun login(@Body login: Login): Call<User>

    @POST("/book-api/register")
    fun register(@Body register: User): Call<User>

    @GET("/book-api/all-category")
    fun getAllCategories(): Call<List<Filter>>

    @GET("/products/category/")
    fun getBooksByCategory(@Query("name") name: String): Call<List<Book>>

    @GET("/book-api/comment/{id}")
    fun getComment(@Query("id") id:Int): Call<CommentList>

    @GET("/book-api/search-name/")
    fun searchByName(@Query("name") name:String):Call<List<Book>>

//    @POST("/comment-api/create")
//    fun writeComment(@Body book_id:Int, user_id:Int, text:String, rating:Double):Call<Comment>

}