package id.shobrun.ukmmobile.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import dagger.android.support.DaggerFragment
import id.shobrun.ukmmobile.R
import id.shobrun.ukmmobile.ui.user.login.LoginActivity
import id.shobrun.ukmmobile.utils.Helper.openBrowser
import id.shobrun.ukmmobile.utils.Helper.sendEmail
import id.shobrun.ukmmobile.utils.SharedPref
import id.shobrun.ukmmobile.utils.SharedPref.Companion.PREFS_USER_EMAIL
import id.shobrun.ukmmobile.utils.SharedPref.Companion.PREFS_USER_USERNAME
import id.shobrun.ukmmobile.utils.Tools
import org.jetbrains.anko.support.v4.intentFor
import javax.inject.Inject

class ProfileFragment : DaggerFragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    val viewModel: ProfileViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        childFragmentManager
            .beginTransaction()
            .replace(R.id.profile, SettingsFragment())
            .commit()
        // TODO: Use the ViewModel
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }

        lateinit var sharedPref: SharedPref
        lateinit var tools: Tools
        lateinit var username: Preference
        lateinit var email: Preference
        lateinit var signoutPreference: Preference
        lateinit var helpPreference: Preference
        lateinit var creator: Preference
        lateinit var institution: Preference
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            sharedPref = SharedPref(requireActivity().application)
            username = preferenceManager.findPreference("username")!!
            username.summary = sharedPref.getValue(PREFS_USER_USERNAME, "username")
            email = preferenceManager.findPreference("email")!!
            email.summary = sharedPref.getValue(PREFS_USER_EMAIL, "email")

            signoutPreference = preferenceManager.findPreference<Preference>("signout")!!
            signoutPreference.setOnPreferenceClickListener {
                var signin = intentFor<LoginActivity>()
                startActivity(signin)
                requireActivity().finish()
                true
            }

            helpPreference = preferenceManager.findPreference("help")!!
            helpPreference.setOnPreferenceClickListener {
                val help = Intent(requireContext(), HelpActivity::class.java)
                requireContext().startActivity(help)
                true
            }

            creator = preferenceManager.findPreference("email_creator")!!
            creator.setOnPreferenceClickListener {
                val intent = sendEmail(getString(R.string.creator_email), "Your Subject")
                requireActivity().startActivity(Intent.createChooser(intent, "Send Email"))
                true
            }

            institution = preferenceManager.findPreference("institution")!!
            institution.setOnPreferenceClickListener {
                val institut = openBrowser(getString(R.string.web_institution))
                requireActivity().startActivity(institut)
                true
            }


            return super.onCreateView(inflater, container, savedInstanceState)
        }
    }
}
