package id.shobrun.ukmmobile.di.invitation

import dagger.Module
import dagger.Provides
import id.shobrun.ukmmobile.room.AppDatabase

@Module
class InvitationPersistenceModule {
    @Provides
    fun provideInvitationDao(appDatabase: AppDatabase) = appDatabase.invitationDao()
}