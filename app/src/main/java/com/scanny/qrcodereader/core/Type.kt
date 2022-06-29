package com.scanny.qrcodereader.core

import com.scanny.qrcodereader.R

enum class Type(val index: Int) {

    TEXT(1) {
        override fun toString(): String {
            return "Text"
        }

        override fun toImage(): Int = R.drawable.ic_file_text
    },
    PHONE(2) {
        override fun toString(): String {
            return "Phone"
        }

        override fun toImage(): Int = R.drawable.ic_phone
    },
    MAIL(3) {
        override fun toString(): String {
            return "E-Mail"
        }

        override fun toImage(): Int = R.drawable.ic_mail


    },
    URL(4) {
        override fun toString(): String {
            return "URL"
        }

        override fun toImage(): Int = R.drawable.ic_globe
    },
    SMS(5) {
        override fun toString(): String {
            return "SMS"
        }

        override fun toImage(): Int = R.drawable.ic_message_square
    },
    GEOPOINT(6) {
        override fun toString(): String {
            return "Geo Point"
        }

        override fun toImage(): Int = R.drawable.ic_globe_2
    },
    WIFI(7) {
        override fun toString(): String {
            return "Wifi"
        }

        override fun toImage(): Int = R.drawable.ic_wifi
    };

    fun getType(index: Int): String {
        when (index) {
            1 -> {
                return TEXT.toString()
            }
            2 -> {
                return PHONE.toString()
            }
            3 -> {
                return MAIL.toString()
            }
            4 -> {
                return URL.toString()
            }
            5 -> {
                return SMS.toString()
            }
            6 -> {
                return GEOPOINT.toString()
            }
            7 -> {
                return WIFI.toString()
            }
        }
        return ""
    }

    open fun toImage(): Int {
        return R.drawable.ic_file_text
    }


}