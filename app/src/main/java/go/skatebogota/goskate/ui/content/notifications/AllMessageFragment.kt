package go.skatebogota.goskate.ui.content.notifications

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import go.skatebogota.goskate.R
import kotlinx.android.synthetic.main.message_fragment.*
import kotlinx.android.synthetic.main.search_new_message.*

class AllMessageFragment: Fragment() {

    private lateinit var dialog : Dialog
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

        floatingActionButton2.setOnClickListener {
            showDialog()
        }

    }

    fun showDialog(){
        dialog = Dialog(this.context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.search_new_message)
        dialog.setTitle("BUSCAR USUARIO")
        closeImageView?.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
    }
}