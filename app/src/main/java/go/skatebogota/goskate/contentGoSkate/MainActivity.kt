package go.skatebogota.goskate.contentGoSkate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.FirebaseAuth
import go.skatebogota.goskate.R
import go.skatebogota.goskate.authGoSkate.ui.Login
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(nav, navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.out) {
            Toast.makeText(this, "ADIOS !!", Toast.LENGTH_LONG).show()
            logoOut()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logoOut() {
        startActivity(Intent(this, Login::class.java))
        auth.signOut()
    }
}
