package id.shobrun.ukmmobile.ui.user.register


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
import id.shobrun.ukmmobile.databinding.ActivityRegisterBinding
import id.shobrun.ukmmobile.ui.user.UserSignActivity
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.support.v4.intentFor
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : DaggerFragment() {

    companion object{
        fun newInstance() = RegisterFragment()
    }
    @Inject
    lateinit var viewModelFactory:ViewModelProvider.Factory

    private val viewModel by viewModels<RegisterViewModel> { viewModelFactory }

    lateinit var binding : ActivityRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.activity_register,container,false)
        with(binding){
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.snackbarText.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.root.snackbar(it)

            }
        })
        viewModel.isSuccess.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    Toast.makeText(context,getString(R.string.seo_register_success), Toast.LENGTH_SHORT).show()
                    val login = intentFor<UserSignActivity>()
                    startActivity(login)

                }
            }
        })
        viewModel.loginAction.observe(viewLifecycleOwner, Observer {
            it?.let {
            }
        })
    }


}
