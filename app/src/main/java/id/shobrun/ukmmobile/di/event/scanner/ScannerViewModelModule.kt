package id.shobrun.ukmmobile.di.event.scanner

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.shobrun.ukmmobile.di.ViewModelKey
import id.shobrun.ukmmobile.ui.myevents.scanner.ScannerViewModel

@Module
abstract class ScannerViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ScannerViewModel::class)
    abstract fun bindScannerVM(scannerViewModel: ScannerViewModel): ViewModel
}