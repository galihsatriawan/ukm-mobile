package id.shobrun.ukmmobile.binding

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import id.shobrun.ukmmobile.R
import id.shobrun.ukmmobile.extensions.bindResource
import id.shobrun.ukmmobile.extensions.gone
import id.shobrun.ukmmobile.extensions.visible
import id.shobrun.ukmmobile.models.Resource
import id.shobrun.ukmmobile.models.Status
import id.shobrun.ukmmobile.models.entity.Event
import id.shobrun.ukmmobile.models.entity.Invitation
import id.shobrun.ukmmobile.models.entity.InvitationStatus
import id.shobrun.ukmmobile.models.entity.Participant
import id.shobrun.ukmmobile.models.network.EventsResponse
import id.shobrun.ukmmobile.models.network.InvitationsResponse
import id.shobrun.ukmmobile.models.network.ParticipantsResponse
import id.shobrun.ukmmobile.ui.adapter.*
import id.shobrun.ukmmobile.utils.Helper.generatedCode
import timber.log.Timber
import java.util.*

@BindingAdapter("resourceLoading")
fun bindResourceItem(view: ProgressBar, result: Resource<Any, Any>?) {
    view.bindResource(result) {
        if (it.status == Status.LOADING) view.visible() else view.gone()
    }
}

@BindingAdapter("liveResourceList")
fun <T> bindParticipantsList(view: RecyclerView, result: Resource<List<T>, Any>?) {
    view.bindResource(result) {
        when (view.adapter) {
            is RecyclerParticipantAdapter -> {
                val res = it.data as (List<Participant>)
                (view.adapter as RecyclerParticipantAdapter).setItems(res)
            }
            is RecyclerInvitationAdapter -> {
                val res = it.data as (List<Invitation>)
                (view.adapter as RecyclerInvitationAdapter).setItems(res)
            }
            is RecyclerParticipantEventAdapter -> {
                val res = it.data as (List<Invitation>)
                (view.adapter as RecyclerParticipantEventAdapter).setItems(res)
            }
            is RecyclerEventAdapter -> {
                val res = it.data as (List<Event>)
                (view.adapter as RecyclerEventAdapter).setItems(res)
            }
            is RecyclerParticipantSelectionAdapter -> {
                val res = it.data as (List<Invitation>)
                Timber.d("${res.size}")
                (view.adapter as RecyclerParticipantSelectionAdapter).set(res)
            }

        }
    }
}

@BindingAdapter("liveResourceAdditionalList")
fun <T> bindAdditionalList(view: RecyclerView, result: Resource<List<T>, Any>?) {
    view.bindResource(result) {
        when (view.adapter) {
            is RecyclerParticipantAdapter -> {
                val respons = it.additionalData as (ParticipantsResponse)
                val res = respons.result
                (view.adapter as RecyclerParticipantAdapter).setItems(res)
            }
            is RecyclerInvitationAdapter -> {
                val respons = it.additionalData as (InvitationsResponse)
                val res = respons.result
                (view.adapter as RecyclerInvitationAdapter).setItems(res)
            }
            is RecyclerParticipantEventAdapter -> {
                val respons = it.additionalData as (InvitationsResponse)
                val res = respons.result
                (view.adapter as RecyclerParticipantEventAdapter).setItems(res)
            }
            is RecyclerEventAdapter -> {
                val respons = it.additionalData as (EventsResponse)
                val res = respons.result
                (view.adapter as RecyclerEventAdapter).setItems(res)
            }
            is RecyclerParticipantSelectionAdapter -> {
                val respons = it.additionalData as (InvitationsResponse)
                val res = respons.result
                (view.adapter as RecyclerParticipantSelectionAdapter).set(res)
            }

        }
    }
}

/*
Binding Participant Detail
 */
@BindingAdapter("participantName")
fun bindParticipantName(view: TextInputEditText, resource: Resource<Participant, Any>?) {
    view.bindResource(resource) {
        view.setText(it.data?.participant_name)
    }
}

