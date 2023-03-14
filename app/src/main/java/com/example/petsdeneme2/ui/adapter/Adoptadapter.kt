package com.example.petsdeneme2.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petsdeneme2.databinding.AdoptIlanRowBinding
import com.example.petsdeneme2.ui.model.AdoptIlan
import com.squareup.picasso.Picasso

class Adoptadapter(private val AdoptIlanList : ArrayList<AdoptIlan>) : RecyclerView.Adapter<Adoptadapter.AdoptIlanHolder>() {
    class AdoptIlanHolder(val tasarim: AdoptIlanRowBinding) : RecyclerView.ViewHolder(tasarim.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdoptIlanHolder {
        val tasarim = AdoptIlanRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AdoptIlanHolder(tasarim)
    }

    override fun onBindViewHolder(holder: AdoptIlanHolder, position: Int) {

        holder.tasarim.textViewEmailRow.text = AdoptIlanList.get(position).email
        holder.tasarim.textViewAciklamaRow.text = "Dostumun Adı: ${AdoptIlanList.get(position).hayvan_ad}, ${AdoptIlanList.get(position).hayvan_yas} yaşında ve ${AdoptIlanList.get(position).hayvan_cinsiyet}. Sağlık Karnesi Var mı: ${AdoptIlanList.get(position).hayvan_karne}"
        Picasso.get().load(AdoptIlanList.get(position).hayvan_resim).into(holder.tasarim.imageViewRow)

        holder.tasarim.favoriEkleButton.setOnClickListener {
            // burada favori ekleme özelliği olucak
        }

        holder.tasarim.mesajAtButton.setOnClickListener {
            // burada mesaj atma özelliği olucak
        }

        /*holder.binding.recyclerEmailText.text = postList.get(position).email
        holder.binding.recyclerCommentText.text = postList.get(position).comment
        Picasso.get().load(postList.get(position).downloadUrl).into(holder.binding.recyclerImageView)*/
    }

    override fun getItemCount(): Int {
        return AdoptIlanList.size
    }

}