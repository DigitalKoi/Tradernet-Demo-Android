package com.koidev.core.data.mapper

import com.koidev.core.data.network.response.QuoteResponse
import com.koidev.core.domain.model.PcpUpdateStatus
import com.koidev.core.domain.model.Quote
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*


const val ZERO_VALUE = 0.0
const val EMPTY_STRING = "_._"



private fun checkPcpAndReturnUpdateStatus(
    old: Double?, new: Double?
): PcpUpdateStatus =
    if (new != null && new > old ?: ZERO_VALUE) PcpUpdateStatus.GREEN
    else if (new ?: ZERO_VALUE < old ?: ZERO_VALUE) PcpUpdateStatus.RED
    else PcpUpdateStatus.WHITE

fun QuoteResponse.toDomain(old: Quote?) =
    Quote(
        ticker = ticker,
        pcp = Quote.Pcp(
            value = pcp ?: old?.pcp?.value ?: ZERO_VALUE,
            status = checkPcpAndReturnUpdateStatus(old?.pcp?.value, pcp),
            time = System.currentTimeMillis()
        ),
        image = Quote.IMAGE_BASE_URL + ticker.toLowerCase(Locale.ROOT),
        chg = chg ?: old?.chg ?: ZERO_VALUE,
        name = name ?: old?.name ?: EMPTY_STRING,
        ltr = ltr ?: old?.ltr ?: EMPTY_STRING,
        ltp = ltp ?: old?.ltp ?: ZERO_VALUE
    )
