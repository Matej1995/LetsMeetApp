package cz.sandera.letsmeet.presentation.util

fun Double.convertCelsiusToFahrenheit(): Double = (this * 9 / 5) + 32

fun Double.convertWindKilometersToMeters(): Double = this * 0.27778

fun Double.format(digits: Int) = "%.${digits}f".format(this)