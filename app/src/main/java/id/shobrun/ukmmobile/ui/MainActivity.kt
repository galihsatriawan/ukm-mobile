package id.shobrun.ukmmobile.ui

import android.os.Bundle
import android.util.SparseArray
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import id.shobrun.ukmmobile.R
import id.shobrun.ukmmobile.extensions.simpleToolbarWithoutHome
import id.shobrun.ukmmobile.ui.invitations.InvitationsFragment
import id.shobrun.ukmmobile.ui.myevents.MyEventsFragment
import id.shobrun.ukmmobile.ui.myparticipants.MyParticipantsFragment
import id.shobrun.ukmmobile.ui.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val SAVED_STATE_CONTAINER_KEY = "ContainerKey"
        const val SAVED_STATE_CURRENT_TAB_KEY = "CurrentTabKey"
    }

    private var savedStateSparseArray = SparseArray<Fragment.SavedState>()
    private var currentSelectItemId = R.id.navigation_invitations
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) {
            savedStateSparseArray =
                savedInstanceState.getSparseParcelableArray(SAVED_STATE_CONTAINER_KEY)!!
            currentSelectItemId = savedInstanceState.getInt(SAVED_STATE_CURRENT_TAB_KEY)
        }
        bottom_navigation.setOnNavigationItemSelectedListener {
            var fragment: Fragment

            when (it.itemId) {
                R.id.navigation_invitations -> {
                    fragment = InvitationsFragment.newInstance()
                    swapFragments(R.id.navigation_invitations, fragment)
                }
                R.id.navigation_events -> {
                    fragment = MyEventsFragment.newInstance()
                    swapFragments(R.id.navigation_events, fragment)
                }
                R.id.navigation_participants -> {
                    fragment = MyParticipantsFragment.newInstance()
                    swapFragments(R.id.navigation_participants, fragment)
                }
                R.id.navigation_profile -> {
                    fragment = ProfileFragment.newInstance()
                    swapFragments(R.id.navigation_profile, fragment)
                }
            }



            true
        }
        bottom_navigation.selectedItemId = currentSelectItemId
        simpleToolbarWithoutHome(toolbar, getString(R.string.app_name))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSparseParcelableArray(SAVED_STATE_CONTAINER_KEY, savedStateSparseArray)
        outState.putInt(SAVED_STATE_CURRENT_TAB_KEY, currentSelectItemId)
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach { fragment ->
            if (fragment != null && fragment.isVisible) {
                with(fragment.childFragmentManager) {
                    if (backStackEntryCount > 0) {
                        popBackStack()
                        return
                    }
                }
            }
        }
        super.onBackPressed()
    }

    private fun swapFragments(@IdRes actionId: Int, fragment: Fragment) {
        if (supportFragmentManager.findFragmentByTag(fragment.javaClass.simpleName) == null) {
            savedFragmentState(actionId)
            createFragment(fragment, actionId)
        }
    }

    private fun createFragment(fragment: Fragment, actionId: Int) {
        fragment.setInitialSavedState(savedStateSparseArray[actionId])
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment, fragment.javaClass.simpleName)
            .commit()
    }

    private fun savedFragmentState(actionId: Int) {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.main_container)
        if (currentFragment != null) {
            savedStateSparseArray.put(
                currentSelectItemId,
                supportFragmentManager.saveFragmentInstanceState(currentFragment)
            )
        }
        currentSelectItemId = actionId
    }
}
