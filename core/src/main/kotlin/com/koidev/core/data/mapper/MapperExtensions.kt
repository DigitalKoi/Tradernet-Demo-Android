package com.koidev.core.data.mapper

import com.koidev.core.data.network.response.QuoteResponse
import com.koidev.core.domain.Quote
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*


const val ZERO_VALUE = 0.0
const val EMPTY_VALUE = '—'
const val PERCENT_CHAR = '%'
const val PLUS_CHAR = '+'

fun QuoteResponse.toDomain() = Quote(
    ticker = ticker,
    pcp = preparePercentValue(pcp),
    image = ticker.toImageUri(),
    chg = chg.nullableToStringView(),
    name = name.nullableToStringView(),
    ltp = ltp.nullableToStringView(),
    ltr = ltr.nullableToStringView(),
)

fun QuoteResponse.toEntity(old: QuoteResponse?) = QuoteResponse(
    ticker = ticker,
    pcp = compareValues(pcp, old?.pcp),
    chg = compareValues(chg, old?.chg),
    name = compareString(name, old?.name),
    ltp = compareValues(ltp, old?.ltp),
    ltr = compareString(ltr, old?.ltr)
)

private fun preparePercentValue(value: Double?): String {
    val preparedValue = roundOffDecimal(value)
    val valueString = StringBuilder(preparedValue.nullableToStringView())
    if (preparedValue != null && preparedValue > 0) valueString.insert(0, PLUS_CHAR)

    return if (valueString.toString() != EMPTY_VALUE.toString()) {
        valueString.append(PERCENT_CHAR).toString()
    } else {
        valueString.toString()
    }
}

private fun Double?.nullableToStringView(): String =
    if (this != null && this != 0.0) roundOffDecimal(this).toString() else EMPTY_VALUE.toString()

private fun String?.nullableToStringView(): String = this ?: EMPTY_VALUE.toString()

private fun String.toImageUri() = Quote.IMAGE_BASE_URL + this.toLowerCase(Locale.getDefault())

private fun compareValues(new: Double?, old: Double?): Double {
    return if (new != null && new != ZERO_VALUE) new
    else if (old != null && old != ZERO_VALUE && (new == null || new == ZERO_VALUE)) old
    else ZERO_VALUE
}

private fun compareString(new: String?, old: String?): String =
    if (!new.isNullOrBlank()) new
    else if (new.isNullOrBlank() && !old.isNullOrBlank()) old
    else EMPTY_VALUE.toString()

fun roundOffDecimal(value: Double?): Double? {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.CEILING
    return df.format(value).toDouble()
}
