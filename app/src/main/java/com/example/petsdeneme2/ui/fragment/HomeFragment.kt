package com.example.petsdeneme2.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petsdeneme2.R
import com.example.petsdeneme2.databinding.FragmentAdoptBinding
import com.example.petsdeneme2.databinding.FragmentHomeBinding
import com.example.petsdeneme2.ui.adapter.HomePostAdapter
import com.example.petsdeneme2.ui.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {
    private lateinit var tasarim : FragmentHomeBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var homePostArrayList : ArrayList<Post>
    private lateinit var homePostAdapter : HomePostAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tasarim = FragmentHomeBinding.inflate(inflater, container, false)
        val view = tasarim.root


        auth = Firebase.auth
        db = Firebase.firestore

        homePostArrayList = ArrayList<Post>()

        getDataHomePost()

        tasarim.rvHome.layoutManager = LinearLayoutManager(requireActivity())
        homePostAdapter = HomePostAdapter(homePostArrayList)
        tasarim.rvHome.adapter = homePostAdapter



        return view
    }

    private fun getDataHomePost(){ // verileri fireStore den alma fonksiyonu

        db.collection("Posts").orderBy("tarih",
            Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if(error != null){
                // hata var demektir
                Toast.makeText(requireActivity(),error.localizedMessage, Toast.LENGTH_LONG).show()
            }else{
                // hata yoksa
                if(value != null){
                    if(!value.isEmpty){
                        // eğer değerler boş değilse

                        val documents = value.documents

                        homePostArrayList.clear()

                        for(document in documents){
                            // artık burada document dediğim sey bu uygulama özelinde bir adet post demek
                            val yorum = document.get("yorum") as String
                            val userEmail = document.get("userEmail") as String
                            val downloadUrlProfilePost = document.get("downloadUrlProfilePost") as String
                            val homePost = Post(userEmail,yorum,downloadUrlProfilePost)
                            homePostArrayList.add(homePost)
                        }
                        homePostAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

    }



}