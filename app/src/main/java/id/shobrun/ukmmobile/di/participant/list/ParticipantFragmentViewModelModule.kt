package id.shobrun.ukmmobile.di.participant.list

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.shobrun.ukmmobile.di.ViewModelKey
import id.shobrun.ukmmobile.ui.myparticipants.MyParticipantsViewModel

@Module
abstract class ParticipantFragmentViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MyParticipantsViewModel::class)
    abstract fun bindParticipantsViewModel(myParticipantsViewModel: MyParticipantsViewModel): ViewModel
}