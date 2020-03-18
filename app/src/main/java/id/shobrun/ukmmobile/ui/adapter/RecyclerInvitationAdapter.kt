package id.shobrun.ukmmobile.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import id.shobrun.ukmmobile.databinding.ItemInvitationBinding
import id.shobrun.ukmmobile.models.entity.Invitation

class RecyclerInvitationAdapter(private var items: List<Invitation>) :
    RecyclerView.Adapter<RecyclerInvitationAdapter.InvitationViewHolder>() {
    class InvitationViewHolder(private val binding: ItemInvitationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val viewModel = InvitationViewModel()
        fun bind(invitation: Invitation) {
            with(binding) {
                vm = viewModel
                viewModel.bind(invitation)
                executePendingBindings()
            }
        }

    }

    private lateinit var itemListener: (Invitation) -> Unit
    fun setItemListener(listener: (Invitation) -> Unit) {
        this.itemListener = listener
    }

    fun setItems(items: List<Invitation>?) {
        if (items != null) {
            this.items = items
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvitationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemInvitationBinding.inflate(layoutInflater, parent, false)
        val view = InvitationViewHolder(itemBinding)

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


    override fun onBindViewHolder(holder: InvitationViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class InvitationViewModel : ViewModel() {
        private val _tvEventName = MutableLiveData<String>()
        val tvEventName: LiveData<String> = _tvEventName

        private val _tvEventDate = MutableLiveData<String>()
        val tvEventDate: LiveData<String> = _tvEventDate

        private val _tvEventFrom = MutableLiveData<String>()
        val tvEventFrom: LiveData<String> = _tvEventFrom

        fun bind(invitation: Invitation) {
            _tvEventName.value = invitation.event_name
            _tvEventFrom.value = "Invited by ${invitation.inviter}"
            _tvEventDate.value = invitation.event_date
        }
    }

}