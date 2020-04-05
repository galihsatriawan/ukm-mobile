package id.shobrun.ukmmobile.repository

import androidx.lifecycle.LiveData
import id.shobrun.ukmmobile.AppExecutors
import id.shobrun.ukmmobile.api.ApiResponse
import id.shobrun.ukmmobile.api.UserApi
import id.shobrun.ukmmobile.models.entity.Profile
import id.shobrun.ukmmobile.models.entity.Role
import id.shobrun.ukmmobile.models.entity.User
import id.shobrun.ukmmobile.models.network.UsersResponse
import id.shobrun.ukmmobile.room.ProfileDao
import id.shobrun.ukmmobile.room.UserDao
import id.shobrun.ukmmobile.transporter.UserResponseTransporter
import id.shobrun.ukmmobile.utils.Helper.md5
import id.shobrun.ukmmobile.utils.Tools
import timber.log.Timber
import javax.inject.Inject

class UserRepository @Inject constructor(
    val apiService: UserApi,
    val localDB: UserDao,
    val profileDB: ProfileDao,
    val appExecutors: AppExecutors
) {
    @Inject
    lateinit var tools: Tools

    fun loginUser(email: String, password: String) = object :
        NetworkBoundRepository<List<Profile>, UsersResponse, UserResponseTransporter>(appExecutors) {
        override fun saveFetchData(items: UsersResponse) {
            Timber.d("${items.message} ${items.status}")
            var role: Role? = items.result_role
            var profile: Profile? = items.result_profile
            if (profile != null) {
                profileDB.deleteByEmail(email)

                profile.user_email = email
                profile.role_id = role?.id

                profileDB.insert(profile)
            }

        }

        override fun shouldFetch(data: List<Profile>?): Boolean {
            return true
        }

        override fun loadFromDb(): LiveData<List<Profile>> {
            return profileDB.getProfileDetail(email)
        }

        override fun fetchService(): LiveData<ApiResponse<UsersResponse>> {
            val data = hashMapOf(
                "email" to email,
                "password" to password
            )
            return apiService.loginUser(data)
        }

        override fun transporter(): UserResponseTransporter {
            return UserResponseTransporter()
        }

        override fun onFetchFailed(message: String?) {
            Timber.d("$message")
        }

    }.asLiveData()

    fun registerUser(user: User) = object :
        NetworkBoundRepository<List<User>, UsersResponse, UserResponseTransporter>(appExecutors) {
        override fun saveFetchData(items: UsersResponse) {
            if (!items.result.isNullOrEmpty()) {
                Timber.d("${items.result[0]}")
                localDB.insert(items.result[0])
            }
        }

        override fun shouldFetch(data: List<User>?): Boolean {
            return true
        }

        override fun loadFromDb(): LiveData<List<User>> {
            return localDB.getDetailUserByUsername(user.user_username, md5(md5(user.user_password)))
        }

        override fun fetchService(): LiveData<ApiResponse<UsersResponse>> {
            val tempUser = user.copy()
            tempUser.user_password = md5(user.user_password)
            val data = hashMapOf(
                "user" to tempUser
            )
            return apiService.registerUser(data)
        }

        override fun transporter(): UserResponseTransporter {
            return UserResponseTransporter()
        }

        override fun onFetchFailed(message: String?) {
            Timber.d("$message")
        }
    }.asLiveData()

}