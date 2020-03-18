package id.shobrun.ukmmobile.di.event.detail

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.shobrun.ukmmobile.di.ViewModelKey
import id.shobrun.ukmmobile.ui.myevents.detail.EventDetailViewModel

@Module
abstract class EventDetailViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(EventDetailViewModel::class)
    abstract fun bindEventDetailVM(eventDetailViewModel: EventDetailViewModel): ViewModel
}