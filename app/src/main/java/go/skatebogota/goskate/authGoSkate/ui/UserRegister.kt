package go.skatebogota.goskate.authGoSkate.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import go.skatebogota.goskate.R
import go.skatebogota.goskate.contentGoSkate.MainActivity
import kotlinx.android.synthetic.main.register.*
import kotlin.properties.Delegates

class UserRegister : AppCompatActivity() {

    lateinit var userName: String
    lateinit var userPassword: String
    lateinit var userPasswordTwo: String
    var sexUser: Boolean? = null
    lateinit var ageUser: String
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        auth = FirebaseAuth.getInstance()


        btn_sign_up.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    /**
     * @author Geraldin
     * * VALIDA SI LA INFORMACION ESTA COMPLETA Y BIEN DILIGENCIADA
     */

    private fun validaInfoNewUser() {
        userName = nameEditText.text.toString().trim()
        userPassword = passwordEditTextR.text.toString().trim()
        userPasswordTwo = passwordTwoEditText.text.toString().trim()
        ageUser = AgeEditText.text.toString().trim()


    }

    fun updateUI(currentUser: FirebaseUser?) {

    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
}