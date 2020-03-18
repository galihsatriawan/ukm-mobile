package id.shobrun.ukmmobile.ui.invitations.detail

import android.app.Dialog
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import dagger.android.support.DaggerAppCompatActivity
import id.shobrun.ukmmobile.R
import id.shobrun.ukmmobile.databinding.ActivityInvitationDetailBinding
import id.shobrun.ukmmobile.extensions.simpleToolbarWithHome
import id.shobrun.ukmmobile.models.entity.Invitation
import id.shobrun.ukmmobile.utils.DialogTools
import kotlinx.android.synthetic.main.activity_participant_detail.*
import javax.inject.Inject


class InvitationDetailActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<InvitationDetailViewModel> { viewModelFactory }

    companion object {
        const val EXTRA_INVITATION = "extra_invitation"
    }

    var invitation: Invitation? = null
    lateinit var binding: ActivityInvitationDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_invitation_detail)
        simpleToolbarWithHome(toolbar, "Invitation Detail")
        with(binding) {
            lifecycleOwner = this@InvitationDetailActivity
            vm = viewModel
        }
        invitation = intent?.getParcelableExtra(EXTRA_INVITATION)
        viewModel.postEventId(invitation?.event_id)
        viewModel.postInvitationId(invitation?.invitation_id)
        viewModel.actionQrCode.observe(this, Observer {
            it?.let {
                val buildDialog = DialogTools(this)
                val callbackDialog = object : DialogTools.CallbackDialog {
                    override fun onPositiveClick(dialog: Dialog?) {
                        dialog?.dismiss()
                    }

                    override fun onNegativeClick(dialog: Dialog?) {
                        dialog?.dismiss()
                    }

                    override fun onSearchClick(dialog: Dialog?) {
                        dialog?.dismiss()
                    }

                }
                val data = Gson().toJson(viewModel.invitation.value?.data)
                val title = "Tickets QR Code"
                val content = "Please show to the ticket keeper"
                val dialog: Dialog =
                    buildDialog.buildDialogCode(title, content, data, callbackDialog)
                dialog.show()
            }

        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()

    }
}
