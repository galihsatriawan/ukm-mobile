package id.shobrun.ukmmobile.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.Nullable

import androidx.lifecycle.LifecycleOwner

import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Lifecycle.Event
import dagger.android.support.DaggerFragment


/**
 * Fragment providing separate lifecycle owners for each created view hierarchy.
 *
 *
 * This is one possible way to solve issue https://github.com/googlesamples/android-architecture-components/issues/47
 *
 * @author Christophe Beyls
 */
class ViewLifecycleFragment : DaggerFragment() {
    internal class ViewLifecycleOwner : LifecycleOwner {
        private val lifecycleRegistry = LifecycleRegistry(this)
        override fun getLifecycle(): LifecycleRegistry {
            return lifecycleRegistry
        }
    }

    @Nullable
    private lateinit var viewLifecycleOwner: ViewLifecycleOwner

    /**
     * @return the Lifecycle owner of the current view hierarchy,
     * or null if there is no current view hierarchy.
     */
    @Nullable
    override fun getViewLifecycleOwner(): LifecycleOwner {
        return viewLifecycleOwner
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner = ViewLifecycleOwner()
        viewLifecycleOwner!!.lifecycle.handleLifecycleEvent(Event.ON_CREATE)
    }

    override fun onStart() {
        super.onStart()
        if (viewLifecycleOwner != null) {
            viewLifecycleOwner!!.lifecycle.handleLifecycleEvent(Event.ON_START)
        }
    }

    override fun onResume() {
        super.onResume()
        if (viewLifecycleOwner != null) {
            viewLifecycleOwner!!.lifecycle.handleLifecycleEvent(Event.ON_RESUME)
        }
    }

    override fun onPause() {
        if (viewLifecycleOwner != null) {
            viewLifecycleOwner!!.lifecycle.handleLifecycleEvent(Event.ON_PAUSE)
        }
        super.onPause()
    }

    override fun onStop() {
        if (viewLifecycleOwner != null) {
            viewLifecycleOwner!!.lifecycle.handleLifecycleEvent(Event.ON_STOP)
        }
        super.onStop()
    }

    override fun onDestroyView() {
        if (viewLifecycleOwner != null) {
            viewLifecycleOwner!!.lifecycle.handleLifecycleEvent(Event.ON_DESTROY)
//            viewLifecycleOwner = null
        }
        super.onDestroyView()
    }
}
