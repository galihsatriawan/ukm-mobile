package id.shobrun.ukmmobile.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import id.shobrun.ukmmobile.databinding.ItemParticipantBinding
import id.shobrun.ukmmobile.models.entity.Participant


class RecyclerParticipantAdapter(private var items: List<Participant>) :
    RecyclerView.Adapter<RecyclerParticipantAdapter.ParticipantViewHolder>() {

    private lateinit var itemListener: (Participant) -> Unit
    fun setItemListener(listener: (participant: Participant) -> Unit) {
        this.itemListener = listener
    }

    fun setItems(items: List<Participant>?) {
        if (items != null) {
            this.items = items
            notifyDataSetChanged()
        }
    }

    class ParticipantViewHolder(private val binding: ItemParticipantBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val viewModel = ParticipantViewModel()

        fun bind(participant: Participant) {
            with(binding) {
                vm = viewModel
                viewModel.bind(participant)
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemParticipantBinding.inflate(layoutInflater, parent, false)
        val view = ParticipantViewHolder(itemBinding)

        view.listen {
            itemListener(items[it])
        }

        return view
    }

    private fun <T : RecyclerView.ViewHolder> T.listen(participant: (position: Int) -> Unit): T {
        itemView.setOnClickListener {
            participant.invoke(adapterPosition)
        }
        return this
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ParticipantViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ParticipantViewModel : ViewModel() {
        private val _tvName = MutableLiveData<String>()
        val tvName: LiveData<String> = _tvName

        private val _tvEmail = MutableLiveData<String>()
        val tvEmail: LiveData<String> = _tvEmail
        fun bind(participant: Participant) {
            _tvName.value = participant.participant_name
            _tvEmail.value = participant.participant_email
        }
    }
}