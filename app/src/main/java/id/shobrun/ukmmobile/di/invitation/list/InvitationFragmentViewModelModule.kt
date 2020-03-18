package id.shobrun.ukmmobile.di.invitation.list

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.shobrun.ukmmobile.di.ViewModelKey
import id.shobrun.ukmmobile.ui.invitations.InvitationsViewModel

@Module
abstract class InvitationFragmentViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(InvitationsViewModel::class)
    abstract fun bindInvitationViewModel(invitationsViewModel: InvitationsViewModel): ViewModel
}