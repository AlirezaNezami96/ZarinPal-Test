package com.saviway.zarinpaltestproject.util

import kotlin.math.ln
import kotlin.math.pow

/**
 * Created by Alireza Nezami on 11/27/2021.
 */
object CustomExtensions {

    fun String?.toUserName(): String {
        return if (this.isNullOrEmpty()) ""
        else "@$this"
    }

    fun String?.orZero(): String = this ?: "0"

    fun Int?.divideBigNumber(): String {
        return if (this == null) "0"
        else getFormattedNumber(this.toLong())
    }

    private fun getFormattedNumber(count: Long): String {
        if (count < 1000) return "" + count
        val exp = (ln(count.toDouble()) / ln(1000.0)).toInt()
        return String.format("%.1f%c", count / 1000.0.pow(exp.toDouble()), "kMGTPE"[exp - 1])
    }


}