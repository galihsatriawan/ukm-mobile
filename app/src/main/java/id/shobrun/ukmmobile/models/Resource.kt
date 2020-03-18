package id.shobrun.ukmmobile.models


import com.google.gson.Gson
import id.shobrun.ukmmobile.models.network.ErrorEnvelope


/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
@Suppress(
    "PARAMETER_NAME_CHANGED_ON_OVERRIDE",
    "LiftReturnOrAssignment",
    "RedundantOverride",
    "SpellCheckingInspection"
)
data class Resource<out T, out S>(
    val status: Status,
    val data: T?,
    val additionalData: S?,
    val message: String?
) {

    var errorEnvelope: ErrorEnvelope? = null

    init {
        message?.let {
            try {
                val gson = Gson()
                errorEnvelope = gson.fromJson(message, ErrorEnvelope::class.java) as ErrorEnvelope
            } catch (e: Throwable) {
                errorEnvelope = ErrorEnvelope(400, message, false)
            }
        }
    }

    companion object {
        fun <T, S> success(data: T?, additionalData: S?): Resource<T, S> {
            return Resource(
                status = Status.SUCCESS,
                data = data,
                additionalData = additionalData,
                message = null
            )
        }

        fun <T, S> error(msg: String, data: T?, additionalData: S?): Resource<T, S> {
            return Resource(
                status = Status.ERROR,
                data = data,
                additionalData = additionalData,
                message = msg
            )
        }

        fun <T, S> loading(data: T?, additionalData: S?): Resource<T, S> {
            return Resource(
                status = Status.LOADING,
                data = data,
                additionalData = additionalData,
                message = null
            )
        }
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }

        val resource = o as Resource<*, *>

        if (status !== resource.status) {
            return false
        }
        if (if (message != null) message != resource.message else resource.message != null) {
            return false
        }
        return if (data != null) data == resource.data else resource.data == null
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return "Resource[" +
                "status=" + status + '\'' +
                ",message='" + message + '\'' +
                ",data=" + data + '\'' +
                ",additional= " + additionalData +
                ']'
    }

}
