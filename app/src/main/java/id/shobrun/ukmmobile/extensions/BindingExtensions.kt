package id.shobrun.ukmmobile.extensions

import android.view.View
import id.shobrun.ukmmobile.R
import id.shobrun.ukmmobile.models.Resource
import id.shobrun.ukmmobile.models.Status
import org.jetbrains.anko.design.snackbar

inline fun <reified T, reified S> View.bindResource(
    resource: Resource<T, S>?,
    onSuccessOrError: (Resource<T, S>) -> Unit
) {
    if (resource != null) {
        when (resource.status) {
            Status.LOADING -> Unit
            Status.SUCCESS -> onSuccessOrError(resource)
            Status.ERROR -> {
                onSuccessOrError(resource)
                snackbar(this.context.resources.getString(R.string.failed_load))
            }
        }
    }
}
