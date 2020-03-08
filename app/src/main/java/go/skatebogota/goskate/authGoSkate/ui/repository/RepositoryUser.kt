package go.skatebogota.goskate.authGoSkate.ui.repository

import android.app.Application
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