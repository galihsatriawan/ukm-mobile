package id.shobrun.ukmmobile.ui.myevents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.android.support.DaggerFragment
import id.shobrun.ukmmobile.R
import id.shobrun.ukmmobile.databinding.FragmentEventsBinding
import id.shobrun.ukmmobile.ui.adapter.RecyclerEventAdapter
import id.shobrun.ukmmobile.ui.myevents.detail.EventDetailActivity
import id.shobrun.ukmmobile.ui.myevents.detail.EventDetailActivity.Companion.EXTRA_EVENT
import kotlinx.android.synthetic.main.fragment_events.*
import org.jetbrains.anko.support.v4.intentFor
import javax.inject.Inject

class MyEventsFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MyEventsViewModel by viewModels { viewModelFactory }

    private lateinit var eventAdapter: RecyclerEventAdapter

    companion object {
        fun newInstance() = MyEventsFragment()
    }

    lateinit var binding: FragmentEventsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_events, container, false)
        with(binding) {
            lifecycleOwner = this@MyEventsFragment
            vm = viewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventAdapter = RecyclerEventAdapter(ArrayList())
        eventAdapter.setItemListener {
            val detail = intentFor<EventDetailActivity>(EXTRA_EVENT to it)
            startActivity(detail)
        }


        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.rvEvent.addItemDecoration(dividerItemDecoration)
        binding.rvEvent.adapter = eventAdapter

        fabAdd.setOnClickListener {
            val newEvent = intentFor<EventDetailActivity>(
            )
            startActivity(newEvent)
        }

    }
}