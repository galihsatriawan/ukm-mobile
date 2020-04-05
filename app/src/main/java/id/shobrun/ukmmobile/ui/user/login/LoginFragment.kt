package id.shobrun.ukmmobile.ui.user.login


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import id.shobrun.ukmmobile.R
import id.shobrun.ukmmobile.databinding.ActivityLoginBinding
import id.shobrun.ukmmobile.ui.MainActivity
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.intentFor
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : DaggerFragment() {
    companion object{
        fun newInstance() = LoginFragment()
    }
    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory

    private val viewModel by viewModels<LoginViewModel>{viewModelFactory}

    lateinit var binding : ActivityLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.activity_login,container,false)

        with(binding){
            lifecycleOwner = this@LoginFragment
            vm = viewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.snackbarText.observe(viewLifecycleOwner, Observer {
            if (it != null)
                binding.root.snackbar(it)
        })
        viewModel.isSuccess.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    Toast.makeText(context,getString(R.string.ukm_success_login), Toast.LENGTH_SHORT).show()
                    val mainContent = intentFor<MainActivity>()
                    startActivity(mainContent)
                }
            }
        })
        viewModel.registerAction.observe(viewLifecycleOwner, Observer {
            it?.let {

            }
        })
    }


}
