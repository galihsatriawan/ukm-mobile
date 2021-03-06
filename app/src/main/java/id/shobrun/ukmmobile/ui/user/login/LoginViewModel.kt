package id.shobrun.ukmmobile.ui.user.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import id.shobrun.ukmmobile.models.Resource
import id.shobrun.ukmmobile.models.Status
import id.shobrun.ukmmobile.models.entity.Profile
import id.shobrun.ukmmobile.models.network.ProfileResponse
import id.shobrun.ukmmobile.repository.UserRepository
import id.shobrun.ukmmobile.utils.AbsentLiveData
import id.shobrun.ukmmobile.utils.SharedPref
import id.shobrun.ukmmobile.utils.SharedPref.Companion.PREFS_IS_LOGIN
import id.shobrun.ukmmobile.utils.SharedPref.Companion.PREFS_USER_EMAIL
import id.shobrun.ukmmobile.utils.SharedPref.Companion.PREFS_USER_ID
import timber.log.Timber
import javax.inject.Inject

class LoginViewModel @Inject constructor(repository: UserRepository, sharedPref: SharedPref) :
    ViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    private val loginAction = MutableLiveData<Boolean>()
    val registerAction = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
    val isSuccess = MutableLiveData<Boolean>()
    private val _snackbarText = MutableLiveData<String>()
    val snackbarText: LiveData<String> = _snackbarText
    val loginRespons: LiveData<Resource<Profile, ProfileResponse>>

    init {
        /**
         * Sign out
         */
        sharedPref.removeSharedPref()
        loginRespons = loginAction.switchMap {
            loginAction.value?.let {
                email.value?.let {
                    password.value?.let {
                        repository.loginUser(email.value!!, password.value!!)
                    }
                }

            } ?: AbsentLiveData.create()
        }
        loading = loginRespons.switchMap {
            Timber.d("${it.status}")
            val isLoading = it.status == Status.LOADING
            if (!isLoading) {
                if (it.status == Status.ERROR) _snackbarText.value = "Please Check Your Connection"
                else _snackbarText.value = it.message ?: it.additionalData?.message
                if (it.data != null) {
                    isSuccess.value = true
                    /**
                     * Login
                     */
                    sharedPref.setValue(PREFS_IS_LOGIN, true)
                    sharedPref.setValue(PREFS_USER_ID, it.data.id)
                    sharedPref.setValue(PREFS_USER_EMAIL, it.data.email)
                }
            }
            MutableLiveData(isLoading)
        }
        /**
         * Delete All User
         */
        repository.localDB.delete()
    }

    fun clickLogin() {
        val currentEmail = email.value
        val currentPassword = password.value
        if (currentPassword.isNullOrEmpty() || currentEmail.isNullOrEmpty()) {
            _snackbarText.value = "Please fill completely"
            return
        }
        if (loginAction.value == null) loginAction.value = true
        else loginAction.value = !loginAction.value!!
    }
}