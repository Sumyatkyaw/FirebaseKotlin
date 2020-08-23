package com.example.firebasedatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.firebasedatabase.model.User
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    private lateinit var dbReference:DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private var userId:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseDatabase= FirebaseDatabase.getInstance()
        dbReference =firebaseDatabase.getReference("user")
        userId=dbReference.push().key.toString()


        if(TextUtils.isEmpty(userId)){
            createUser("James","097777")
        }else{
            updateUser(edtName.text.toString(),edtPhone.text.toString())


        }

    }
    fun createUser(name:String,mobile:String){
        val user= User(name,mobile)
        dbReference.child(userId).setValue(user)
    }
    fun updateUser(name: String,mobile: String){
        if(!TextUtils.isEmpty(name)){
            dbReference.child(userId).child(name).setValue(name)
        }
        if (!TextUtils.isEmpty(mobile)){
            dbReference.child(userId).child(mobile).setValue(mobile)
        }
        dbReference.child(userId).addValueEventListener(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var user= snapshot.getValue(User::class.java)
                edtName.setText(user?.name)
                edtPhone.setText(user?.mobile)
            }
        })

    }
}

