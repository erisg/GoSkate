package go.skatebogota.goskate.authGoSkate.ui

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import go.skatebogota.goskate.R
import go.skatebogota.goskate.authGoSkate.ui.viewModel.UserViewModel
import go.skatebogota.goskate.contentGoSkate.MainActivity
import kotlinx.android.synthetic.main.register.*


class UserRegister : AppCompatActivity() {

    private val userViewModel: UserViewModel by lazy { UserViewModel.getUserViewModel(this)!! }
    private lateinit var userName: String
    private lateinit var userEmail: String
    private lateinit var userPassword: String
    private lateinit var userPasswordTwo: String
    var sexUser: Boolean? = null
    private lateinit var ageUser: String
    private var mAuth = FirebaseAuth.getInstance();
    lateinit var response : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)



        btn_sign_up.setOnClickListener {
            validaInfoNewUser()
        }
    }

    /**
     * @author Geraldin
     * * VALIDA SI LA INFORMACION ESTA COMPLETA Y BIEN DILIGENCIADA
     */

    private fun validaInfoNewUser() {
        userName = nameEditText.text.toString().trim()
        userEmail = emallEditTextR.text.toString().trim()
        userPassword = passwordEditTextR.text.toString().trim()
        userPasswordTwo = passwordTwoEditText.text.toString().trim()
        ageUser = AgeEditText.text.toString().trim()

        when {
            userName.isEmpty() || ageUser.isEmpty() -> {
                validateEditText(nameEditText)
                validateEditText(AgeEditText)
            }
            userPassword != userPasswordTwo -> {
                Toast.makeText(this, "LAS CONTRSE;AS NO COINCIDEN", Toast.LENGTH_LONG).show()
            }

            else -> {
                userViewModel.registerUser(userEmail, userPasswordTwo)
                userResponse()
            }
        }

    }

    private fun userResponse(){
        response = userViewModel.getUserRegisterResponse()
        if(response == "fine"){
            startActivity(Intent(this, MainActivity::class.java))
        }else{
            Toast.makeText(this,"$response",Toast.LENGTH_LONG).show()
        }
    }

    private fun validateEditText(editText: EditText) {
        editText.error = getString(R.string.info_valid)
        editText.requestFocus()
    }


    override fun onStart() {
        super.onStart()

        val currentUser: FirebaseUser? = mAuth.currentUser
    }
}