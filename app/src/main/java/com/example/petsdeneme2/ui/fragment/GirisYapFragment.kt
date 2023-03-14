package com.example.petsdeneme2.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.petsdeneme2.OlayActivity
import com.example.petsdeneme2.R
import com.example.petsdeneme2.databinding.FragmentGirisYapBinding
import com.example.petsdeneme2.databinding.FragmentKayitOlBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class GirisYapFragment : Fragment() {

    private lateinit var tasarim : FragmentGirisYapBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tasarim = FragmentGirisYapBinding.inflate(inflater, container, false)
        val view = tasarim.root

        auth = Firebase.auth





        tasarim.textViewKaydol.setOnClickListener{
            kayitOlText(it)
        }

        tasarim.buttonGirisYap.setOnClickListener {

            val email = tasarim.editTextEmailGiris.text.toString()
            val sifre = tasarim.editTextSifre.text.toString()

            if(email.equals("") || sifre.equals("")){
                Toast.makeText(requireActivity(),"Email ya da Şifre Giriniz!",Toast.LENGTH_LONG).show()
            }else{
                auth.signInWithEmailAndPassword(email,sifre).addOnSuccessListener {
                    // giriş yapılırsa
                    olayActivityGec(tasarim.buttonGirisYap)
                }.addOnFailureListener {
                    Toast.makeText(requireActivity(),"Giriş Yaparken Hata Oluştu",Toast.LENGTH_LONG).show()
                }
            }
        }

        return view
    }


    fun kayitOlText(it:View){
        Navigation.findNavController(it).navigate(R.id.kayitOlFragmentGecis)
    }

    fun olayActivityGec(it:View){
        val intent = Intent(requireActivity(),OlayActivity::class.java)
        startActivity(intent)
    }

}