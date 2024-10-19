package com.example.movieapp.common


import android.content.Context
import android.widget.EditText
import android.widget.Toast
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import okhttp3.ResponseBody
import java.util.regex.Pattern


fun Context.showMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}


fun TextInputEditText.getString(): String {
    return this.text.toString().trim()
}


fun EditText.getString(): String {
    return this.text.toString().trim()
}


fun EditText.getInt(): Int = try {
    this.text.toString().toInt()
} catch (e: Exception) {
    0
}

fun String.getIntFromString(): Int = try {
    this.toInt()
} catch (e: Exception) {
    0
}

fun EditText.getDouble(): Double = try {
    this.text.toString().toDouble()
} catch (e: Exception) {
    0.0
}

fun String.getDouble(): Double = try {
    this.toDouble()
} catch (e: Exception) {
    0.0
}


fun TextInputEditText.isStringEmpty(): Boolean {
    return this.text.toString().trim().isEmpty()
}

fun EditText.isStringEmpty(): Boolean {
    return this.text.toString().trim().isEmpty()
}


fun isValidEmail(email: String): Boolean {
    return Pattern.compile(
        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
    ).matcher(email).matches()
}

fun isValidPassword(password: String): Boolean {
    return Pattern.compile(
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$"
    ).matcher(password).matches()
}


inline fun <reified T> ResponseBody.parseError(): T? {
    return try {
        Gson().fromJson(this.string(), T::class.java)
    } catch (e: Exception) {
        null
    }
}

// get progressbar as drawable
fun Context.getLoadingDrawable(): CircularProgressDrawable {
    val circularProgressDrawable = CircularProgressDrawable(this)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()
    return circularProgressDrawable
}








