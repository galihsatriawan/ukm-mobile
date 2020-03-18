package id.shobrun.ukmmobile.utils

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.cardview.widget.CardView
import com.google.zxing.BarcodeFormat
import de.hdodenhof.circleimageview.CircleImageView
import id.shobrun.ukmmobile.R
import id.shobrun.ukmmobile.models.entity.Invitation
import id.shobrun.ukmmobile.models.entity.InvitationStatus
import id.shobrun.ukmmobile.utils.Helper.generatedCode


class DialogTools(private val activity: Activity) {
    private fun buildDialogView(@LayoutRes layout: Int): Dialog {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(layout)
        dialog.setCancelable(true)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window!!.attributes = lp
        return dialog
    }

    fun buildDialogInfo(
        @StringRes title: Int, @StringRes content: Int, @StringRes tipe: Int, @StringRes data: Int, @StringRes bt_text_pos: Int, @DrawableRes icon: Int,
        callback: CallbackDialog?,
        callbackShare: CallbackShare?
    ): Dialog {
        return buildDialogInfo(
            activity.getString(title),
            activity.getString(content),
            activity.getString(tipe),
            activity.getString(data),
            icon,
            callback,
            callbackShare
        )
    }

    // dialog info
    fun buildDialogInfo(
        title: String?,
        content: String?,
        tipe: String?,
        data: String?, @DrawableRes icon: Int,
        callback: CallbackDialog?,
        callbackShare: CallbackShare?
    ): Dialog {
        val dialog = buildDialogView(R.layout.dialog_info)
        (dialog.findViewById<View>(R.id.title) as TextView).text = title
        (dialog.findViewById<View>(R.id.content) as TextView).text = content
        (dialog.findViewById<View>(R.id.tv_type) as TextView).text = tipe
        (dialog.findViewById<View>(R.id.tv_data) as TextView).text = data
        (dialog.findViewById<View>(R.id.icon) as CircleImageView).setImageResource(
            icon
        )
        if (callbackShare != null) {
            (dialog.findViewById<View>(R.id.content) as TextView).visibility = View.GONE
            //            ((LinearLayout) dialog.findViewById(R.id.lyt_action)).setVisibility(View.GONE);
            (dialog.findViewById<View>(R.id.btn_negative) as TextView).text = "Close"
            (dialog.findViewById<View>(R.id.btn_positive) as TextView).visibility = View.GONE
            (dialog.findViewById<View>(R.id.cont_share) as CardView).visibility = View.VISIBLE
            (dialog.findViewById<View>(R.id.fb_image) as CircleImageView).setOnClickListener {
                callbackShare.onFacebook(dialog)
            }

            (dialog.findViewById<View>(R.id.twitter_image) as CircleImageView).setOnClickListener {
                callbackShare.onTwitter(dialog)
            }
            (dialog.findViewById<View>(R.id.whatsapp) as CircleImageView).setOnClickListener {
                callbackShare.onWhatsapp(dialog)
            }

            (dialog.findViewById<View>(R.id.btn_search_web) as Button).setVisibility(
                View.VISIBLE
            )
        }
        if (callback != null) {
            (dialog.findViewById<View>(R.id.cont_share) as CardView).visibility = View.VISIBLE
            (dialog.findViewById<View>(R.id.btn_positive) as Button).setOnClickListener {
                callback.onPositiveClick(dialog)
            }
            (dialog.findViewById<View>(R.id.btn_negative) as Button).setOnClickListener {
                callback.onNegativeClick(dialog)
            }
            (dialog.findViewById<View>(R.id.btn_search_web) as Button).setOnClickListener {
                callback.onSearchClick(dialog)
            }
        }
        return dialog
    }

    fun buildDialogCode(
        @StringRes title: Int, @StringRes content: Int, @StringRes data: Int, callbackDialog: CallbackDialog?
    ): Dialog? {
        return buildDialogCode(
            activity.getString(title),
            activity.getString(content),
            activity.getString(data),
            callbackDialog
        )
    }

    fun buildDialogCode(
        title: String?,
        content: String?,
        data: String?,
        callback: CallbackDialog?
    ): Dialog {
        val dialog = buildDialogView(R.layout.dialog_qr_code)
        (dialog.findViewById<View>(R.id.title) as TextView).text = title
        (dialog.findViewById<View>(R.id.content) as TextView).text = content
        (dialog.findViewById<View>(R.id.imgBar) as ImageView).setImageBitmap(
            generatedCode(data, BarcodeFormat.QR_CODE)
        )
        if (callback != null) {
            (dialog.findViewById<View>(R.id.btn_neutral) as Button).setOnClickListener {
                callback.onPositiveClick(
                    dialog
                )
            }
        }
        return dialog
    }

    fun buildDialogValidateTicket(
        myTicket: Invitation,
        callback: CallbackDialog?
    ): Dialog? {
        return buildDialogValidate(myTicket, callback)
    }

    // dialog info
    fun buildDialogValidate(
        myTicket: Invitation,
        callback: CallbackDialog?
    ): Dialog {
        val dialog = buildDialogView(R.layout.dialog_confirm_ticket)
        (dialog.findViewById<View>(R.id.tvIdTicket) as TextView).setText(
            myTicket.invitation_id ?: -1
        )
        (dialog.findViewById<View>(R.id.tvParticipantName) as TextView).setText(myTicket.participant_name)
        (dialog.findViewById<View>(R.id.tvParticipantEmail) as TextView).setText(myTicket.participant_email)
        val valid: Boolean =
            myTicket.status?.equals(InvitationStatus.WAITING_FOR_COMING.toString()) ?: false
        var contentStatus = ""

        if (!valid) {
            contentStatus = "Not Valid (${myTicket.status})"
            (dialog.findViewById<View>(R.id.tvStatusTicket) as TextView).text = contentStatus
            (dialog.findViewById<View>(R.id.btn_positive) as Button).visibility = View.GONE
            (dialog.findViewById<View>(R.id.btn_negative) as Button).text = "Close"
        } else {
            contentStatus = "Valid"
            (dialog.findViewById<View>(R.id.tvStatusTicket) as TextView).text = contentStatus
        }
        if (callback != null) {
            (dialog.findViewById<View>(R.id.btn_positive) as Button).setOnClickListener {
                callback.onPositiveClick(
                    dialog
                )
            }
            (dialog.findViewById<View>(R.id.btn_negative) as Button).setOnClickListener {
                callback.onNegativeClick(
                    dialog
                )
            }
        }
        return dialog
    }

    interface CallbackDialog {
        fun onPositiveClick(dialog: Dialog?)
        fun onNegativeClick(dialog: Dialog?)
        fun onSearchClick(dialog: Dialog?)
    }

    interface CallbackShare {
        fun onFacebook(dialog: Dialog?)
        fun onTwitter(dialog: Dialog?)
        fun onWhatsapp(dialog: Dialog?)
    }

}