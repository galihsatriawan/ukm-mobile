package id.shobrun.ukmmobile.ui.myevents.scanner


import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import dagger.android.support.DaggerAppCompatActivity
import id.shobrun.ukmmobile.R
import id.shobrun.ukmmobile.databinding.DialogConfirmTicketBinding
import id.shobrun.ukmmobile.models.entity.Event
import id.shobrun.ukmmobile.models.entity.Invitation
import id.shobrun.ukmmobile.ui.myevents.detail.EventDetailActivity.Companion.EXTRA_EVENT
import id.shobrun.ukmmobile.utils.SharedPref.Companion.PREFS_USER_ID
import me.dm7.barcodescanner.zxing.ZXingScannerView
import timber.log.Timber
import javax.inject.Inject


class ScannerActivity : DaggerAppCompatActivity(), ZXingScannerView.ResultHandler {
    private val TAG = javaClass.simpleName
    private var mScannerView: ZXingScannerView? = null
    private val REQUEST_CAMERA = 101
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    val viewModel: ScannerViewModel by viewModels { viewModelFactory }
    var event: Event? = null
    lateinit var binding: DialogConfirmTicketBinding
    public override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        checkCameraPermission()
        event = intent?.getParcelableExtra(EXTRA_EVENT)
        mScannerView = ZXingScannerView(this) // Programmatically initialize the scanner view
        mScannerView!!.setFormats(mutableListOf(BarcodeFormat.QR_CODE))
        setContentView(mScannerView) // Set the scanner view as the content view
        viewModel.snackbarText.observe(this, Observer {
            if (it != null) {
                Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
            }

        })

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CAMERA -> {
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) { // permission was granted, yay! Do the
// contacts-related task you need to do.
                } else { // permission denied, boo! Disable the
// functionality that depends on this permission.
                }
                return
            }
        }
    }


    private fun checkCameraPermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            )
            != PackageManager.PERMISSION_GRANTED
        ) { // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.CAMERA
                )
            ) { // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder(this)
                    .setTitle(R.string.title_camera_permission)
                    .setMessage(R.string.text_camera_permission)
                    .setPositiveButton(
                        R.string.OK
                    ) { dialogInterface, i ->
                        //Prompt the user once explanation has been shown
                        ActivityCompat.requestPermissions(
                            this@ScannerActivity,
                            arrayOf(Manifest.permission.CAMERA),
                            REQUEST_CAMERA
                        )
                    }
                    .create()
                    .show()
            } else { // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.CAMERA),
                    REQUEST_CAMERA
                )
            }
            false
        } else {
            true
        }
    }

    public override fun onResume() {
        super.onResume()
        mScannerView!!.setResultHandler(this) // Register ourselves as a handler for scan results.
        mScannerView!!.startCamera() // Start camera on resume
    }

    public override fun onPause() {
        super.onPause()
        mScannerView!!.stopCamera() // Stop camera on pause
    }

    override fun handleResult(rawResult: Result) { // Do something with the result here
        val data: String = rawResult.text
        Timber.d(data)
        var invitation: Invitation? = null
        var valid = true
        try {
            invitation = Gson().fromJson(data, Invitation::class.java)
        } catch (e: Exception) {
            valid = false
            Toast.makeText(this, getString(R.string.seo_info_check_camera), Toast.LENGTH_SHORT)
                .show()
        }
        val userId = viewModel.sharedPref.getValue(PREFS_USER_ID, -1)
        if (invitation?.inviter_id != userId) {
            valid = false
            Toast.makeText(
                this,
                getString(R.string.seo_info_not_grant_validate),
                Toast.LENGTH_SHORT
            ).show()
        }
        if (invitation?.event_id != event?.event_id ?: -1) {
            valid = false
            Toast.makeText(this, getString(R.string.seo_info_not_the_event), Toast.LENGTH_SHORT)
                .show()
        }
        if (valid) {
            val resDialog = Dialog(this as Activity)
            resDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            binding = DataBindingUtil.inflate(
                LayoutInflater.from(this as Activity),
                R.layout.dialog_confirm_ticket,
                null,
                false
            )
//            resDialog.setCancelable(true)
            resDialog.setContentView(binding.root)
            val closeButton: MaterialButton = resDialog.findViewById(R.id.btn_negative)
            closeButton.setOnClickListener {
                mScannerView!!.resumeCameraPreview(this)
                resDialog.dismiss()
                // If you would like to resume scanning, call this method below:

            }
            with(binding) {
                vm = viewModel
                lifecycleOwner = this@ScannerActivity
            }
            resDialog.show()

            viewModel.postInvitation(invitation)
        }else{
            mScannerView!!.resumeCameraPreview(this)
        }


    }
}