/**
 * Binding Invitation Detail
 */
@BindingAdapter("eventName")
fun bindEventName(view: TextView, resource: Resource<Event, Any>?) {
    view.bindResource(resource) {
        view.text = it.data?.event_name
    }
}

@BindingAdapter("eventDescription")
fun bindEventDesc(view: TextView, resource: Resource<Event, Any>?) {
    view.bindResource(resource) {
        view.text = it.data?.event_description
    }
}

@BindingAdapter("eventDate")
fun bindEventDate(view: TextView, resource: Resource<Event, Any>?) {
    view.bindResource(resource) {
        view.text = it.data?.event_date
    }
}

@BindingAdapter("eventLocation")
fun bindEventLocation(view: TextView, resource: Resource<Event, Any>?) {
    view.bindResource(resource) {
        view.text = it.data?.event_location
    }
}


@BindingAdapter("eventInviter")
fun bindEventInviter(view: TextView, resource: Resource<Event, Any>?) {
    view.bindResource(resource) {
        view.text = it.data?.user_username
    }
}


@BindingAdapter("eventCp")
fun bindEventCp(view: TextView, resource: Resource<Event, Any>?) {
    view.bindResource(resource) {
        view.text = it.data?.event_cp
    }
}

@BindingAdapter("eventParticipant")
fun bindEventParticipant(view: TextView, resource: Resource<Event, Any>?) {
    view.bindResource(resource) {
        var res = it.data?.participant_total ?: 0
        if (res < 0) res = 0
        view.text = "$res"
    }
}

@BindingAdapter("eventParticipantAttend")
fun bindEventParticipantAttend(view: TextView, resource: Resource<Event, Any>?) {
    view.bindResource(resource) {
        var res = it.data?.participant_attend ?: 0
        if (res < 0) res = 0
        view.text = "$res"
    }
}

@BindingAdapter("eventParticipantNotAttend")
fun bindEventParticipantNotAttend(view: TextView, resource: Resource<Event, Any>?) {
    view.bindResource(resource) {

        val tot = it.data?.participant_total ?: 0
        val notAtt = it.data?.participant_attend ?: 0
        Timber.d("-- Not Attend ${tot} - ${notAtt}")
        var res = tot - notAtt
        if (res < 0) res = 0
        view.text = "$res"
    }
}

@BindingAdapter("invitationQr")
fun bindEventLocation(view: ImageView, resource: Resource<Invitation, Any>?) {
    view.bindResource(resource) {
        it.data?.let {
            val gson = Gson()
            val data = gson.toJson(it)
            view.setImageBitmap(generatedCode(data, BarcodeFormat.QR_CODE))
        }

    }
}

/**
 * Confirm Ticket
 */
@BindingAdapter("idTicket")
fun bindIdTicket(view: TextView, resource: Resource<Invitation, Any>?) {
    view.bindResource(resource) {
        it.data?.let {
            view.text = it.event_id
        }
    }
}

@BindingAdapter("participantEmail")
fun bindParticipantEmail(view: TextView, resource: Resource<Invitation, Any>?) {
    view.bindResource(resource) {
        it.data?.let {
            view.text = it.participant_email
        }
    }
}

@BindingAdapter("invitationStatus")
fun bindInvitationStatus(view: TextView, resource: Resource<Invitation, Any>?) {
    view.bindResource(resource) {
        it.data?.let {
            val valid: Boolean =
                it.status?.equals(InvitationStatus.WAITING_FOR_COMING.toString()) ?: false
            if (valid) view.text = "Valid"
            else view.text = "Not Valid (${it.status})"

        }
    }
}

@BindingAdapter("tvParticipantName")
fun bindParticipantName(view: TextView, resource: Resource<Invitation, Any>?) {
    view.bindResource(resource) {
        it.data?.let {
            view.text = it.participant_name
        }
    }
}

