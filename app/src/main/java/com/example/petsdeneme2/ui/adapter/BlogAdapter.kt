package com.example.petsdeneme2.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.petsdeneme2.R
import com.example.petsdeneme2.databinding.AdoptIlanRowBinding
import com.example.petsdeneme2.databinding.BlogMyblogRowBinding
import com.example.petsdeneme2.ui.fragment.BlogFragment
import com.example.petsdeneme2.ui.fragment.BlogFragmentDirections
import com.example.petsdeneme2.ui.model.AdoptIlan
import com.example.petsdeneme2.ui.model.BlogEkle
import com.squareup.picasso.Picasso



class BlogAdapter(private val BlogList : ArrayList<BlogEkle>) : RecyclerView.Adapter<BlogAdapter.BlogEkleHolder>() {


    class BlogEkleHolder(val tasarim: BlogMyblogRowBinding) : RecyclerView.ViewHolder(tasarim.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogEkleHolder {
        val tasarim = BlogMyblogRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BlogEkleHolder(tasarim)
    }

    override fun onBindViewHolder(holder: BlogEkleHolder, position: Int) {
        holder.tasarim.textViewBlogEmail.text = BlogList.get(position).email
        holder.tasarim.textViewBlogAd.text = BlogList.get(position).blog_ad
        holder.tasarim.textViewBlogKonu.text = "Bu Bloğumda'${BlogList.get(position).blog_konu}' konusu üzerinde konuştum"
        Picasso.get().load(BlogList.get(position).blog_resim).into(holder.tasarim.imageViewBlog)

        val blog = BlogList.get(position)





        holder.tasarim.buttonBlogDetay.setOnClickListener {

            val gecis = BlogFragmentDirections.blogDetayGecis(blog = blog)
            Navigation.findNavController(it).navigate(gecis)

        }


    }

    override fun getItemCount(): Int {
        return BlogList.size
    }

}