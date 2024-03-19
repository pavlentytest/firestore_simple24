package com.example.myapplication

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)

        val db = FirebaseFirestore.getInstance()
        db.collection("test").add(User("Ivan",20)).addOnCompleteListener {
            task ->
            if(task.isComplete) {
                Log.d("RRR","Done!")
            } else {
                Log.d("RRR",task.exception.toString())
            }
        }

        val query = db.collection("test")
        val countQuery = query.count()
        countQuery.get(AggregateSource.SERVER).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Count fetched successfully
                val snapshot = task.result
                Log.d("RRR", "Count: ${snapshot.count}")
            } else {
                Log.d("RRR", "Count failed: ", task.getException())
            }
        }

        db.collection("test").get().addOnCompleteListener { task ->
            if(task.isComplete) {
                val user_list = task.result.documents.size



           /*     user_list.forEach {
                    val user = it.toObject(User::class.java)
                    Log.d("RRR", "${user}")
                }*/
            } else {
                Log.d("RRR",task.exception.toString())
            }

        }
        val user: HashMap<String,Any> = hashMapOf(
            "name" to "Olga",
            "age" to 20
        )


        db.collection("test").document("2BlUvuVV4w42C6srkjOv").update(user).addOnCompleteListener {

        }
    }
}
data class User(val name: String, val age: Int) {
    constructor() : this("",0)
}