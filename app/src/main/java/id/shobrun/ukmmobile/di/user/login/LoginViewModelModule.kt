package id.shobrun.ukmmobile.di.user.login

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.shobrun.ukmmobile.di.ViewModelKey
import id.shobrun.ukmmobile.ui.user.login.LoginViewModel

@Module
abstract class LoginViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginVM(loginViewModel: LoginViewModel): ViewModel
}