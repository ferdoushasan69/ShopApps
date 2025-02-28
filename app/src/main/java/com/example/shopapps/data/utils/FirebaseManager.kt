package com.example.shopapps.data.utils


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject



class FirebaseManager @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser

    suspend fun registerUser(email:String,password:String):Result<String>{
       return try {
            val authResult  = firebaseAuth.createUserWithEmailAndPassword(email,password).await()
            Result.success("Registration Successfully done ${authResult.user?.uid}")
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    suspend fun loginUser(email: String,password: String):Result<String>{
        return try {
            val loginResult = firebaseAuth.signInWithEmailAndPassword(email,password).await()
            Result.success("Login successfully done ${loginResult.user?.uid}")
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    suspend fun logout(){
        firebaseAuth.signOut()
    }

    fun isUserLoggedIn():Boolean{
      return firebaseAuth.currentUser != null
    }

}