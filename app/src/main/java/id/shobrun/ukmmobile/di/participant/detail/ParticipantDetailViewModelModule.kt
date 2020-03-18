package id.shobrun.ukmmobile.di.participant.detail

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.shobrun.ukmmobile.di.ViewModelKey
import id.shobrun.ukmmobile.ui.myparticipants.detail.ParticipantDetailViewModel

@Module
abstract class ParticipantDetailViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ParticipantDetailViewModel::class)
    abstract fun bindParticipantDetailVM(participantDetailViewModel: ParticipantDetailViewModel): ViewModel
}