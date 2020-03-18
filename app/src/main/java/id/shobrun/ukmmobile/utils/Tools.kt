package id.shobrun.ukmmobile.utils

import android.app.Application
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import timber.log.Timber
import java.io.UnsupportedEncodingException
import java.net.URLEncoder


class Tools(private val application: Application) {
    private val TAG = javaClass.simpleName
    fun isConnect() : Boolean {
        val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val isConnected : Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnect()
    }
    fun openMap(context: Context, latitude: Double, longitude: Double) {
        val uri = "geo:$latitude,$longitude"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        context.startActivity(intent)
    }


    fun callPhone(context: Context, telp: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$telp")
        context.startActivity(intent)
    }


    fun shareWA(context: Context, message: String?) {
        val whatsappIntent = Intent(Intent.ACTION_SEND)
        whatsappIntent.type = "text/plain"
        whatsappIntent.setPackage("com.whatsapp")
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, message)
        try {
            context.startActivity(whatsappIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(context, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Share on Facebook. Using Facebook app if installed or web link otherwise.
     *
     * @param text     not used/allowed on Facebook
     * @param url      url to share
     */
    fun shareFacebook(context: Context, text: String?, url: String) {
        var facebookAppFound = false
        var shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, url)
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(url))
        val pm: PackageManager = context.getPackageManager()
        val activityList =
            pm.queryIntentActivities(shareIntent, 0)
        for (app in activityList) {
            if (app.activityInfo.packageName.contains("com.facebook.katana")) {
                val activityInfo = app.activityInfo
                val name =
                    ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name)
                shareIntent.addCategory(Intent.CATEGORY_LAUNCHER)
                shareIntent.component = name
                facebookAppFound = true
                break
            }
        }
        if (!facebookAppFound) {
            val sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=$url"
            shareIntent = Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl))
        }
        context.startActivity(shareIntent)
    }

    fun shareTwitter(context: Context, message: String) {
        val tweetIntent = Intent(Intent.ACTION_SEND)
        tweetIntent.putExtra(Intent.EXTRA_TEXT, "This is a Test.")
        tweetIntent.type = "text/plain"
        val packManager: PackageManager = context.getPackageManager()
        val resolvedInfoList =
            packManager.queryIntentActivities(tweetIntent, PackageManager.MATCH_DEFAULT_ONLY)
        var resolved = false
        for (resolveInfo in resolvedInfoList) {
            if (resolveInfo.activityInfo.packageName.startsWith("com.twitter.android")) {
                tweetIntent.setClassName(
                    resolveInfo.activityInfo.packageName,
                    resolveInfo.activityInfo.name
                )
                resolved = true
                break
            }
        }
        if (resolved) {
            context.startActivity(tweetIntent)
        } else {
            val i = Intent()
            i.putExtra(Intent.EXTRA_TEXT, message)
            i.action = Intent.ACTION_VIEW
            i.data = Uri.parse("https://twitter.com/intent/tweet?text=" + urlEncode(message))
            context.startActivity(i)
            Toast.makeText(context, "Twitter app isn't found", Toast.LENGTH_LONG).show()
        }
    }

    private fun urlEncode(s: String): String {
        return try {
            URLEncoder.encode(s, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            Timber.d(TAG, "UTF-8 should always be supported", e)
            ""
        }
    }

    inner class Pair<T, S> {
        var first: T? = null
        var second: S? = null

        internal constructor(first: T, second: S) {
            this.first = first
            this.second = second
        }

        internal constructor()
    }

    fun matcherQRData(data: String): Pair<String, String> {
        var type = isValidEmail(data)
        if (type.isEmpty()) {
            type = isValidWebUrl(data)
            if (type.isEmpty()) {
                type = isValidPhone(data)
                if (type.isEmpty()) {
                    type = isValidLoc(data)
                    if (type.isEmpty()) {
                        type = "$KODE/$PRODUK"
                    }
                }
            }
        }
        return Pair(type, data)
    }

    private fun isValidEmail(target: CharSequence): String {
        return if (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()) EMAIL else ""
    }

    private fun isValidWebUrl(target: CharSequence): String {
        return if (!TextUtils.isEmpty(target) && Patterns.WEB_URL.matcher(target).matches()) WEB else ""
    }

    private fun isValidPhone(target: CharSequence): String {
        return if (!TextUtils.isEmpty(target) && Patterns.PHONE.matcher(target).matches() && target.toString().contains(
                "+"
            )
        ) TELP else ""
    }

    private fun isValidLoc(target: CharSequence): String {
        return if (!TextUtils.isEmpty(target) && target.toString().contains("geo")) LOC else ""
    }

    companion object {
        const val EMAIL = "EMAIL"
        const val WEB = "WEB"
        const val TELP = "TELP"
        const val LOC = "LOC"
        const val KODE = "KODE"
        const val PRODUK = "PRODUK"
    }
}
