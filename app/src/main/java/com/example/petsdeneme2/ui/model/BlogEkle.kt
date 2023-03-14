package com.example.petsdeneme2.ui.model

import java.io.Serializable

data class BlogEkle(val email:String,
                    val blog_ad:String,
                    val blog_konu:String,
                    val blog_detay:String,
                    val blog_resim:String) : Serializable  {
}