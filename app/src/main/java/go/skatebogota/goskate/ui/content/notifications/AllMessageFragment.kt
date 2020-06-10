package go.skatebogota.goskate.ui.content.notifications

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import go.skatebogota.goskate.R
import go.skatebogota.goskate.data.models.UserVO
import go.skatebogota.goskate.ui.viewmodels.ViewModelContent
import kotlinx.android.synthetic.main.message_fragment.*
import kotlinx.android.synthetic.main.search_new_message.view.*


class AllMessageFragment : Fragment(){

    private var navController: NavController? = null
    private lateinit var viewModelContent: ViewModelContent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.message_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        viewModelContent = ViewModelProviders.of(this).get(ViewModelContent::class.java)
        messageRecyclerView.adapter

        floatingActionButton2.setOnClickListener {
            var arrayAdapter: ArrayAdapter<*>?
            var listNames: MutableList<String>? = null
            val mDialogView = LayoutInflater.from(this.context).inflate(R.layout.search_new_message, null)
            val mBuilder = AlertDialog.Builder(this.context).setView(mDialogView)
            val mAlertDialog = mBuilder.show()
            viewModelContent.getAllUsersName().observeForever { userVo ->
            val list =  userVo.map {
                   it.userName
                }
                arrayAdapter = ArrayAdapter<Any?>(
                    this.context!!,
                    android.R.layout.simple_dropdown_item_1line,
                    list
                )
                mDialogView.seacrhUsersAutoComplete.setAdapter(arrayAdapter)
                mDialogView.seacrhUsersAutoComplete.threshold = 0
                mDialogView.seacrhUsersAutoComplete.onFocusChangeListener =
                    View.OnFocusChangeListener { v, hasFocus ->
                        if(hasFocus) mDialogView.seacrhUsersAutoComplete.showDropDown()
                    }
            }


            mDialogView.seacrhUsersAutoComplete.setOnItemClickListener { parent, view, position, id ->
                if((view as TextView).text != null) {
                    viewModelContent.userVO = (view as TextView).text
                    mDialogView.initNewChatButton.visibility = View.VISIBLE
                } else mDialogView.initNewChatButton.visibility = View.GONE


            }

            mDialogView.initNewChatButton.setOnClickListener {
                navController!!.navigate(R.id.action_allMessageFragment_to_messageChatFragment)
                mAlertDialog.dismiss()
            }
            mDialogView.closeImageView.setOnClickListener {
                mAlertDialog.dismiss()
            }

        }

    }


}