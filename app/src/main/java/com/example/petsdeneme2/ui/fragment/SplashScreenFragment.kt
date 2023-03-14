package com.example.petsdeneme2.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.petsdeneme2.OlayActivity
import com.example.petsdeneme2.R
import com.example.petsdeneme2.databinding.FragmentSplashScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashScreenFragment : Fragment() {

    private lateinit var tasarim : FragmentSplashScreenBinding
    private lateinit var auth : FirebaseAuth


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tasarim = FragmentSplashScreenBinding.inflate(inflater, container, false)
        val view = tasarim.root

        auth = Firebase.auth
        val currentUser = auth.currentUser




        Handler(Looper.myLooper()!!).postDelayed({

            if(currentUser != null){
                // bu if çalışırsa içerde giriş yapılı bir kullanıcı var demektir
                val intent = Intent(requireActivity(), OlayActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }else{
                findNavController().navigate(R.id.splashFragmentGecis)
            }
            },3000)


        return view
    }



}