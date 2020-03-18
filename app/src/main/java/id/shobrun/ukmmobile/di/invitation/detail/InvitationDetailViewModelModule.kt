package id.shobrun.ukmmobile.di.invitation.detail

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.shobrun.ukmmobile.di.ViewModelKey
import id.shobrun.ukmmobile.ui.invitations.detail.InvitationDetailViewModel

@Module
abstract class InvitationDetailViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(InvitationDetailViewModel::class)
    abstract fun bindInvitationDetailVM(invitationDetailViewModel: InvitationDetailViewModel): ViewModel
}