package com.example.petsdeneme2.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.petsdeneme2.R
import com.example.petsdeneme2.databinding.FragmentKayitOlBinding
import com.example.petsdeneme2.databinding.FragmentSplashScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class KayitOlFragment : Fragment() {

    private lateinit var tasarim : FragmentKayitOlBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tasarim = FragmentKayitOlBinding.inflate(inflater, container, false)
        val view = tasarim.root

        auth = Firebase.auth


        tasarim.textViewGirisYap.setOnClickListener {
            girisYapGecis(it)
        }

        tasarim.buttonKayTOl.setOnClickListener {
            val email = tasarim.editTextEmail.text.toString()
            val sifre = tasarim.editTextSifre.text.toString()

            if(email.equals("") || sifre.equals("")){
                Toast.makeText(requireActivity(),"Lütfen E-mail ya da Şifre Giriniz!",Toast.LENGTH_LONG).show()
            }else{
                auth.createUserWithEmailAndPassword(email,sifre).addOnSuccessListener {
                    // kayıt başarılı olursa.
                    girisYapGecis(tasarim.buttonKayTOl)
                }.addOnFailureListener {
                    // kayıt başarısız olursa
                    Toast.makeText(requireActivity(),"Kayıt Olurken Bir Hata Oluştu",Toast.LENGTH_LONG).show()
                }
            }
        }

        return view
    }



    fun girisYapGecis(it:View){
        Navigation.findNavController(it).navigate(R.id.girisYapFragmentGecis)
    }

}