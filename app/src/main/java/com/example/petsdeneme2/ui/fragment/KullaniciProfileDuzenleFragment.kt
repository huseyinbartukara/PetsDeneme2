package com.example.petsdeneme2.ui.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.example.petsdeneme2.R
import com.example.petsdeneme2.databinding.FragmentAdoptBinding
import com.example.petsdeneme2.databinding.FragmentKullaniciProfileDuzenleBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.util.*

class KullaniciProfileDuzenleFragment : Fragment() {

    private lateinit var tasarim : FragmentKullaniciProfileDuzenleBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var firestore : FirebaseFirestore
    private lateinit var storge : FirebaseStorage
    private lateinit var activityResultLauncher : ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher : ActivityResultLauncher<String>
    var selectedPicture : Uri? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tasarim = FragmentKullaniciProfileDuzenleBinding.inflate(inflater, container, false)
        val view = tasarim.root

        auth = Firebase.auth
        firestore = Firebase.firestore
        storge = Firebase.storage

        registerLauncher()


        tasarim.imageViewKullaniciProfilDuzenle.setOnClickListener {
            // galeriye gidip resim cekilicek.
            selectImage(it)
        }

        tasarim.buttonKullaniciProfilDuzenle.setOnClickListener {
            // burada bilgiler firebase e kayıt edilicek.
            upload(it)
        }





        return view
    }

    fun upload(view: View){

        val uuid = UUID.randomUUID()
        val imageName = "$uuid.jpg"

        val reference = storge.reference
        val imageReference = reference.child("kullaniciProfileImages").child(imageName)

        if(selectedPicture != null){
            imageReference.putFile(selectedPicture!!).addOnSuccessListener {
                // upload edilirse
                val uploadPictureReference = storge.reference.child("kullaniciProfileImages").child(imageName)
                uploadPictureReference.downloadUrl.addOnSuccessListener {
                    // bize resmin storage da nereye kayıtlı oldugunu uri ile veriyor
                    val dowloadUrl = it.toString()

                    if(auth.currentUser != null){
                        val postMap = hashMapOf<String, Any>()

                        postMap.put("downloadUrlKullaniciProfil",dowloadUrl)
                        postMap.put("userEmail",auth.currentUser!!.email!!)
                        postMap.put("kullaniciProfileDetay",tasarim.editTextKullaniciProfilDuzenle.text.toString())




                        firestore.collection("KullaniciProfile").add(postMap).addOnSuccessListener {
                            // firestore içerisine aktarılırsa
                            Navigation.findNavController(tasarim.buttonKullaniciProfilDuzenle).navigate(R.id.profileFragmentGecis)
                        }.addOnFailureListener {
                            // fireStore içerisine aktaramazsam
                            Toast.makeText(requireActivity(),it.localizedMessage,Toast.LENGTH_LONG).show()
                        }
                    }


                }

            }.addOnFailureListener{
                // UPLOAD başarısız olursa
                Toast.makeText(requireActivity(),it.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }

    }


    fun selectImage(view:View){

        if(ContextCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            // izin yok demek
            if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)){
                Snackbar.make(view,"Permission Needed for gallery", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission"){
                    // izin isteme
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }.show()
            }else{
                // izin isteme
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }else{
            // izin var demek galeriye gidicek.
            val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intentToGallery)
        }
    }


    private fun registerLauncher(){

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == AppCompatActivity.RESULT_OK){
                val intentFromResult = result.data
                if(intentFromResult != null){
                    selectedPicture = intentFromResult.data // galeriden gelen verinin uri si
                    selectedPicture?.let {
                        tasarim.imageViewKullaniciProfilDuzenle.setImageURI(it) // kullanıcı arayuzde sectiği resmi görsün diye
                    }
                }
            }
        }

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ result ->
            if(result){
                // izin verildi
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)

            }else{
                // izin verilmedi
                Toast.makeText(requireActivity(),"permission needed!", Toast.LENGTH_LONG).show()
            }
        }

    }



}