@BindingAdapter("bindValidateButton")
fun bindValidateButton(view: MaterialButton, resource: Resource<Invitation, Any>?) {
    view.bindResource(resource) {
        it.data?.let {
            val valid: Boolean =
                it.status?.equals(InvitationStatus.WAITING_FOR_COMING.toString()) ?: false
            if (!valid) view.gone()
        }

    }
}

@BindingAdapter("selectDate")
fun bindDateClicks(v: TextInputEditText, date: MutableLiveData<String>) {
    v.setOnClickListener {
        selectDate(it.context, date)
    }
}

@BindingAdapter("selectDateLayout")
fun bindDateLayoutClicks(v: TextInputLayout, date: MutableLiveData<String>) {
    v.setOnClickListener {
        selectDate(it.context, date)
    }
}

fun selectDate(context: Context, date: MutableLiveData<String>) {
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)


    val datePickerDialog = DatePickerDialog(
        context,
        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            date.value = year.toString() + "-" + (month + 1) + "-" + dayOfMonth.toString()
            selectTime(context, date)
        },
        year,
        month,
        day
    )
    datePickerDialog.show()
}

fun selectTime(context: Context, date: MutableLiveData<String>) {
    val c = Calendar.getInstance()
    val hour = c.get(Calendar.HOUR_OF_DAY)
    val minute = c.get(Calendar.MINUTE)
    val timePickerDialog =
        TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            var sMinute = "$minute"
            if (minute < 10) {
                sMinute = "0$minute"
            }

            date.value = date.value + " $hourOfDay:$sMinute"
        }, hour, minute, true)
    timePickerDialog.show()
}


@BindingAdapter("visibleMessage")
fun bindVisibleMessage(view: LinearLayout, resource: Resource<List<Any>, Any>?) {
    view.bindResource(resource) {
        if (it.data.isNullOrEmpty()) {
            view.visible()
        } else {
            view.gone()
        }
    }
}

@BindingAdapter("messageEvents")
fun bindMessage(view: TextView, resource: Resource<List<Any>, Any>?) {
    view.bindResource(resource) {
        if (it.status == Status.ERROR) {
            view.text = it.message
            view.text = view.context.getString(R.string.failed_load)
        } else {
            if (it.data.isNullOrEmpty()) {
                view.text = view.context.getString(R.string.empty_data)
            }
        }
    }
}

@BindingAdapter("visibleMessageItem")
fun bindVisibleMessageItem(view: LinearLayout, resource: Resource<Any, Any>?) {
    view.bindResource(resource) {
        if (it.data == null) {
            view.visible()
        } else {
            view.gone()
        }
    }
}

@BindingAdapter("messageItem")
fun bindMessageItem(view: TextView, resource: Resource<Any, Any>?) {
    view.bindResource(resource) {
        if (it.status == Status.ERROR) {
            view.text = it.message
            view.text = view.context.getString(R.string.failed_load)
        } else {
            if (it.data == null) {
                view.text = view.context.getString(R.string.empty_data)
            }
        }
    }
}


@BindingAdapter("visibleMessageTransporter")
fun bindVisibleMessageTransporter(view: LinearLayout, resource: Resource<List<Any>, Any>?) {
    view.bindResource(resource) {
        if (it.additionalData == null) view.visible()
        else {
            val data = it.additionalData as InvitationsResponse
            if (data.result.isNullOrEmpty()) {
                view.visible()
            } else {
                view.gone()
            }
        }
    }
}

@BindingAdapter("messageEventsTransporter")
fun bindMessageTransporter(view: TextView, resource: Resource<List<Any>, Any>?) {
    view.bindResource(resource) {
        if (it.status == Status.ERROR) {
            view.text = it.message
            view.text = view.context.getString(R.string.failed_load)
        } else {
            if (it.additionalData == null) view.text = view.context.getString(R.string.empty_data)
            else {
                val data = it.additionalData as InvitationsResponse
                if (data.result.isNullOrEmpty()) {
                    view.text = view.context.getString(R.string.empty_data)
                }
            }

        }
    }
}