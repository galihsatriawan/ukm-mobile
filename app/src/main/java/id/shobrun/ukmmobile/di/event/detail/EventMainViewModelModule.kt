package id.shobrun.ukmmobile.di.event.detail

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.shobrun.ukmmobile.di.ViewModelKey
import id.shobrun.ukmmobile.ui.myevents.detail.EventDetailMainViewModel

@Module
abstract class EventMainViewModelModule{
    @Binds
    @IntoMap
    @ViewModelKey(EventDetailMainViewModel::class)
    abstract fun bindEventMainVM (eventDetailMainViewModel: EventDetailMainViewModel) : ViewModel
}