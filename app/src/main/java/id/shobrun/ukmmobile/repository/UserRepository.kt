package id.shobrun.ukmmobile.repository

import androidx.lifecycle.LiveData
import id.shobrun.ukmmobile.AppExecutors
import id.shobrun.ukmmobile.api.ApiResponse
import id.shobrun.ukmmobile.api.UserApi
import id.shobrun.ukmmobile.models.entity.Profile
import id.shobrun.ukmmobile.models.entity.Role
import id.shobrun.ukmmobile.models.entity.User
import id.shobrun.ukmmobile.models.network.ProfileResponse
import id.shobrun.ukmmobile.models.network.UsersResponse
import id.shobrun.ukmmobile.room.ProfileDao
import id.shobrun.ukmmobile.room.UserDao
import id.shobrun.ukmmobile.transporter.ProfileResponseTransporter
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
        NetworkBoundRepository<Profile, ProfileResponse, ProfileResponseTransporter>(appExecutors) {
        override fun saveFetchData(items: ProfileResponse) {
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

        override fun shouldFetch(data: Profile?): Boolean {
            return true
        }

        override fun loadFromDb(): LiveData<Profile> {
            return profileDB.getProfileDetail(email)
        }

        override fun fetchService(): LiveData<ApiResponse<ProfileResponse>> {
            val data = hashMapOf(
                "email" to email,
                "password" to password
            )
            return apiService.loginUser(data)
        }

        override fun transporter(): ProfileResponseTransporter {
            return ProfileResponseTransporter()
        }

        override fun onFetchFailed(message: String?) {
            Timber.d("$message")
        }

    }.asLiveData()

    fun registerUser(user: User,profile: Profile) = object :
        NetworkBoundRepository<List<User>, UsersResponse, UserResponseTransporter>(appExecutors) {
        override fun saveFetchData(items: UsersResponse) {
            if (items.result !=null ) {
                Timber.d("${items.result}")
                localDB.insert(items.result)
            }
        }

        override fun shouldFetch(data: List<User>?): Boolean {
            return true
        }

        override fun loadFromDb(): LiveData<List<User>> {
            return localDB.getDetailUserByEmail(user.user_email)
        }

        override fun fetchService(): LiveData<ApiResponse<UsersResponse>> {
            val tempUser = user.copy()
            val tempProfile = profile.copy()
            val data = hashMapOf(
                "email" to tempUser.user_email,
                "password" to tempUser.user_password,
                "role_id" to tempUser.user_role_id,
                "userprofile" to tempProfile
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