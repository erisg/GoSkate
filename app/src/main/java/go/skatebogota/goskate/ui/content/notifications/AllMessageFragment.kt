package go.skatebogota.goskate.ui.content.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import go.skatebogota.goskate.R
import kotlinx.android.synthetic.main.message_fragment.*

class AllMessageFragment: Fragment() , View.OnClickListener {

    private var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.message_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        messageRecyclerView.adapter
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.floatingActionButton2 -> navController!!.navigate(R.id.action_allMessageFragment_to_searchNewMessage)
        }
    }
}