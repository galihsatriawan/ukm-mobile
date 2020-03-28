package id.shobrun.ukmmobile.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import id.shobrun.ukmmobile.di.event.EventNetworkModule
import id.shobrun.ukmmobile.di.event.EventPersistenceModule
import id.shobrun.ukmmobile.di.event.EventRepositoryModule
import id.shobrun.ukmmobile.di.event.detail.EventMainViewModelModule
import id.shobrun.ukmmobile.di.event.detail.ParticipantSelectionModule
import id.shobrun.ukmmobile.di.event.detail.ParticipantSelectionViewModelModule
import id.shobrun.ukmmobile.di.event.scanner.ScannerModule
import id.shobrun.ukmmobile.di.event.scanner.ScannerViewModelModule
import id.shobrun.ukmmobile.di.invitation.InvitationNetworkModule
import id.shobrun.ukmmobile.di.invitation.InvitationPersistenceModule
import id.shobrun.ukmmobile.di.invitation.InvitationRepositoryModule
import id.shobrun.ukmmobile.di.invitation.detail.InvitationDetailModule
import id.shobrun.ukmmobile.di.invitation.detail.InvitationDetailViewModelModule
import id.shobrun.ukmmobile.di.participant.ParticipantNetworkModule
import id.shobrun.ukmmobile.di.participant.ParticipantPersistenceModule
import id.shobrun.ukmmobile.di.participant.ParticipantRepositoryModule
import id.shobrun.ukmmobile.di.participant.detail.ParticipantDetailModule
import id.shobrun.ukmmobile.di.participant.detail.ParticipantDetailViewModelModule
import id.shobrun.ukmmobile.di.user.UserNetworkModule
import id.shobrun.ukmmobile.di.user.UserPersistenceModule
import id.shobrun.ukmmobile.di.user.UserRepositoryModule
import id.shobrun.ukmmobile.di.user.login.LoginModule
import id.shobrun.ukmmobile.di.user.login.LoginViewModelModule
import id.shobrun.ukmmobile.di.user.register.RegisterModule
import id.shobrun.ukmmobile.di.user.register.RegisterViewModelModule
import id.shobrun.ukmmobile.ui.SplashScreen
import id.shobrun.ukmmobile.ui.invitations.detail.InvitationDetailActivity
import id.shobrun.ukmmobile.ui.myevents.detail.EventDetailActivity
import id.shobrun.ukmmobile.ui.myevents.detail.ParticipantSelectionActivity
import id.shobrun.ukmmobile.ui.myevents.scanner.ScannerActivity
import id.shobrun.ukmmobile.ui.myparticipants.detail.ParticipantDetailActivity


@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector(
        modules = [
            EventMainViewModelModule::class,
            EventNetworkModule::class,
            EventPersistenceModule::class,
            EventRepositoryModule::class,
            InvitationNetworkModule::class,
            InvitationPersistenceModule::class,
            InvitationRepositoryModule::class
        ]
    )
    abstract fun injectEventDetailActivity() : EventDetailActivity
    @ContributesAndroidInjector(
        modules = [
            InvitationDetailModule::class,
            InvitationDetailViewModelModule::class,
            InvitationNetworkModule::class,
            InvitationPersistenceModule::class,
            InvitationRepositoryModule::class,
            EventNetworkModule::class,
            EventPersistenceModule::class,
            EventRepositoryModule::class
        ]
    )
    abstract fun injectInvitationDetailActivity(): InvitationDetailActivity

    @ContributesAndroidInjector(
        modules = [
            ParticipantSelectionModule::class,
            ParticipantSelectionViewModelModule::class,
            InvitationNetworkModule::class,
            InvitationPersistenceModule::class,
            InvitationRepositoryModule::class
        ]
    )
    abstract fun injectParticipantSelectionActivity(): ParticipantSelectionActivity

    @ContributesAndroidInjector(
        modules = [
            ScannerModule::class,
            ScannerViewModelModule::class,
            InvitationNetworkModule::class,
            InvitationPersistenceModule::class,
            InvitationRepositoryModule::class
        ]
    )
    abstract fun injectScannerActivity(): ScannerActivity

    @ContributesAndroidInjector(
        modules = [
            ParticipantDetailModule::class,
            ParticipantDetailViewModelModule::class,
            ParticipantNetworkModule::class,
            ParticipantPersistenceModule::class,
            ParticipantRepositoryModule::class
        ]
    )
    abstract fun injectParticipantDetailActivity(): ParticipantDetailActivity

    @ContributesAndroidInjector
    abstract fun injectSplashScreenActivity(): SplashScreen
}