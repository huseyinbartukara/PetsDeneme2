package com.example.petsdeneme2.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petsdeneme2.databinding.HomePostPageRowBinding
import com.example.petsdeneme2.ui.model.Post
import com.squareup.picasso.Picasso

class HomePostAdapter(private val homePostList : ArrayList<Post>) : RecyclerView.Adapter<HomePostAdapter.PostHolder>() {

    class PostHolder(val tasarim: HomePostPageRowBinding) : RecyclerView.ViewHolder(tasarim.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val tasarim = HomePostPageRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(tasarim)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {

        holder.tasarim.rvHomeEmailText.text = homePostList.get(position).email
        holder.tasarim.rvHomeYorumText.text = homePostList.get(position).yorum
        Picasso.get().load(homePostList.get(position).post_resim).into(holder.tasarim.rvHomeImageView)

    }

    override fun getItemCount(): Int {
        return homePostList.size
    }


}