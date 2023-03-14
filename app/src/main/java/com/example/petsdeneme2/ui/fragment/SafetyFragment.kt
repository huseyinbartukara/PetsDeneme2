package com.example.petsdeneme2.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petsdeneme2.R
import com.example.petsdeneme2.databinding.FragmentAdoptBinding
import com.example.petsdeneme2.databinding.FragmentHomeBinding
import com.example.petsdeneme2.databinding.FragmentSafetyBinding

class SafetyFragment : Fragment() {

    private lateinit var tasarim : FragmentSafetyBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tasarim = FragmentSafetyBinding.inflate(inflater, container, false)
        val view = tasarim.root




        return view
    }
}