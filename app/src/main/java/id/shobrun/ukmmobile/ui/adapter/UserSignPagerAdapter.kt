package id.shobrun.ukmmobile.ui.adapter


import android.content.Context
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import id.shobrun.ukmmobile.R
import id.shobrun.ukmmobile.models.entity.Event
import id.shobrun.ukmmobile.ui.myevents.detail.EventDetailActivity.Companion.EXTRA_EVENT
import id.shobrun.ukmmobile.ui.myevents.detail.EventDetailFragment
import id.shobrun.ukmmobile.ui.myevents.detail.EventSummaryFragment
import id.shobrun.ukmmobile.ui.myevents.detail.ParticipantEventFragment
import id.shobrun.ukmmobile.ui.user.login.LoginFragment
import id.shobrun.ukmmobile.ui.user.register.RegisterFragment


private val TAB_TITLES = arrayOf(
    R.string.tab_sign_in,
    R.string.tab_sign_up

)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class UserSignPagerAdapter(
    private val context: Context,
    fm: FragmentManager
) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment
        
        if(position==0) fragment = LoginFragment.newInstance()
        else fragment = RegisterFragment.newInstance()

        return fragment

    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }
}