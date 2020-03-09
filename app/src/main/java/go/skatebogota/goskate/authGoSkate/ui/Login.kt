package go.skatebogota.goskate.authGoSkate.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import go.skatebogota.goskate.R
import go.skatebogota.goskate.authGoSkate.ui.viewModel.UserViewModel
import go.skatebogota.goskate.contentGoSkate.ui.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    private var userEmail: String? = null
    private var userPassword: String? = null
    private val userViewModel: UserViewModel by lazy { UserViewModel.getUserViewModel(this)!! }
    private lateinit var auth: FirebaseAuth
    lateinit var response : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()




        loginBtn.setOnClickListener {
            validateInfo()
            //startActivity(Intent(this, MainActivity::class.java))
        }

        noAccountTextView.setOnClickListener {
            startActivity(Intent(this, UserRegister::class.java))
        }
    }

    private fun validateInfo() {
        userEmail = emailEditText.text.toString()
        userPassword = passwordEditText.text.toString()
        if (emailEditText.text.toString().isEmpty()||passwordEditText.text.toString().isEmpty() ) {
            validateEditText(emailEditText)
            validateEditText(passwordEditText)
        } else {
            userViewModel.loginUser( userEmail!! , userPassword!!)
            userResponse()
        }
    }

    private fun userResponse(){
        response = userViewModel.getUserLoginResponse()
        if(response == "fine"){
            startActivity(Intent(this, MainActivity::class.java))
        }else{
            Toast.makeText(this,"USUARIO NO ENCONTRADO",Toast.LENGTH_SHORT).show()
        }
    }



    private fun validateEditText(editText: EditText) {
        editText.error = getString(R.string.info_valid)
        editText.requestFocus()
    }

    fun updateUI(currentUser: FirebaseUser?) {

    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
}
