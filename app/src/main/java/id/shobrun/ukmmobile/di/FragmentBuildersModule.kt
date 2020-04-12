package id.shobrun.ukmmobile.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import id.shobrun.ukmmobile.di.event.EventNetworkModule
import id.shobrun.ukmmobile.di.event.EventPersistenceModule
import id.shobrun.ukmmobile.di.event.EventRepositoryModule
import id.shobrun.ukmmobile.di.event.detail.*
import id.shobrun.ukmmobile.di.event.list.EventFragmentModule
import id.shobrun.ukmmobile.di.event.list.EventFragmentViewModelModule
import id.shobrun.ukmmobile.di.invitation.InvitationNetworkModule
import id.shobrun.ukmmobile.di.invitation.InvitationPersistenceModule
import id.shobrun.ukmmobile.di.invitation.InvitationRepositoryModule
import id.shobrun.ukmmobile.di.invitation.list.InvitationFragmentModule
import id.shobrun.ukmmobile.di.invitation.list.InvitationFragmentViewModelModule
import id.shobrun.ukmmobile.di.participant.ParticipantNetworkModule
import id.shobrun.ukmmobile.di.participant.ParticipantPersistenceModule
import id.shobrun.ukmmobile.di.participant.ParticipantRepositoryModule
import id.shobrun.ukmmobile.di.participant.list.ParticipantFragmentModule
import id.shobrun.ukmmobile.di.participant.list.ParticipantFragmentViewModelModule
import id.shobrun.ukmmobile.di.user.ProfilePersistenceModule
import id.shobrun.ukmmobile.di.user.UserNetworkModule
import id.shobrun.ukmmobile.di.user.UserPersistenceModule
import id.shobrun.ukmmobile.di.user.UserRepositoryModule
import id.shobrun.ukmmobile.di.user.login.LoginModule
import id.shobrun.ukmmobile.di.user.login.LoginViewModelModule
import id.shobrun.ukmmobile.di.user.profile.ProfileModule
import id.shobrun.ukmmobile.di.user.profile.ProfileViewModelModule
import id.shobrun.ukmmobile.di.user.register.RegisterModule
import id.shobrun.ukmmobile.di.user.register.RegisterViewModelModule
import id.shobrun.ukmmobile.ui.invitations.InvitationsFragment
import id.shobrun.ukmmobile.ui.myevents.MyEventsFragment
import id.shobrun.ukmmobile.ui.myevents.detail.EventDetailFragment
import id.shobrun.ukmmobile.ui.myevents.detail.EventSummaryFragment
import id.shobrun.ukmmobile.ui.myevents.detail.ParticipantEventFragment
import id.shobrun.ukmmobile.ui.myparticipants.MyParticipantsFragment
import id.shobrun.ukmmobile.ui.profile.ProfileFragment
import id.shobrun.ukmmobile.ui.user.login.LoginFragment
import id.shobrun.ukmmobile.ui.user.register.RegisterFragment

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector(
        modules = [
            ParticipantFragmentModule::class,
            ParticipantFragmentViewModelModule::class,
            ParticipantNetworkModule::class,
            ParticipantPersistenceModule::class,
            ParticipantRepositoryModule::class
        ]
    )
    abstract fun injectParticipantFragment(): MyParticipantsFragment

    @ContributesAndroidInjector(
        modules = [
            InvitationFragmentModule::class,
            InvitationFragmentViewModelModule::class,
            InvitationNetworkModule::class,
            InvitationPersistenceModule::class,
            InvitationRepositoryModule::class
        ]
    )
    abstract fun injectInvitationFragment(): InvitationsFragment

    @ContributesAndroidInjector(
        modules = [
            EventFragmentModule::class,
            EventFragmentViewModelModule::class,
            EventNetworkModule::class,
            EventPersistenceModule::class,
            EventRepositoryModule::class
        ]
    )
    abstract fun injectEventFragment(): MyEventsFragment

    @ContributesAndroidInjector(
        modules = [

            EventDetailModule::class,
            EventDetailViewModelModule::class,
            EventNetworkModule::class,
            EventPersistenceModule::class,
            EventRepositoryModule::class,
            EventMainViewModelModule::class,
            InvitationNetworkModule::class,
            InvitationPersistenceModule::class,
            InvitationRepositoryModule::class
        ]
    )
    abstract fun injectEventDetailFragment(): EventDetailFragment

    @ContributesAndroidInjector(
        modules = [
            EventMainViewModelModule::class,
            EventSummaryModule::class,
            EventSummaryViewModelModule::class,
            EventNetworkModule::class,
            EventPersistenceModule::class,
            EventRepositoryModule::class,
            InvitationNetworkModule::class,
            InvitationPersistenceModule::class,
            InvitationRepositoryModule::class
        ]
    )
    abstract fun injectEventSummaryFragment(): EventSummaryFragment

    @ContributesAndroidInjector(
        modules = [
            ParticipantEventModule::class,
            ParticipantEventViewModelModule::class,
            InvitationNetworkModule::class,
            InvitationPersistenceModule::class,
            InvitationRepositoryModule::class,
            EventNetworkModule::class,
            EventPersistenceModule::class,
            EventRepositoryModule::class,
            EventMainViewModelModule::class
        ]
    )
    abstract fun injectParticipantEventFragment(): ParticipantEventFragment

    @ContributesAndroidInjector(
        modules = [
            ProfileModule::class,
            ProfileViewModelModule::class,
            UserNetworkModule::class,
            UserPersistenceModule::class,
            UserRepositoryModule::class,
            ProfilePersistenceModule::class
        ]
    )
    abstract fun injectProfileFragment(): ProfileFragment

    @ContributesAndroidInjector(
        modules = [
            LoginModule::class,
            LoginViewModelModule::class,
            UserNetworkModule::class,
            UserPersistenceModule::class,
            UserRepositoryModule::class,
            ProfilePersistenceModule::class
        ]
    )
    abstract fun injectLoginFragment(): LoginFragment

    @ContributesAndroidInjector(
        modules = [
            RegisterModule::class,
            RegisterViewModelModule::class,
            UserNetworkModule::class,
            UserPersistenceModule::class,
            UserRepositoryModule::class,
            ProfilePersistenceModule::class
        ]
    )
    abstract fun injectRegisterFragment(): RegisterFragment

}