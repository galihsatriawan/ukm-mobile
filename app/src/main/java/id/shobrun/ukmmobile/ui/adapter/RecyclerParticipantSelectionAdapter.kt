package id.shobrun.ukmmobile.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import id.shobrun.ukmmobile.databinding.ItemParticipantSelectionBinding
import id.shobrun.ukmmobile.models.entity.Invitation
import timber.log.Timber

class RecyclerParticipantSelectionAdapter(var items: List<Invitation>) :
    RecyclerView.Adapter<RecyclerParticipantSelectionAdapter.ParticipantsViewHolder>() {
    private lateinit var itemListener: (Invitation) -> Unit
    fun setItemListener(listener: (invitation: Invitation) -> Unit) {
        this.itemListener = listener
    }

    fun set(items: List<Invitation>) {
        if (items != null) {
            this.items = items
            notifyDataSetChanged()
        }
    }

    class ParticipantsViewHolder(private val binding: ItemParticipantSelectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val viewModel = ParticipantSelectionViewModel()
        fun bind(invitation: Invitation) {
            with(binding) {
                vm = viewModel
                viewModel.bind(invitation)
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemParticipantSelectionBinding.inflate(layoutInflater, parent, false)
        val view = ParticipantsViewHolder(itemBinding)

        view.listen {
            itemBinding.checkboxInvited.isChecked = !itemBinding.checkboxInvited.isChecked
            items[it].is_invited = itemBinding.checkboxInvited.isChecked
        }

        return view
    }

    private fun <T : RecyclerView.ViewHolder> T.listen(invitation: (position: Int) -> Unit): T {
        itemView.setOnClickListener {
            invitation.invoke(adapterPosition)
        }
        return this
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ParticipantsViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    class ParticipantSelectionViewModel : ViewModel() {
        private val _tvName = MutableLiveData<String>()
        val tvName: LiveData<String> = _tvName

        private val _tvEmail = MutableLiveData<String>()
        val tvEmail: LiveData<String> = _tvEmail

        val _checkBox = MutableLiveData<Boolean>(false)
        val checkBox: LiveData<Boolean> = _checkBox
        fun bind(invitation: Invitation) {
            _tvName.value = invitation.participant_name
            _tvEmail.value = invitation.participant_email
            _checkBox.value = invitation.is_invited
            Timber.d("${tvName.value}-${checkBox.value}")
        }
    }
}