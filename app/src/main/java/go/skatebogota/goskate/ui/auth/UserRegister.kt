@file:Suppress("DEPRECATION")

package go.skatebogota.goskate.ui.auth

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media.getBitmap
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import go.skatebogota.goskate.R
import go.skatebogota.goskate.ui.content.MainActivity
import go.skatebogota.goskate.ui.viewmodels.UserViewModel
import go.skatebogota.goskate.util.customizedview.DatePickerDialogView
import go.skatebogota.goskate.util.interfaces.AuthListenerResponseUserInfo
import go.skatebogota.goskate.util.interfaces.AuthListenerResponseUserRegister
import kotlinx.android.synthetic.main.register.*
import java.util.*


class UserRegister : AppCompatActivity() {


    lateinit var userName: String
    lateinit var userEmail: String
    lateinit var userPassword: String
    lateinit var userPasswordTwo: String
    private lateinit var ageUser: String
    lateinit var sexUser: String
    private var mAuth = FirebaseAuth.getInstance();
    lateinit var response: String
    private var maximumAge = 0
    var validationType: Int = 0
    private var minimumAge = 0
    private lateinit var viewModel: UserViewModel
    private lateinit var progressDialog: ProgressDialog
    var uri: Uri? = null
    var authListener: AuthListenerResponseUserRegister? = null
    var authListenerUserInfo: AuthListenerResponseUserInfo? = null


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        progressDialog = ProgressDialog(this@UserRegister)

        AgeTextView.setOnClickListener {
            dateOfBirth()
        }

        btn_sign_up.setOnClickListener {
            validaInfoNewUser()
        }

        imageProfileButton.setOnClickListener {
            selectImageProfile()
        }
    }

    private fun selectImageProfile() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 0)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            uri = data.data
            selet_photo.visibility = View.GONE
            imageProfileButton.setImageURI(uri)

        }
    }

    /**
     * @author Geraldin
     * * VALIDA SI LA INFORMACION ESTA COMPLETA Y BIEN DILIGENCIADA
     */

    @SuppressLint("ShowToast")
    private fun validaInfoNewUser() {

        userName = nameEditText.text.toString()
        userEmail = emallEditTextR.text.toString()
        userPassword = passwordEditTextR.text.toString()
        userPasswordTwo = passwordTwoEditText.text.toString()
        if (radioMen.isChecked) sexUser =
            SexUser.MEN.valueData else if (radioWomen.isChecked) sexUser = SexUser.WOMEN.valueData
        sexUser
        ageUser = AgeTextView.text.toString()

        when {
            uri == null -> {
                Toast.makeText(this, "POR FAVOR ELIGE UNA FOTO DE PERFIL", Toast.LENGTH_LONG)
            }
            TextUtils.isEmpty(userName) -> validateEditText(
                nameEditText,
                "POR FAVOR INGRESAR NOMBRE DE USUARIO"
            )
            TextUtils.isEmpty(userEmail) -> validateEditText(
                nameEditText,
                "POR FAVOR INGRESAR EMAIL"
            )
            TextUtils.isEmpty(userPasswordTwo) -> validateEditText(
                nameEditText,
                "POR FAVOR INGRESAR CONTRASEÑA"
            )
            TextUtils.isEmpty(ageUser) -> validateEditText(
                nameEditText,
                "POR FAVOR INGRESAR LA FECHA DE NACIMIENTO"
            )


            else -> {
                viewModel.registerUser(uri, userName, userEmail, userPasswordTwo, ageUser, sexUser)
            }
        }

    }


    private fun validateEditText(editText: EditText, message: String) {
        editText.error = message
        editText.requestFocus()
    }

    private fun saveInfoUser() {
        val firebaseResponse = viewModel.getUserRegisterResponse()
        if(firebaseResponse == "Successful") {
            startActivity(Intent(this, MainActivity::class.java))
        }else{
            Toast.makeText(this, firebaseResponse,Toast.LENGTH_SHORT)
        }
    }

    /**
     * Funcion que se encarga de mostrar el datepicker para capturar la fecha de nacimiento
     */

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    private fun dateOfBirth() {
        val myCalendar = Calendar.getInstance()
        val dateCustom =
            DatePickerDialogView()
        dateCustom.setListener(DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            AgeTextView.text = SimpleDateFormat("dd/MM/yyyy").format(myCalendar.time)
        }, "FECHA")
        dateCustom.setDate(AgeTextView.text.toString())
        dateCustom.minYear = 1970
        dateCustom.maxYear = when (validationType) {
            1 -> myCalendar.get(Calendar.YEAR) - (minimumAge / 365)
            2 -> myCalendar.get(Calendar.YEAR)
            3 -> myCalendar.get(Calendar.YEAR) + (maximumAge / 365) + 1
            else -> dateCustom.maxYear
        }

        dateCustom.show((this as AppCompatActivity).supportFragmentManager, "Date")
    }


    override fun onStart() {
        super.onStart()
    }

    enum class SexUser(var valueData: String) {
        WOMEN("F"),
        MEN("M")
    }

}