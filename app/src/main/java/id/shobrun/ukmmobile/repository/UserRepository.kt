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

                profile.email = email
                profile.roleId = role?.id

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
        NetworkBoundRepository<User, UsersResponse, UserResponseTransporter>(appExecutors) {
        override fun saveFetchData(items: UsersResponse) {
            if (items.result !=null ) {
                Timber.d("${items.result}")
                localDB.insert(items.result)
            }
        }

        override fun shouldFetch(data: User?): Boolean {
            return true
        }

        override fun loadFromDb(): LiveData<User> {
            return localDB.getDetailUserByEmail(user.email)
        }

        override fun fetchService(): LiveData<ApiResponse<UsersResponse>> {
            val data = hashMapOf(
                "userprofile" to profile,
                "email" to user.email,
                "password" to user.password,
                "role_id" to user.roleId
            )
            Timber.d(data.toString())

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