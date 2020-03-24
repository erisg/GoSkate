package go.skatebogota.goskate.ui.auth

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import go.skatebogota.goskate.R
import go.skatebogota.goskate.ui.ui.viewmodels.UserViewModel
import go.skatebogota.goskate.ui.ui.content.MainActivity
import go.skatebogota.goskate.util.customizedview.DatePickerDialogView
import go.skatebogota.goskate.util.interfaces.AuthListenerResponseUserInfo
import go.skatebogota.goskate.util.interfaces.AuthListenerResponseUserRegister
import kotlinx.android.synthetic.main.register.*
import java.util.*


class UserRegister : AppCompatActivity()  {


    lateinit var userName: String
    lateinit var userEmail: String
    lateinit var userPassword: String
    lateinit var userPasswordTwo: String
    private lateinit var ageUser: String
    var sexUser: Boolean? = null
    private var mAuth = FirebaseAuth.getInstance();
    lateinit var response : String
    private var maximumAge = 0
    var validationType: Int = 0
    private var minimumAge = 0
    private lateinit var viewModel : UserViewModel
    private lateinit var progressDialog : ProgressDialog
    var authListener : AuthListenerResponseUserRegister? = null
    var authListenerUserInfo : AuthListenerResponseUserInfo? = null


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
    }

    /**
     * @author Geraldin
     * * VALIDA SI LA INFORMACION ESTA COMPLETA Y BIEN DILIGENCIADA
     */

    private fun validaInfoNewUser() {

        userName = nameEditText.text.toString()
        userEmail = emallEditTextR.text.toString()
        userPassword = passwordEditTextR.text.toString()
        userPasswordTwo = passwordTwoEditText.text.toString()
        ageUser = AgeTextView.text.toString()

        when {
           TextUtils.isEmpty(userName) -> validateEditText(nameEditText , "POR FAVOR INGRESAR NOMBRE DE USUARIO" )
            TextUtils.isEmpty(userEmail) -> validateEditText(nameEditText , "POR FAVOR INGRESAR EMAIL" )
            TextUtils.isEmpty(userPasswordTwo) -> validateEditText(nameEditText , "POR FAVOR INGRESAR CONTRASEÑA" )
            TextUtils.isEmpty(ageUser) -> validateEditText(nameEditText , "POR FAVOR INGRESAR LA FECHA DE NACIMIENTO" )

            else -> {
                saveInfoUser()
            }
        }

    }


    private fun validateEditText(editText: EditText , message: String) {
        editText.error = message
        editText.requestFocus()
    }

    private fun saveInfoUser(){
        viewModel.registerUser(userEmail, userPasswordTwo)
        val isSuccessfulRegister = authListener?.onSuccess()
        val isSuccessfulUserInfo = authListenerUserInfo?.onSuccessUser()
        val onFailureUserRegister = authListener?.onFailure()
        val onFailureUserUserInfo = authListenerUserInfo?.onFailureUser()

        when{
            isSuccessfulRegister!=null->{
                viewModel.saveInfoUser(userName , userEmail ,userPassword ,ageUser)
            }
            isSuccessfulUserInfo!=null->{
                Toast.makeText(this,"$isSuccessfulUserInfo",Toast.LENGTH_LONG).show()
                startActivity(Intent(this, MainActivity::class.java))
            }
            !onFailureUserRegister.isNullOrEmpty()->{
                Toast.makeText(this,"$onFailureUserRegister",Toast.LENGTH_LONG).show()
            }
            !onFailureUserUserInfo.isNullOrEmpty()->{
                Toast.makeText(this,"$onFailureUserUserInfo",Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * Funcion que se encarga de mostrar el datepicker para capturar la fecha de nacimiento
     */

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    private fun dateOfBirth(){
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

}