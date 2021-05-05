package com.koidev.core.data.network.response

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.koidev.core.annotations.OpenForTesting

open class TradernetResponse

@OpenForTesting
data class ErrorResponse(
    val type: ErrorTypeResponse
) : TradernetResponse() {

    enum class ErrorTypeResponse {
        FAILURE_CONNECTION, PARSING_DATA
    }
}

/**
 * Tradernet API quote network response item.
 *
 * @namespace
 * @property ticker Тикер
 * @property pcp Изменение в процентах относительно цены закрытия предыдущей торговой сессии
 * @property ltr Биржа последней сделки
 * @property name Название бумаги
 * @property ltp Цена последней сделки
 * @property chg Изменение цены последней сделки в пунктах относительно цены закрытия предыдущей торговой сессии
 */
@OpenForTesting
data class QuoteResponse(
    @NonNull
    val ticker: String,
    @Nullable
    val pcp: Double?,
    @Nullable
    val ltr: String?,
    @Nullable
    val name: String?,
    @Nullable
    val ltp: Double?,
    @Nullable
    val chg: Double?
) : TradernetResponse() {

    fun isNotNullableFields() =
        pcp != null || ltr != null || name != null || ltp != null || chg != null
}
