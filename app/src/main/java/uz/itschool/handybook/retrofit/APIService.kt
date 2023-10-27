package uz.itschool.handybook.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import uz.itschool.handybook.model.Book
import uz.itschool.handybook.model.BookList
import uz.itschool.handybook.model.Login
import uz.itschool.handybook.model.User

interface APIService {

    @GET("/book-api")
    fun getAllBooks(): Call<BookList>

    @GET("/book-api/{id}")
    fun getBook(@Path("id") id:Int): Call<Book>

    @POST("/book-api/login")
    fun login(@Body login: Login): Call<User>

    @POST("/book-api/register")
    fun register(@Body register: User): Call<User>

    @GET("/book-api/main-book")
    fun getMainBook(): Call<Book>

    @GET("/book-api/all-category")
    fun getAllCategories(): Call<List<String>>

    @GET("/products/category/{type_name}")
    fun getProductsByCategory(@Path("type_name") type_name: String): Call<BookList>


}