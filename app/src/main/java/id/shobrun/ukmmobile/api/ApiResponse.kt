package id.shobrun.ukmmobile.api

import retrofit2.Response
import timber.log.Timber
import java.io.IOException


class ApiResponse<T> {
    val code: Int
    val body: T?
    val message: String?

    val isSuccessful: Boolean
        get() = code in 200..300
    private val isFailure: Boolean

    constructor(error: Throwable) {
        this.code = 500
        this.body = null
        this.message = error.message
        this.isFailure = true
    }

    constructor(response: Response<T>) {
        Timber.d("Api Response Header : ${response.headers()}")
        Timber.d("Api Response Raw : ${response.raw()}")
        Timber.d("Api Response Raw : ${response.body().toString()}")
        Timber.d("Api Response Raw : ${response.errorBody()}")
        this.code = response.code()

        if (response.isSuccessful) {
            this.body = response.body()
            this.message = null
            this.isFailure = false
            Timber.d("Api Response : ${this.body}")
        } else {
            var errorMessage: String? = null
            response.errorBody()?.let {
                try {
                    errorMessage = response.errorBody()!!.string()
                } catch (ignored: IOException) {
                    Timber.e(ignored, "error while parsing response")
                }
            }

            errorMessage?.apply {
                if (isNullOrEmpty() || trim { it <= ' ' }.isEmpty()) {
                    errorMessage = response.message()
                }
            }

            this.body = null
            this.message = errorMessage
            this.isFailure = true
        }
    }
}
