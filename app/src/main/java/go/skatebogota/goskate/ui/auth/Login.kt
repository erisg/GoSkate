package go.skatebogota.goskate.ui.auth


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import go.skatebogota.goskate.R
import go.skatebogota.goskate.ui.content.MainActivity
import go.skatebogota.goskate.ui.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.register.*

class Login : AppCompatActivity() {

    private lateinit var viewModel: UserViewModel
    lateinit var userEmail: String
    lateinit var userPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        if(viewModel.currentUser!=null){
            startActivity(Intent(this, MainActivity::class.java))
        }

        loginBtn.setOnClickListener {
           // validateInfoUser()
            startActivity(Intent(this, MainActivity::class.java))
        }

        noAccountTextView.setOnClickListener {
            startActivity(Intent(this, UserRegister::class.java))
        }
    }

    private fun validateInfoUser() {
        userEmail = emailEditText.text.toString()
        userPassword = passwordEditText.text.toString()

        when {
            TextUtils.isEmpty(userEmail) -> validateEditText(
                emailEditText,
                "POR FAVOR INGRESAR EMAIL"
            )
            TextUtils.isEmpty(userPassword) -> validateEditText(
                passwordEditText,
                "POR FAVOR INGRESAR CONTRASEÑA"
            )
            else -> {
                viewModel.loginUser(userEmail, userPassword)
                validateUser()
            }
        }
    }

    private fun validateUser() {
        val firebaseResponse = viewModel.getUserRegisterResponse()
        if (firebaseResponse == "Successful") {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            Toast.makeText(this, "$firebaseResponse", Toast.LENGTH_SHORT)
        }
    }

    private fun validateEditText(editText: EditText, message: String) {
        editText.error = message
        editText.requestFocus()
    }


}
