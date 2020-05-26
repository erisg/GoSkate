package go.skatebogota.goskate.util.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import go.skatebogota.goskate.R
import go.skatebogota.goskate.ui.content.notifications.AllMessageFragment
import go.skatebogota.goskate.ui.content.notifications.NotificationFragment

class ViewPagerAdapter(fragment: FragmentManager ,val context: Context? ) : FragmentPagerAdapter(fragment) {

    private val fragmentList = listOf(
        NotificationFragment(),
        AllMessageFragment()
    )

    override fun getItem(position: Int): Fragment =
        fragmentList[position]

    override fun getCount(): Int =
        fragmentList.size

    override fun getPageTitle(position: Int): String? =
        context?.resources?.getStringArray(R.array.fragment_titles)?.get(position)

}