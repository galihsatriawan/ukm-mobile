package id.shobrun.ukmmobile.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import id.shobrun.ukmmobile.databinding.ItemEventBinding
import id.shobrun.ukmmobile.models.entity.Event

class RecyclerEventAdapter(private var items: List<Event>) :
    RecyclerView.Adapter<RecyclerEventAdapter.EventViewHolder>() {
    fun setItems(items: List<Event>?) {
        if (items != null) {
            this.items = items
            notifyDataSetChanged()
        }
    }

    private lateinit var itemListener: (Event) -> Unit
    fun setItemListener(listener: (Event) -> Unit) {
        this.itemListener = listener
    }

    class EventViewHolder(val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = EventViewModel()
        fun bind(event: Event) {
            with(binding) {
                vm = viewModel
                viewModel.bind(event)
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemEventBinding.inflate(layoutInflater, parent, false)
        val view = EventViewHolder(itemBinding)
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

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class EventViewModel : ViewModel() {
        private val _tvEventName = MutableLiveData<String>()
        val tvEventName: LiveData<String> = _tvEventName

        private val _tvEventDate = MutableLiveData<String>()
        val tvEventDate: LiveData<String> = _tvEventDate

        private val _tvEventStatus = MutableLiveData<String>()
        val tvEventStatus: LiveData<String> = _tvEventStatus

        fun bind(event: Event) {
            _tvEventName.value = event.event_name
            _tvEventDate.value = event.event_date
            _tvEventStatus.value = event.event_status
        }
    }
}