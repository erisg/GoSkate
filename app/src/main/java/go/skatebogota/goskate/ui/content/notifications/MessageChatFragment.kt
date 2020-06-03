package go.skatebogota.goskate.ui.content.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import go.skatebogota.goskate.R
import go.skatebogota.goskate.ui.viewmodels.ViewModelContent
import kotlinx.android.synthetic.main.message_chat.*

class MessageChatFragment : Fragment() {


    private lateinit var viewModelContent: ViewModelContent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.message_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelContent = ViewModelProviders.of(this).get(ViewModelContent::class.java)


        chatNameTextView.text = viewModelContent.userVO


        sendImageButton.setOnClickListener {
            val message = messageEditText.text.toString()

            if(message.isEmpty()){
                Toast.makeText(this.context,"Por favor escribe tu mensaje",Toast.LENGTH_LONG).show()
            }else{
                sendMessageToUser()
            }
        }
    }

    private fun sendMessageToUser() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}