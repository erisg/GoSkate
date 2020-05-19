package go.skatebogota.goskate.ui.auth


import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProviders
import go.skatebogota.goskate.R
import go.skatebogota.goskate.ui.content.MainActivity
import go.skatebogota.goskate.ui.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.register.*
import java.util.jar.Manifest

class Login : AppCompatActivity() {

    private lateinit var viewModel: UserViewModel
    lateinit var userEmail: String
    lateinit var userPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
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

    private fun camChooser() {
        val camPermission =
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        val galleryPermission = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        val locationPermission = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
        if (camPermission != PackageManager.PERMISSION_GRANTED || galleryPermission != PackageManager.PERMISSION_GRANTED || locationPermission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
               // ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE , android.Manifest.permission.CAMERA , android.Manifest.permission.ACCESS_FINE_LOCATION))
            }
        }
    }

    private fun validateEditText(editText: EditText, message: String) {
        editText.error = message
        editText.requestFocus()
    }


}
