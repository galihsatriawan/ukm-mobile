package id.shobrun.ukmmobile.ui.user.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import id.shobrun.ukmmobile.models.Resource
import id.shobrun.ukmmobile.models.Status
import id.shobrun.ukmmobile.models.entity.Profile
import id.shobrun.ukmmobile.models.entity.User
import id.shobrun.ukmmobile.models.network.UsersResponse
import id.shobrun.ukmmobile.repository.UserRepository
import id.shobrun.ukmmobile.utils.AbsentLiveData
import id.shobrun.ukmmobile.utils.Helper
import id.shobrun.ukmmobile.utils.Helper.isValidEmail
import timber.log.Timber
import javax.inject.Inject

class RegisterViewModel @Inject constructor(repository: UserRepository) : ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()
    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val telp = MutableLiveData<String>()
    val registerAction = MutableLiveData<Boolean>()
    val loginAction = MutableLiveData<Boolean>()
    private val _snackbarText = MutableLiveData<String>()
    private val userMutable = MutableLiveData<User>()
    private val profileMutable = MutableLiveData<Profile>()
    val snackbarText: LiveData<String> = _snackbarText
    val loading: LiveData<Boolean>
    val isSuccess = MutableLiveData<Boolean>()
    val registerResponse: LiveData<Resource<User, UsersResponse>>

    init {
        registerResponse = profileMutable.switchMap {
            userMutable.value?.let {user ->
                profileMutable.value?.let {profile ->
                    repository.registerUser(user,profile)
                }

            } ?: AbsentLiveData.create()
        }
        loading = registerResponse.switchMap {
            var isLoading = true
            if(it!=null) isLoading= it.status == Status.LOADING
            if (!isLoading) {
                Timber.d("${it.message ?: it.additionalData?.message}")
                if (it.status == Status.ERROR) _snackbarText.value = "Please Check Your Connection"
                else _snackbarText.value = it.additionalData?.message
                if (it.data != null) isSuccess.value = true
                Timber.d("${it.data}")
            }
            MutableLiveData(isLoading)
        }
    }

    fun clickRegisterUser() {
        val currentFirstName= firstName.value
        val currentLastName = lastName.value
        val currentPassword = password.value
        val currentEmail = email.value
        val currentTelp = telp.value

        if (currentFirstName.isNullOrEmpty() ||currentLastName.isNullOrEmpty() || currentPassword.isNullOrEmpty() || currentEmail.isNullOrEmpty() || currentTelp.isNullOrEmpty()) {
            _snackbarText.value = "Please fill completely"
            return
        }
        if(isValidEmail(currentEmail).isNullOrEmpty()) {
            _snackbarText.value = "Your email is not valid"
            return
        }
        if (registerAction.value == null) registerAction.value = true
        else registerAction.value = !registerAction.value!!
        val user = User(
            null,
            currentEmail,
            currentPassword,
            currentEmail,
            Helper.getNRPByEmail(currentEmail),
            1,
            "USER",
            Helper.getCurrentDatetime(),
            Helper.getCurrentDatetime()
        )

        val profile = Profile(
            Helper.getNRPByEmail(currentEmail),
            currentFirstName,
            currentLastName,
            currentEmail.subSequence(0,1).toString(),
            currentTelp,
            true,
            "Male",
            "",
            Helper.getCurrentDatetime(),
            Helper.getCurrentDatetime(),
            1,
            currentEmail
        )
        Timber.d(user.toString())
        Timber.d(profile.toString())
        userMutable.value = user
        profileMutable.value = profile
    }

    fun clickLogin() {
        if (loginAction.value == null) loginAction.value = true
        else loginAction.value = !loginAction.value!!
    }
}