package id.shobrun.ukmmobile.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import id.shobrun.ukmmobile.R
import id.shobrun.ukmmobile.databinding.ItemParticipantEventBinding
import id.shobrun.ukmmobile.models.entity.Invitation
import id.shobrun.ukmmobile.models.entity.InvitationStatus
import kotlinx.android.synthetic.main.item_participant_event.view.*

class RecyclerParticipantEventAdapter(private var items: List<Invitation>) :
    RecyclerView.Adapter<RecyclerParticipantEventAdapter.ParticipantEventViewHolder>() {

    private lateinit var itemListener: (Invitation) -> Unit
    fun setItemListener(listener: (invitation: Invitation) -> Unit) {
        this.itemListener = listener
    }

    fun setItems(items: List<Invitation>?) {
        if (items != null) {
            this.items = items
            notifyDataSetChanged()
        }
    }

    class ParticipantEventViewHolder(private val binding: ItemParticipantEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val viewModel = ParticipantEventViewModel()

        fun bind(invitation: Invitation) {
            with(binding) {
                vm = viewModel
                viewModel.bind(invitation)
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantEventViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemParticipantEventBinding.inflate(layoutInflater, parent, false)
        val view = ParticipantEventViewHolder(itemBinding)

        view.listen {
            //itemListener(items[it])
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

    override fun onBindViewHolder(holder: ParticipantEventViewHolder, position: Int) {
        holder.bind(items[position])
        if (items[position].status.equals(InvitationStatus.ATTENDED.toString()))
            holder.itemView.icAttendance.setImageResource(R.drawable.ic_attend)
        else
            holder.itemView.icAttendance.setImageResource(R.drawable.ic_no_attend_yet)
    }

    class ParticipantEventViewModel : ViewModel() {
        private val _tvName = MutableLiveData<String>()
        val tvName: LiveData<String> = _tvName

        private val _tvEmail = MutableLiveData<String>()
        val tvEmail: LiveData<String> = _tvEmail
        fun bind(invitation: Invitation) {
            _tvName.value = invitation.participant_name
            _tvEmail.value = invitation.participant_email
        }
    }
}