package com.example.jetpack_compose_all_in_one.utils

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.jetpack_compose_all_in_one.features.alarm.database.AlarmInfo
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

fun showToast(context: Context, message: String) =
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

fun showLongToast(context: Context, message: String) =
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()

// Use this on a url string.
// It tries to return the file name plus the extension based on the end of the url.
fun String.extractFileName() = substringAfterLast("/")
fun Long.toReadableFileSize(): String {
    val kilobyte: Long = 1024
    val megabyte = kilobyte * 1024
    val gigabyte = megabyte * 1024
    val terabyte = gigabyte * 1024
    return when {
        this in 0 until kilobyte -> "$this B"
        this in kilobyte until megabyte -> (this / kilobyte).toString() + " KB"
        this in megabyte until gigabyte -> (this / megabyte).toString() + " MB"
        this in gigabyte until terabyte -> (this / gigabyte).toString() + " GB"
        this >= terabyte -> (this / terabyte).toString() + " TB"
        else -> "$this this"
    }
}

fun AlarmInfo.getTime() = "$hour:$minute"

fun Long.formatTime() = String.format(
    "%02d:%02d:%02d",
    TimeUnit.MILLISECONDS.toHours(this),
    TimeUnit.MILLISECONDS.toMinutes(this) -
            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(this)),
    TimeUnit.MILLISECONDS.toSeconds(this) -
            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(this))
)

fun LocalTime.simpleFormat() = this.format(DateTimeFormatter.ofPattern("HH:mm"))

// used to copy quote to clipboard
fun Context.copyToClipboard(text: CharSequence) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label", text)
    clipboard.setPrimaryClip(clip)
}

// used to share quote to other application
fun Context.shareToOthers(quote: String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, quote)
    startActivity(Intent.createChooser(intent, "Share via"))
}

@SuppressLint("SimpleDateFormat")
fun String.formatDate(): Long =
    SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").parse(this)?.time ?: 0L

@SuppressLint("SimpleDateFormat")
fun Long.toNewsDate(): String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").format(this)