package uz.itschool.handybook.model

import java.io.Serializable

data class Filter(
    var name: String,
    var state: Boolean = false
) : Serializable
