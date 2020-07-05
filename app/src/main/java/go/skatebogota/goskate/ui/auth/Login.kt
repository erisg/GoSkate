package go.skatebogota.goskate.ui.auth


import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProviders
import go.skatebogota.goskate.R
import go.skatebogota.goskate.ui.content.MainActivity
import go.skatebogota.goskate.ui.viewmodels.MapsViewModel
import go.skatebogota.goskate.ui.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.register.*
import java.util.jar.Manifest

class Login : AppCompatActivity() {


    private val viewModel: UserViewModel by viewModels()
    lateinit var userEmail: String
    lateinit var userPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (viewModel.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }

        loginBtn.setOnClickListener {
            validateInfoUser()
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
                viewModel.loginUser(userEmail, userPassword).observeForever {
                    if (it == "Successful") {
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }



    private fun validateEditText(editText: EditText, message: String) {
        editText.error = message
        editText.requestFocus()
    }


}
