package com.example.petsdeneme2.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.petsdeneme2.R
import com.example.petsdeneme2.databinding.FragmentBlogBinding
import com.example.petsdeneme2.databinding.FragmentBlogDetayBinding
import com.example.petsdeneme2.ui.adapter.BlogAdapter
import com.example.petsdeneme2.ui.model.BlogEkle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class BlogDetayFragment : Fragment() {

    private lateinit var tasarim : FragmentBlogDetayBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tasarim = FragmentBlogDetayBinding.inflate(inflater, container, false)
        val view = tasarim.root

        val bundle:BlogDetayFragmentArgs by navArgs()
        val gelenBlog = bundle.blog

        tasarim.textViewBlogDetayEmail.text = gelenBlog.email
        tasarim.textViewBlogDetayText.text = gelenBlog.blog_detay
        tasarim.textViewBlogDetayBlogKonu.text = gelenBlog.blog_konu




        return view
    }



}