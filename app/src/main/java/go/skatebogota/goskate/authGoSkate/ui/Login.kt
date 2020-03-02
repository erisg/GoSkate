package go.skatebogota.goskate.authGoSkate.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import go.skatebogota.goskate.R
import go.skatebogota.goskate.contentGoSkate.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    private var userName: String? = null
    private var userPassword: String? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        loginBtn.setOnClickListener {
            validateInfo()
        }

        noAccountTextView.setOnClickListener {
            startActivity(Intent(this, UserRegister::class.java))
        }
    }

    private fun validateInfo() {
        if (emailEditText.text.toString().isEmpty()) {
            validateEditText(emailEditText)
        }
        if (passwordEditText.text.toString().isEmpty()) {
            validateEditText(passwordEditText)
        }
    }

    fun validateEditText(editText: EditText) {
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
