package com.example.petsdeneme2.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.View.inflate
import androidx.fragment.app.Fragment
import com.example.petsdeneme2.MainActivity
import com.example.petsdeneme2.R
import com.example.petsdeneme2.databinding.ActivityMainBinding.inflate
import com.example.petsdeneme2.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.zip.Inflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petsdeneme2.ui.adapter.ProfilePostAdapter
import com.example.petsdeneme2.ui.model.BlogEkle
import com.example.petsdeneme2.ui.model.HayvanProfile
import com.example.petsdeneme2.ui.model.KullaniciProfile
import com.example.petsdeneme2.ui.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {

    private lateinit var tasarim : FragmentProfileBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var kullaniciProfilArrayList : ArrayList<KullaniciProfile>
    private lateinit var kullaniciProfil :KullaniciProfile
    private lateinit var hayvanProfil : HayvanProfile
    private lateinit var postArrayList : ArrayList<Post>
    private lateinit var profilePostAdapter : ProfilePostAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tasarim = FragmentProfileBinding.inflate(inflater, container, false)
        val view = tasarim.root



        auth = Firebase.auth
        db = Firebase.firestore

        postArrayList = ArrayList<Post>()

        getDataKullanici()
        getDataHayvan()
        getDataProfilePost()

        tasarim.rvProfileFotoGosterici.layoutManager = GridLayoutManager(requireActivity(),3)
        profilePostAdapter = ProfilePostAdapter(postArrayList)
        tasarim.rvProfileFotoGosterici.adapter = profilePostAdapter


        kullaniciProfilArrayList = ArrayList<KullaniciProfile>()

        tasarim.toolbarProfile.title = ""
        (activity as AppCompatActivity).setSupportActionBar(tasarim.toolbarProfile)




        tasarim.imageViewProfilDuzenle.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.kullaniciProfileFragmentGecis)
        }

        tasarim.imageViewHayvanProfilDuzenle.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.hayvanProfileDuzenleGecis)
        }

        tasarim.ButtonPostEkle.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.postEkleFragmentGecis)
        }




        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.profile_fragment_options_menu,menu)

                //val item = menu.findItem(R.id.action_cikis)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.action_cikis) {
                    auth.signOut() // çıkıs yapma işlemi
                    val intent = Intent(requireActivity(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                return true
            }
        },viewLifecycleOwner, Lifecycle.State.RESUMED)


        return view

    }


    private fun getDataKullanici(){ // verileri fireStore den alma fonksiyonu

        db.collection("KullaniciProfile").addSnapshotListener { value, error ->
            if(error != null){
                // hata var demektir
                Toast.makeText(requireActivity(),"Bir Hata Oluştu!", Toast.LENGTH_LONG).show()
            }else{
                // hata yoksa
                if(value != null){
                    if(!value.isEmpty){
                        // eğer değerler boş değilse

                        val documents = value.documents



                        for(documentKullaniciProfile in documents){
                            // artık burada document dediğim sey bu uygulama özelinde bir adet post demek
                            val userEmail = documentKullaniciProfile.get("userEmail") as String
                            val profilDetay = documentKullaniciProfile.get("kullaniciProfileDetay") as String
                            val downloadUrlKullaniciProfil = documentKullaniciProfile.get("downloadUrlKullaniciProfil") as String




                             kullaniciProfil = KullaniciProfile(userEmail,profilDetay,downloadUrlKullaniciProfil)

                            val userEmailActive = auth.currentUser?.email as String

                            if(kullaniciProfil.email.equals(userEmailActive)){
                                tasarim.textViewKullaniciAdi.text = kullaniciProfil.email
                                tasarim.textViewProfilDetay.text = kullaniciProfil.profilDetay
                                Picasso.get().load(kullaniciProfil.profile_resim).into(tasarim.imageViewKullaniciProfil);
                            }
                        }
                    }
                }
            }
        }

    }

    private fun getDataHayvan(){

        db.collection("HayvanProfile").addSnapshotListener { value, error ->
            if(error != null){
                // hata var demektir
                Toast.makeText(requireActivity(),"Bir Hata Oluştu!", Toast.LENGTH_LONG).show()
            }else{
                // hata yoksa
                if(value != null){
                    if(!value.isEmpty){
                        // eger değerler boş değilse
                        val documents = value.documents

                        for(documentHayvanProfile in documents){
                            val userEmail = documentHayvanProfile.get("userEmail") as String
                            val evcilHayvanAd = documentHayvanProfile.get("evcilHayvanAd") as String
                            val evcilHayvanYas = documentHayvanProfile.get("evcilHayvanYas") as String
                            val evcilHayvanCins = documentHayvanProfile.get("evcilHayvanCins") as String
                            val evcilHayvanCinsiyet = documentHayvanProfile.get("evcilHayvanCinsiyet") as String
                            val downloadurlHayvanProfile = documentHayvanProfile.get("downloadUrlHayvanProfil") as String

                            hayvanProfil = HayvanProfile(userEmail,evcilHayvanAd,evcilHayvanYas,evcilHayvanCins,evcilHayvanCinsiyet,downloadurlHayvanProfile)

                            val userEmailActive = auth.currentUser?.email as String

                            if(hayvanProfil.email.equals(userEmailActive)){
                                tasarim.textViewHayvanAdi.text = hayvanProfil.evcilHayvanAd
                                Picasso.get().load(hayvanProfil.evcilHayvanResim).into(tasarim.imageViewHayvanProfil)
                                tasarim.textViewHayvanProfilDetay.text = "Dostumun Adı: ${hayvanProfil.evcilHayvanAd}. ${hayvanProfil.evcilHayvanYas} yaşında bir ${hayvanProfil.evcilHayvanCinsiyet}"
                            }
                        }
                    }
                }
            }
        }



    }

    private fun getDataProfilePost(){ // verileri fireStore den alma fonksiyonu

        val userEmailActive = auth.currentUser?.email as String



        db.collection("Posts").whereEqualTo("userEmail",userEmailActive).orderBy("tarih",
            Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if(error != null){
                // hata var demektir
                //Toast.makeText(requireActivity(),error.localizedMessage,Toast.LENGTH_LONG).show()
            }else{
                // hata yoksa
                if(value != null){
                    if(!value.isEmpty){
                        // eğer değerler boş değilse

                        val documents = value.documents

                        postArrayList.clear()

                        for(document in documents){
                            // artık burada document dediğim sey bu uygulama özelinde bir adet post demek
                            val yorum = document.get("yorum") as String
                            val userEmail = document.get("userEmail") as String
                            val downloadUrlProfilePost = document.get("downloadUrlProfilePost") as String
                            val post = Post(userEmail,yorum,downloadUrlProfilePost)
                            postArrayList.add(post)
                        }
                        profilePostAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

    }







}