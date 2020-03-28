package id.shobrun.ukmmobile.ui

import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar
import dagger.android.support.DaggerAppCompatActivity
import id.shobrun.ukmmobile.R
import id.shobrun.ukmmobile.ui.user.UserSignActivity
import id.shobrun.ukmmobile.utils.SharedPref
import id.shobrun.ukmmobile.utils.SharedPref.Companion.PREFS_IS_LOGIN
import org.jetbrains.anko.intentFor
import javax.inject.Inject

class SplashScreen : DaggerAppCompatActivity() {
    @Inject
    lateinit var sharedPref: SharedPref
    private lateinit var progressBar: ProgressBar
    private val loadingTime = 4000L // 3 Seconds
    private lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        progressBar = findViewById(R.id.progressBar)
        Thread(Runnable {
            doProgress()
            goToMain()
        }).start()

    }

    fun doProgress() {
        var progress = 0
        Thread.sleep(1000)
        val max = 100
        val cnt = 90

        while (progress <= max) {
            try {
                Thread.sleep(loadingTime / cnt)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            progress += (max / cnt)
        }
    }

    private fun goToMain() {
        if (sharedPref.getValue(PREFS_IS_LOGIN, false)) {
            val mainContent = intentFor<MainActivity>()
            startActivity(mainContent)
            finish()
        } else {
            val mainContent = intentFor<UserSignActivity>()
            startActivity(mainContent)
            finish()
        }

    }
}
