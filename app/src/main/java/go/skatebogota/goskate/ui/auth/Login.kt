package go.skatebogota.goskate.ui.auth


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import go.skatebogota.goskate.R
import go.skatebogota.goskate.ui.ui.viewmodels.UserViewModel

class Login : AppCompatActivity(){

    private  lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)


    }






}
