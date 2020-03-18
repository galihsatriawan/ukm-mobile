package id.shobrun.ukmmobile.ui.profile

import androidx.lifecycle.ViewModel
import id.shobrun.ukmmobile.repository.UserRepository
import id.shobrun.ukmmobile.utils.SharedPref
import javax.inject.Inject

class ProfileViewModel @Inject constructor(val repository: UserRepository, sharedPref: SharedPref) :
    ViewModel() {
    // TODO: Implement the ViewModel
}
