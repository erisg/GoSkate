package go.skatebogota.goskate.ui.content

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import go.skatebogota.goskate.R
import go.skatebogota.goskate.ui.auth.Login
import go.skatebogota.goskate.ui.viewmodels.ViewModelContent
import kotlinx.android.synthetic.main.home.*

class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var viewModelContent: ViewModelContent
    private var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.btn_post).setOnClickListener(this)
        viewModelContent = ViewModelProviders.of(this).get(ViewModelContent::class.java)


    }



    private fun getDtaaPost() {
        val images = viewModelContent.getImagePost()
     //   recyclerPost.adapter = RecyclerPostAdapter(images)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_post -> navController!!.navigate(R.id.action_homeFragment_to_postFragment2)
        }
    }



}