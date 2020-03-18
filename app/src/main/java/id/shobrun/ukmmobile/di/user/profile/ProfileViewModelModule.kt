package id.shobrun.ukmmobile.di.user.profile

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.shobrun.ukmmobile.di.ViewModelKey
import id.shobrun.ukmmobile.ui.profile.ProfileViewModel

@Module
abstract class ProfileViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileVM(profileViewModel: ProfileViewModel): ViewModel
}