package go.skatebogota.goskate.data.repositories


import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import go.skatebogota.goskate.util.interfaces.AuthListenerResponseUserInfo
import go.skatebogota.goskate.util.interfaces.AuthListenerResponseUserRegister

class RepositoryUser() {

    var response: String = ""
    var authListener : AuthListenerResponseUserRegister? = null
    var authListenerInfoUser : AuthListenerResponseUserInfo? = null
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var userResponse : LiveData<String>

    /**
     * Se registra USUARIO en retrofit
     */
    fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            val message  = it.exception?.toString()
            if(it.isSuccessful){
            }else{
                authListener?.onFailure()
            }
        }
    }

    /**
     * Se guarda la informacion del perfil del usuario
     */
    fun saveInfoUser(userName :String , userEmail :String , userPassword :String , ageUser :String) {
        var userId = auth.currentUser!!.uid
        var userRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")

        val userMap = HashMap<String , Any>()
        userMap["uid"] = userId
        userMap["userName"] = userId
        userMap["userEmail"] = userId
        userMap["userPassword"] = userId
        userMap["ageUser"] = userId

        userRef.child(userId).setValue(userMap).addOnCompleteListener{task ->
            val message  = task.exception?.toString()
            if(task.isSuccessful){
                authListenerInfoUser?.onSuccessUser()
            }else{
                authListenerInfoUser?.onFailureUser()
            }
        }
    }

    fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            val message  = it.exception!!.toString()
            if(it.isSuccessful){
                authListener?.onSuccess()
            }else{
                authListener?.onFailure()
            }

        }
    }

}