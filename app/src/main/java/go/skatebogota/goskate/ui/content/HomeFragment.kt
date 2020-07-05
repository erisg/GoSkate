package go.skatebogota.goskate.ui.content

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import go.skatebogota.goskate.R
import go.skatebogota.goskate.data.models.PostVO
import go.skatebogota.goskate.ui.auth.Login
import go.skatebogota.goskate.ui.viewmodels.UserViewModel
import go.skatebogota.goskate.ui.viewmodels.ViewModelContent
import go.skatebogota.goskate.util.adapters.RecyclerPostAdapter
import kotlinx.android.synthetic.main.head_bar.*
import kotlinx.android.synthetic.main.home.*

class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var viewModelContent: ViewModelContent
    private lateinit var viewModel: UserViewModel
    private var navController: NavController? = null
    private lateinit var adapter: RecyclerPostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.btn_post).setOnClickListener(this)
        viewModelContent = ViewModelProviders.of(this).get(ViewModelContent::class.java)
        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        adapter = RecyclerPostAdapter(this.requireContext())
        recyclerPost.setHasFixedSize(true)
        recyclerPost.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerPost.adapter = adapter
        viewModelContent.mutableData.observe(viewLifecycleOwner, Observer {
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
        })
        getAllUserPost()


        // BOTON DE DESLOGUEO

        logoOutImageView.setOnClickListener {
            if (viewModel.currentUser != null) {
                viewModelContent.currentUser.signOut()
                startActivity(Intent(this.context, Login::class.java))
            }
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_post -> navController!!.navigate(R.id.action_homeFragment_to_postFragment2)
        }
    }

    private fun getAllUserPost() {
        viewModelContent.getInfoPost()

    }


}