package com.example.petsdeneme2.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petsdeneme2.R
import com.example.petsdeneme2.databinding.FragmentAdoptBinding
import com.example.petsdeneme2.databinding.FragmentBlogBinding
import com.example.petsdeneme2.ui.adapter.Adoptadapter
import com.example.petsdeneme2.ui.adapter.BlogAdapter
import com.example.petsdeneme2.ui.model.AdoptIlan
import com.example.petsdeneme2.ui.model.BlogEkle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class BlogFragment : Fragment() {

    private lateinit var tasarim : FragmentBlogBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var blogArrayList : ArrayList<BlogEkle>
    private lateinit var BlogAdapter : BlogAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tasarim = FragmentBlogBinding.inflate(inflater, container, false)
        val view = tasarim.root

        auth = Firebase.auth
        db = Firebase.firestore

        blogArrayList = ArrayList<BlogEkle>()

        tasarim.toolbarBlog.title = ""
        (activity as AppCompatActivity).setSupportActionBar(tasarim.toolbarBlog)

        getData()

        tasarim.rvBlog.layoutManager = LinearLayoutManager(requireActivity())
        BlogAdapter = BlogAdapter(blogArrayList)
        tasarim.rvBlog.adapter = BlogAdapter


        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.blog_fragment_menu,menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.action_blog_ekle) {
                    // Blog ekleme sayfamız.
                    Navigation.findNavController(tasarim.toolbarBlog).navigate(R.id.blogEkleGecis)


                }
                return true
            }
        },viewLifecycleOwner, Lifecycle.State.RESUMED)




        return view
    }


    private fun getData(){ // verileri fireStore den alma fonksiyonu

        db.collection("Blog").addSnapshotListener { value, error ->
            if(error != null){
                // hata var demektir
                Toast.makeText(requireActivity(),"Bloğunuzu Yayınlarken Bir Hata Oluştu!", Toast.LENGTH_LONG).show()
            }else{
                // hata yoksa
                if(value != null){
                    if(!value.isEmpty){
                        // eğer değerler boş değilse

                        val documents = value.documents

                        blogArrayList.clear()

                        for(documentBlog in documents){
                            // artık burada document dediğim sey bu uygulama özelinde bir adet post demek
                            val userEmail = documentBlog.get("userEmail") as String
                            val blogAd = documentBlog.get("blogAd") as String
                            val downloadUrl = documentBlog.get("downloadUrl") as String
                            val blogDetayUzun = documentBlog.get("blogDetayUzun") as String
                            val blogKonu = documentBlog.get("blogKonu") as String



                            val blog = BlogEkle(userEmail,blogAd,blogKonu,blogDetayUzun,downloadUrl)
                            blogArrayList.add(blog)
                        }
                        BlogAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

    }


}