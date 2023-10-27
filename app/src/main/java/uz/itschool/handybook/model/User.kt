package uz.itschool.handybook.model

import android.net.Uri
import java.io.Serializable

data class User(
    var name: String,
    var surname: String,
    var email: String,
    var password: String,
    var status: Boolean = false,
    var url: String? = null,
    var access_token: String
): Serializable
