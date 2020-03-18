package id.shobrun.ukmmobile.utils

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.text.TextUtils
import android.util.Patterns
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.security.MessageDigest
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*


object Helper {
    const val EMAIL = "EMAIL"
    const val WEB = "WEB"
    const val TELP = "TELP"
    const val LOC = "LOC"


    fun getTimeStamp() = Timestamp(System.currentTimeMillis()).time
    fun getCurrentDatetime() :String{
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val current = Calendar.getInstance().time
        return formatter.format(current)
    }
    /**
     * Hashing
     */
    fun md5(input: String) = hashString("MD5", input)

    fun sha256(input: String) = hashString("SHA-256", input)
    private fun hashString(type: String, input: String): String {
        val bytes = MessageDigest.getInstance(type)
            .digest(input.toByteArray())
        return printHexBinary(bytes)
    }

    private fun printHexBinary(data: ByteArray): String {
        val hexString = StringBuilder()

        data.forEach { b ->
            var h = Integer.toHexString(0xFF and b.toInt())
            while (h.length < 2) h = "0$h"
            hexString.append(h)
        }
        return hexString.toString()
    }

    private fun simpleHash(input: String): String {
        val alphabet = "ABCDEF0123456789".toCharArray()
        val alphabetLength = alphabet.size
        var hash = ""
        var index = 0
        do {
            val temp = input[index++].toInt() % alphabetLength
            hash = "${alphabet[temp]}$hash"
        } while (index < input.length)
        return hash
    }

    fun getUniqueID(userId: String): String {
        val id = "$userId${getTimeStamp()}"
        return simpleHash(simpleHash(id))
    }

    fun generatedCode(text: String?, type: BarcodeFormat?): Bitmap? {
        var bitmap: Bitmap? = null
        val multiFormatWriter = MultiFormatWriter()
        try {
            val bitMatrix: BitMatrix = multiFormatWriter.encode(text, type, 200, 200)
            val barcodeEncoder = BarcodeEncoder()
            bitmap = barcodeEncoder.createBitmap(bitMatrix)
            //            imageView.setImageBitmap(bitmap);
        } catch (e: WriterException) {
            e.printStackTrace()
        }
        return bitmap
    }

    fun directionMaps(latitude: Double, longitude: Double): Intent? {
        return Intent(
            Intent.ACTION_VIEW,
            Uri.parse("google.navigation:q=$latitude,$longitude")
        )
    }

    fun openBrowser(url: String?): Intent {
        return Intent(Intent.ACTION_VIEW, Uri.parse(url))
    }

    fun sendEmail(
        to: String,
        subject: String?
    ): Intent {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", to, null))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject ?: "")
        return intent
    }
    fun isValidEmail(target: CharSequence): String {
        return if (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()) EMAIL else ""
    }

    fun isValidWebUrl(target: CharSequence): String {
        return if (!TextUtils.isEmpty(target) && Patterns.WEB_URL.matcher(target).matches()) WEB else ""
    }

    fun isValidPhone(target: CharSequence): String {
        return if (!TextUtils.isEmpty(target) && Patterns.PHONE.matcher(target).matches() && target.toString().contains(
                "+"
            )
        ) TELP else ""
    }
    fun isValidDate(target: String) : String{
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        formatter.isLenient = false
        try {
            formatter.parse(target)
        }catch (t: Throwable){
            return ""
        }
        return target
    }
    fun isValidLoc(target: CharSequence): String {
        return if (!TextUtils.isEmpty(target) && target.toString().contains("geo")) LOC else ""
    }

}