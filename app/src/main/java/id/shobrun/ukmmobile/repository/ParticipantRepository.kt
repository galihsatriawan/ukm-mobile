package id.shobrun.ukmmobile.repository

import androidx.lifecycle.LiveData
import id.shobrun.ukmmobile.AppExecutors
import id.shobrun.ukmmobile.api.ApiResponse
import id.shobrun.ukmmobile.api.ParticipantApi
import id.shobrun.ukmmobile.models.Resource
import id.shobrun.ukmmobile.models.entity.Participant
import id.shobrun.ukmmobile.models.network.ParticipantsResponse
import id.shobrun.ukmmobile.repository.local.ILocalSource
import id.shobrun.ukmmobile.room.ParticipantDao
import id.shobrun.ukmmobile.transporter.ParticipantResponseTransporter
import timber.log.Timber
import javax.inject.Inject

class ParticipantRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val apiService: ParticipantApi,
    private val localDB: ParticipantDao
) : ILocalSource {
    fun getParticipantDetail(id: String): LiveData<Resource<Participant, ParticipantsResponse>> {
        return object :
            NetworkBoundRepository<Participant, ParticipantsResponse, ParticipantResponseTransporter>(
                appExecutors
            ) {
            override fun saveFetchData(items: ParticipantsResponse) {
                if (items.result.isNotEmpty()) {
                    val item = items.result[0]
                    Timber.d("Service ${item.participant_name}")
                    localDB.insert(item)
                }
            }

            override fun shouldFetch(data: Participant?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<Participant> {
                val item = localDB.getDetailParticipant(id)
                return item
            }

            override fun fetchService(): LiveData<ApiResponse<ParticipantsResponse>> {
                return apiService.getDetailParticipant(id)
            }

            override fun onFetchFailed(message: String?) {
                Timber.d("$message")
            }

            override fun transporter(): ParticipantResponseTransporter {
                return ParticipantResponseTransporter()
            }

        }.asLiveData()
    }

    fun getMyParticipants(id: Int): LiveData<Resource<List<Participant>, ParticipantsResponse>> {
        return object :
            NetworkBoundRepository<List<Participant>, ParticipantsResponse, ParticipantResponseTransporter>(
                appExecutors
            ) {
            override fun saveFetchData(items: ParticipantsResponse) {
                val participants = items.result
                if (participants.isNotEmpty()) {
                    localDB.inserts(items.result)
                }
            }

            override fun shouldFetch(data: List<Participant>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<Participant>> {
                return localDB.getMyParticipants(id)
            }

            override fun fetchService(): LiveData<ApiResponse<ParticipantsResponse>> {
                return apiService.getMyParticipants(id)
            }

            override fun onFetchFailed(message: String?) {
                Timber.d("$message")
            }

            override fun transporter(): ParticipantResponseTransporter {
                return ParticipantResponseTransporter()
            }

        }.asLiveData()
    }

    fun insertObj(participant: Participant) = object :
        NetworkBoundRepository<Participant, ParticipantsResponse, ParticipantResponseTransporter>(
            appExecutors
        ) {
        override fun saveFetchData(items: ParticipantsResponse) {
            if (!items.result.isNullOrEmpty()) {
                localDB.insert(items.result[0])
            }
        }

        override fun shouldFetch(data: Participant?): Boolean {
            return true
        }

        override fun loadFromDb(): LiveData<Participant> {
            return localDB.getDetailParticipantByEmail(participant.participant_email)
        }

        override fun fetchService(): LiveData<ApiResponse<ParticipantsResponse>> {
            val data = HashMap<String, Participant>()
            data["participant"] = participant
            return apiService.addParticipant(data)
        }

        override fun onFetchFailed(message: String?) {
            Timber.d("$message")
        }

        override fun transporter(): ParticipantResponseTransporter {
            return ParticipantResponseTransporter()
        }
    }.asLiveData()

    fun updateObj(participant: Participant) = object :
        NetworkBoundRepository<Participant, ParticipantsResponse, ParticipantResponseTransporter>(
            appExecutors
        ) {
        override fun saveFetchData(items: ParticipantsResponse) {
            if (!items.result.isNullOrEmpty()) {
                localDB.update(items.result[0])
            } else {
                localDB.update(participant)
            }
        }

        override fun shouldFetch(data: Participant?): Boolean {
            return true
        }

        override fun loadFromDb(): LiveData<Participant> {
            return localDB.getDetailParticipant(participant.participant_id)
        }

        override fun fetchService(): LiveData<ApiResponse<ParticipantsResponse>> {
            val data = HashMap<String, Participant>()
            data["participant"] = participant
            return apiService.updateParticipant(data)
        }

        override fun onFetchFailed(message: String?) {
            Timber.d("$message")
        }

        override fun transporter(): ParticipantResponseTransporter {
            return ParticipantResponseTransporter()
        }

    }.asLiveData()

    override fun <T> insertsLocal(obj: List<T>) {
        val participants = obj as List<Participant>
        localDB.inserts(participants)
    }

    override fun <T> insertLocal(obj: T) {
        val participant = obj as Participant
        Timber.d(participant.participant_name)
        localDB.insert(participant)
    }

    override fun <T> updateLocal(obj: T): Int {
        val participant = obj as Participant
        return localDB.update(participant)
    }

    override fun <T> deleteLocal(obj: T) {
        val participant = obj as Participant
        localDB.delete(participant.participant_id)
    }
}