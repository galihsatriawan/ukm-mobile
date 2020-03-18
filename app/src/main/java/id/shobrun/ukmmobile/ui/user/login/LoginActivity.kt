package id.shobrun.ukmmobile.ui.user.login

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import id.shobrun.ukmmobile.R
import id.shobrun.ukmmobile.databinding.ActivityLoginBinding
import id.shobrun.ukmmobile.ui.MainActivity
import id.shobrun.ukmmobile.ui.user.register.RegisterActivity
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.intentFor
import javax.inject.Inject

class LoginActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: LoginViewModel by viewModels { viewModelFactory }
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        with(binding) {
            lifecycleOwner = this@LoginActivity
            vm = viewModel
        }
        viewModel.snackbarText.observe(this, Observer {
            if (it != null)
                binding.root.snackbar(it)
        })
        viewModel.isSuccess.observe(this, Observer {
            it?.let {
                if (it) {
                    Toast.makeText(this,getString(R.string.seo_success_login), Toast.LENGTH_SHORT).show()
                    val mainContent = intentFor<MainActivity>()
                    startActivity(mainContent)
                    finish()
                }
            }
        })
        viewModel.registerAction.observe(this, Observer {
            it?.let {
                val register = intentFor<RegisterActivity>()
                startActivity(register)
                finish()
            }
        })
    }
}
