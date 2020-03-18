package id.shobrun.ukmmobile.ui.myparticipants

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
import id.shobrun.ukmmobile.databinding.FragmentParticipantsBinding
import id.shobrun.ukmmobile.ui.adapter.RecyclerParticipantAdapter
import id.shobrun.ukmmobile.ui.myparticipants.detail.ParticipantDetailActivity
import kotlinx.android.synthetic.main.fragment_participants.*
import org.jetbrains.anko.support.v4.intentFor
import javax.inject.Inject

class MyParticipantsFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<MyParticipantsViewModel> { viewModelFactory }

    private lateinit var binding: FragmentParticipantsBinding

    private lateinit var participantsAdapter: RecyclerParticipantAdapter

    companion object {
        fun newInstance() = MyParticipantsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_participants, container, false)
        with(binding) {
            lifecycleOwner = this@MyParticipantsFragment
            vm = viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        participantsAdapter = RecyclerParticipantAdapter(ArrayList())
        participantsAdapter.setItemListener {
            /**
             * When Click Item
             */
            var detail =
                intentFor<ParticipantDetailActivity>(ParticipantDetailActivity.EXTRA_PARTICIPANT to it)
            startActivity(detail)

        }
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        binding.rvParticipants.addItemDecoration(dividerItemDecoration)
        binding.rvParticipants.adapter = participantsAdapter
        fabAdd.setOnClickListener {
            var add = intentFor<ParticipantDetailActivity>()
            startActivity(add)
        }
    }
}