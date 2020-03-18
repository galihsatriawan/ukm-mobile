package id.shobrun.ukmmobile.ui.profile

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import id.shobrun.ukmmobile.R
import id.shobrun.ukmmobile.utils.Constants.Companion.HELP_URL

class HelpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
        var web: WebView = findViewById(R.id.webView)
        web.loadUrl(HELP_URL)
    }
}
