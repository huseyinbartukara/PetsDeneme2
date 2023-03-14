package com.example.petsdeneme2.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petsdeneme2.databinding.ProfilePostPageRowBinding
import com.example.petsdeneme2.ui.model.Post
import com.squareup.picasso.Picasso

class ProfilePostAdapter(private val postList : ArrayList<Post>) : RecyclerView.Adapter<ProfilePostAdapter.PostHolder>() {

    class PostHolder(val tasarim:ProfilePostPageRowBinding) :RecyclerView.ViewHolder(tasarim.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val tasarim = ProfilePostPageRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(tasarim)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        Picasso.get().load(postList.get(position).post_resim).into(holder.tasarim.rvProfileImageView)
    }

    override fun getItemCount(): Int {
        return postList.size
    }


}