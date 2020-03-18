package id.shobrun.ukmmobile.ui.myevents.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import dagger.android.support.DaggerAppCompatActivity
import id.shobrun.ukmmobile.R
import id.shobrun.ukmmobile.extensions.simpleToolbarWithHome
import id.shobrun.ukmmobile.models.entity.Event
import id.shobrun.ukmmobile.ui.adapter.EventDetailPagerAdapter
import kotlinx.android.synthetic.main.activity_event_detail.*
import timber.log.Timber
import javax.inject.Inject

class EventDetailActivity : DaggerAppCompatActivity() {
    companion object {
        val EXTRA_EVENT = "extra_event"
        var currentEventId: String? = null
        var isNewEvent: Boolean = false
    }
    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory

    private val viewModel : EventDetailMainViewModel by viewModels{viewModelFactory}

    var event: Event? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        simpleToolbarWithHome(toolbar, "Event")
        event = intent?.getParcelableExtra(EXTRA_EVENT)
        isNewEvent = event == null
        currentEventId = event?.event_id
        val sectionsPagerAdapter =
            EventDetailPagerAdapter(
                applicationContext,
                supportFragmentManager, event
            )
        val viewPager: ViewPager = findViewById(R.id.view_pager)

        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        Timber.d("${EventDetailFragment.TAG} MainVM  ${viewModel.hashCode()}")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}
