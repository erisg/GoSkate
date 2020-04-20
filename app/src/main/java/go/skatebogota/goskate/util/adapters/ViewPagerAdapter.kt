package go.skatebogota.goskate.util.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import go.skatebogota.goskate.ui.content.notifications.AllMessageFragment
import go.skatebogota.goskate.ui.content.notifications.NotificationFragment

class ViewPagerAdapter(fragment: FragmentManager) : FragmentPagerAdapter(fragment) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                NotificationFragment()
            }
            else -> {
                AllMessageFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "NOTIFICACIONES"
            else ->{"MENSAJES"}
        }
    }

}