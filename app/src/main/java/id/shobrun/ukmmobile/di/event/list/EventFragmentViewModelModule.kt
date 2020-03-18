package id.shobrun.ukmmobile.di.event.list

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.shobrun.ukmmobile.di.ViewModelKey
import id.shobrun.ukmmobile.ui.myevents.MyEventsViewModel

@Module
abstract class EventFragmentViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MyEventsViewModel::class)
    abstract fun bindMyEventsViewModel(myEventsViewModel: MyEventsViewModel): ViewModel
}