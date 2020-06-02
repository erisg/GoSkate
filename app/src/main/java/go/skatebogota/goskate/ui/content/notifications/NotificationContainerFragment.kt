package go.skatebogota.goskate.ui.content.notifications

import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import go.skatebogota.goskate.R
import go.skatebogota.goskate.util.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.notification.*
import kotlinx.android.synthetic.main.search_new_message.*

class NotificationContainerFragment: Fragment() , View.OnClickListener  {


    private lateinit var dialog : Dialog
    private var navController: NavController? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<ImageView>(R.id.messageImageView).setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.messageImageView -> navController!!.navigate(R.id.action_notifyFragment_to_allMessageFragment)
        }
    }
}