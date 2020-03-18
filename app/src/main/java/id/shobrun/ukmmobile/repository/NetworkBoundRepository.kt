package id.shobrun.ukmmobile.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import id.shobrun.ukmmobile.AppExecutors
import id.shobrun.ukmmobile.api.ApiResponse
import id.shobrun.ukmmobile.models.NetworkResponseModel
import id.shobrun.ukmmobile.models.Resource
import id.shobrun.ukmmobile.transporter.NetworkResponseTransporter


abstract class NetworkBoundRepository<ResultType,
        RequestType : NetworkResponseModel,
        Transporter : NetworkResponseTransporter<RequestType>>
@MainThread constructor(private val appExecutors: AppExecutors) {

    private val result: MediatorLiveData<Resource<ResultType, RequestType>> = MediatorLiveData()

    init {

        val loadedFromDB = this.loadFromDb()
        result.addSource(loadedFromDB) { data ->
            result.removeSource(loadedFromDB)
            if (shouldFetch(data)) {
                result.postValue(Resource.loading(null, null))
                fetchFromNetwork(loadedFromDB)
            } else {
                result.addSource<ResultType>(loadedFromDB) { newData ->
                    setValue(Resource.success(newData, null))
                }
            }
        }
    }

    private fun fetchFromNetwork(loadedFromDB: LiveData<ResultType>) {
        val apiResponse = fetchService()
        result.addSource(apiResponse) { response ->
            response?.let {
                when (response.isSuccessful) {
                    true -> {
                        if (response.body != null) {
                            response.body.let {
                                appExecutors.diskIO().execute {
                                    saveFetchData(it)
                                    appExecutors.mainThread().execute {
                                        // we specially request a new live data,
                                        // otherwise we will get immediately last cached value,
                                        // which may not be updated with latest results received from network.
                                        val loaded = loadFromDb()
                                        result.addSource(loaded) { newData ->
                                            newData?.let {
                                                setValue(
                                                    Resource.success(
                                                        newData,
                                                        transporter().additionalData(response.body)
                                                    )
                                                )
                                            }
                                        }
                                    }

                                }
                            }
                        } else {
                            appExecutors.mainThread().execute {
                                // reload from disk whatever we had
                                result.addSource(loadFromDb()) { newData ->
                                    newData?.let {
                                        setValue(
                                            Resource.success(
                                                data = newData,
                                                additionalData = null
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                    false -> {
                        result.removeSource(loadedFromDB)
                        onFetchFailed(response.message)
                        response.message?.let {
                            result.addSource<ResultType>(loadedFromDB) { newData ->
                                setValue(Resource.error(it, data = newData, additionalData = null))
                            }
                        }
                    }
                }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType, RequestType>) {
        result.value = newValue
    }

    fun asLiveData(): LiveData<Resource<ResultType, RequestType>> {
        return result
    }

    @WorkerThread
    protected abstract fun saveFetchData(items: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun fetchService(): LiveData<ApiResponse<RequestType>>

    @MainThread
    protected abstract fun transporter(): Transporter

    @MainThread
    protected abstract fun onFetchFailed(message: String?)
}
