package go.skatebogota.goskate.ui.content.notifications

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import go.skatebogota.goskate.R
import go.skatebogota.goskate.util.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.notification.*

class NotificationContainerFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentAdapter = ViewPagerAdapter(fragmentManager!!)
        viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)
    }


    override fun onStart() {
        val fragmentAdapter = ViewPagerAdapter(fragmentManager!!)
        viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)
        super.onStart()
    }
}