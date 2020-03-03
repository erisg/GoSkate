package go.skatebogota.goskate.authGoSkate.ui.rpository

import android.app.Application
import android.util.Log
import com.google.firebase.auth.FirebaseAuth

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RepositoryUser(application: Application) {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            when {
                it.isSuccessful -> {
                    Log.e("AUTH", "FINE")
                }
                else -> {
                    Log.e("AUTH", it.exception!!.message)
                }
            }
        }
    }
}