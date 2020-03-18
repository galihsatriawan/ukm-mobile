package id.shobrun.ukmmobile.ui.myparticipants.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import id.shobrun.ukmmobile.R
import id.shobrun.ukmmobile.databinding.ActivityParticipantDetailBinding
import id.shobrun.ukmmobile.extensions.simpleToolbarWithHome
import id.shobrun.ukmmobile.models.entity.Participant
import kotlinx.android.synthetic.main.activity_participant_detail.*
import org.jetbrains.anko.design.snackbar
import javax.inject.Inject

class ParticipantDetailActivity : DaggerAppCompatActivity() {
    companion object {
        const val EXTRA_PARTICIPANT = "extra_participant"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    var participant: Participant? = null
    val viewModel by viewModels<ParticipantDetailViewModel> { viewModelFactory }

    lateinit var binding: ActivityParticipantDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_participant_detail)
        with(binding) {
            lifecycleOwner = this@ParticipantDetailActivity
            vm = viewModel
        }
        simpleToolbarWithHome(toolbar, "Participant")
        if (intent.getParcelableExtra<Participant>(EXTRA_PARTICIPANT) != null) {
            participant = intent.getParcelableExtra(EXTRA_PARTICIPANT)
            viewModel.postParticipantId(participant?.participant_id)
        } else {
            viewModel.postParticipantId(null)
        }
        viewModel.snackbarText.observe(this, Observer {
            if (it != null) {
                binding.progressBar.snackbar(it).show()
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()

    }


}
