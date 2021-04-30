package com.koidev.core.domain

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
    val pcp: String,
    @NonNull
    val ltr: String,
    @NonNull
    val name: String,
    @NonNull
    val ltp: String,
    @NonNull
    val chg: String,
    @NonNull
    val image: String
) {

    companion object {
        const val IMAGE_BASE_URL = "https://tradernet.ru/logos/get-logo-by-ticker?ticker="
    }
}

