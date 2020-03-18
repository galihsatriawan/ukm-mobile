package id.shobrun.ukmmobile.di.event.detail

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.shobrun.ukmmobile.di.ViewModelKey
import id.shobrun.ukmmobile.ui.myevents.detail.ParticipantEventViewModel


@Module
abstract class ParticipantEventViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ParticipantEventViewModel::class)
    abstract fun bindParticipantEventViewModel(participantEventViewModel: ParticipantEventViewModel): ViewModel
}