package go.skatebogota.goskate.ui.content.notifications

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import go.skatebogota.goskate.R
import go.skatebogota.goskate.ui.viewmodels.ViewModelContent
import kotlinx.android.synthetic.main.message_fragment.*
import kotlinx.android.synthetic.main.search_new_message.view.*


class AllMessageFragment : Fragment() {

    private var navController: NavController? = null
    private lateinit var viewModelContent: ViewModelContent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.message_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelContent = ViewModelProviders.of(this).get(ViewModelContent::class.java)
        messageRecyclerView.adapter

        floatingActionButton2.setOnClickListener {
            val mDialogView =
                LayoutInflater.from(this.context).inflate(R.layout.search_new_message, null)
            val mBuilder =
                AlertDialog.Builder(this.context).setView(mDialogView)
            val mAlertDialog = mBuilder.show()
            viewModelContent.getAllUsersName().observeForever { userVo ->
                userVo.forEach {
                    it.userName

                }
            }

            mDialogView.closeImageView.setOnClickListener {
                mAlertDialog.dismiss()
            }

        }

    }


}