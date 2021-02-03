package com.example.kelasfirestore

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = FirebaseFirestore.getInstance()
        val dbCol = "Pekerjaan"

        //Butuh Buat Yg Contoh Auto ID
        /*val user: MutableMap<String, Any> = HashMap()
       user["first"] = "Ada"
       user["last"] = "Lovelace"
       user["born"] = 1815*/

        //Contoh Send to Firestore with Auto ID
        /*db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference -> Log.d("CobaFirebase", "Doc Snapshot by auto ID : " + documentReference.id) }
            .addOnFailureListener { e -> Log.w("CobaFirebase", "Error adding document", e) }*/

        btSave.setOnClickListener {
            val data = hashMapOf(
                "Pekerjaan" to etPekerjaan.text.toString(),
                "Rating" to rbnilai.rating.toString()
            )
            db.collection(dbCol).document(etPekerjaan.text.toString()).set(data)
        }

        btCari.setOnClickListener {
            db.collection(dbCol).document(etPekerjaan.text.toString())
                .get()
                .addOnSuccessListener {
                    var data = it?.data as MutableMap<String, String>
                    rbnilai.rating = data.getValue("Rating").toFloat()
                    Log.d("CobaFirebase", "${data.getValue("Pekerjaan")}")
                }
                .addOnFailureListener { Log.d("CobaFirebase", "Failure") }
        }

        btTampil.setOnClickListener {
            db.collection(dbCol)
                .get()
                .addOnSuccessListener {
                    documents ->
                    for(document in documents){
                        var data = document.data as MutableMap<String, String>
                        Log.d("CobaFirebase", data.toString())
                    }
                }
                .addOnFailureListener { Log.d("CobaFirebase", "Failure") }
        }

        btHapus.setOnClickListener {
            db.collection(dbCol).document(etPekerjaan.text.toString())
                .delete()
                .addOnSuccessListener { Log.d("CcobaFirebase", "Berhasil Delete") }
                .addOnFailureListener { e -> Log.d("CobaFirebase", "Error ", e)}
        }
    }
}