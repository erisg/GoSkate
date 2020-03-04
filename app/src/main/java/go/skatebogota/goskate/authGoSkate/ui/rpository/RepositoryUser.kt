package go.skatebogota.goskate.authGoSkate.ui.rpository

import android.app.Application
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class RepositoryUser(application: Application) {

    var response : String = ""

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            response = when {
                it.isSuccessful -> {
                    "fine"
                }
                else -> {
                    it.exception?.message ?:""
                }
            }
        }
    }

    fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            response = when {
                it.isSuccessful -> {
                    "fine"
                }
                else -> {
                    it.exception?.message ?:""
                }
            }
        }
    }
}