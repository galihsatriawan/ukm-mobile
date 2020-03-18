package id.shobrun.ukmmobile.di.event.detail

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.shobrun.ukmmobile.di.ViewModelKey
import id.shobrun.ukmmobile.ui.myevents.detail.ParticipantSelectionViewModel

@Module
abstract class ParticipantSelectionViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ParticipantSelectionViewModel::class)
    abstract fun bindParticipantSelectionVM(participantSelectionViewModel: ParticipantSelectionViewModel): ViewModel
}