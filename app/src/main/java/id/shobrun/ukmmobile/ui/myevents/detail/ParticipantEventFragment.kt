package id.shobrun.ukmmobile.ui.myevents.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import id.shobrun.ukmmobile.R
import id.shobrun.ukmmobile.databinding.FragmentParticipantEventBinding
import id.shobrun.ukmmobile.models.entity.Event
import id.shobrun.ukmmobile.ui.adapter.RecyclerParticipantEventAdapter
import id.shobrun.ukmmobile.ui.myevents.detail.EventDetailActivity.Companion.EXTRA_EVENT
import kotlinx.android.synthetic.main.fragment_participants.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.intentFor
import javax.inject.Inject

class ParticipantEventFragment : DaggerFragment() {

    companion object {
        fun newInstance() = ParticipantEventFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ParticipantEventViewModel by viewModels { viewModelFactory }
    private val viewModelMain : EventDetailMainViewModel by viewModels{viewModelFactory}
    private lateinit var binding: FragmentParticipantEventBinding
    private lateinit var participantsAdapter: RecyclerParticipantEventAdapter
    private var event: Event? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_participant_event, container, false)
        event= arguments?.getParcelable(EXTRA_EVENT)
        with(binding) {
            lifecycleOwner = this@ParticipantEventFragment
            vm = viewModel
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.postEventId(event?.event_id ?: EventDetailActivity.currentEventId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        participantsAdapter = RecyclerParticipantEventAdapter(ArrayList())
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        binding.rvParticipants.addItemDecoration(dividerItemDecoration)
        binding.rvParticipants.adapter = participantsAdapter
        fabAdd.setOnClickListener {
            if (EventDetailActivity.isNewEvent) {
                binding.root.snackbar("Please create the event first")
                return@setOnClickListener
            }
            var add = intentFor<ParticipantSelectionActivity>(
                EXTRA_EVENT to event
            )
            startActivity(add)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.postEventId(event?.event_id?:EventDetailActivity.currentEventId)

    }

}
