package com.example.petsdeneme2.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petsdeneme2.MainActivity
import com.example.petsdeneme2.R
import com.example.petsdeneme2.databinding.FragmentAdoptBinding
import com.example.petsdeneme2.databinding.FragmentGirisYapBinding
import com.example.petsdeneme2.ui.adapter.Adoptadapter
import com.example.petsdeneme2.ui.model.AdoptIlan
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class AdoptFragment : Fragment() {

    private lateinit var tasarim : FragmentAdoptBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var adoptArrayList : ArrayList<AdoptIlan>
    private lateinit var AdoptAdapter : Adoptadapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tasarim = FragmentAdoptBinding.inflate(inflater, container, false)
        val view = tasarim.root

        auth = Firebase.auth
        db = Firebase.firestore

        adoptArrayList = ArrayList<AdoptIlan>()


        tasarim.toolbarAdopt.title = ""
        (activity as AppCompatActivity).setSupportActionBar(tasarim.toolbarAdopt)

        getData()

        tasarim.rv.layoutManager = LinearLayoutManager(requireActivity())
        AdoptAdapter = Adoptadapter(adoptArrayList)
        tasarim.rv.adapter = AdoptAdapter


        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.adopt_fragment_menu,menu)

                //val item = menu.findItem(R.id.action_cikis)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.action_ekle) {
                    // ilan ekleme sayfamız.
                    Navigation.findNavController(tasarim.toolbarAdopt).navigate(R.id.ilanVermeGecis)


                }
                else if(menuItem.itemId == R.id.action_ara){

                }
                return true
            }
        },viewLifecycleOwner, Lifecycle.State.RESUMED)




        return view
    }

    private fun getData(){ // verileri fireStore den alma fonksiyonu

        db.collection("Ilan").addSnapshotListener { value, error ->
            if(error != null){
                // hata var demektir
                Toast.makeText(requireActivity(),"Ilan Yayınlarken Bir Hata Oluştu!", Toast.LENGTH_LONG).show()
            }else{
                // hata yoksa
                if(value != null){
                    if(!value.isEmpty){
                        // eğer değerler boş değilse

                        val documents = value.documents

                        adoptArrayList.clear()

                        for(document in documents){
                            // artık burada document dediğim sey bu uygulama özelinde bir adet post demek
                            val userEmail = document.get("userEmail") as String
                            val hayvanAd = document.get("hayvanAd") as String
                            val downloadUrl = document.get("dowloadUrl") as String
                            val hayvanYas = document.get("hayvanYas") as String
                            val hayvanCinsiyet = document.get("hayvanCinsiyet") as String
                            val hayvanCins = document.get("hayvanCins") as String
                            val hayvanKarne = document.get("hayvanKarne") as String
                            val hayvanSehir = document.get("hayvanSehir") as String


                            val ilan = AdoptIlan(userEmail,hayvanAd,hayvanYas,hayvanCinsiyet,hayvanCins,hayvanKarne,hayvanSehir,downloadUrl)
                            adoptArrayList.add(ilan)
                        }
                        AdoptAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

    }




}