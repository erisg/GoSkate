package go.skatebogota.goskate.contentGoSkate.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import go.skatebogota.goskate.R

class HomeFragment : Fragment(), View.OnClickListener {

    private var navController: NavController? = null

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.btn_post).setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        when (v!!.id) {
            //   R.id.btn_post -> navController!!.navigate(R.id.action_homeFragment_to_postFragment)
        }
    }
}