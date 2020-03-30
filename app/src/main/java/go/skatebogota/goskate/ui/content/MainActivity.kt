package go.skatebogota.goskate.ui.content

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.FirebaseAuth
import go.skatebogota.goskate.R
import go.skatebogota.goskate.ui.auth.Login
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(navGoSkate, navController)
        checkUserStatus()

    }



    private fun checkUserStatus(){
        if(auth.currentUser != null){
           //jklp
        }else{
            logoOut()
        }
    }

    private fun logoOut() {
        startActivity(Intent(this, Login::class.java))
    }
}
