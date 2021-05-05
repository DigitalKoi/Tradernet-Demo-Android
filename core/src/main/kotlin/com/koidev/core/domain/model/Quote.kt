package com.koidev.core.domain.model

import androidx.annotation.NonNull
import androidx.annotation.Nullable

/**
 * @property ticker Тикер
 * @property pcp Изменение в процентах относительно цены закрытия предыдущей торговой сессии
 * @property ltr Биржа последней сделки
 * @property name Название бумаги
 * @property ltp Цена последней сделки
 * @property chg Изменение цены последней сделки в пунктах относительно цены закрытия предыдущей торговой сессии
 * @property image Адресс для получения лого
 */
data class Quote(
    @NonNull
    val ticker: String,
    @NonNull
    val pcp: Pcp,
    @NonNull
    val ltr: String,
    @NonNull
    val name: String,
    @Nullable
    val ltp: Double?,
    @NonNull
    val chg: Double?,
    @NonNull
    val image: String
) {

    data class Pcp(
        val value: Double?,
        val status: PcpUpdateStatus,
        val time: Long
    )

    companion object {
        const val IMAGE_BASE_URL = "https://tradernet.ru/logos/get-logo-by-ticker?ticker="
    }
}

