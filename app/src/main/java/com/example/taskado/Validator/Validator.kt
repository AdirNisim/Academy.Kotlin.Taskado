package com.example.taskado.Validator

import android.text.TextUtils
import com.example.taskado.Models.User

class Validator {
    companion object {

        fun isPhoneNumberValid(phone: String): Boolean {
            val pattern = "^05\\d{8}$"
            val phoneRegex = Regex(pattern)
            val phoneNumber = phone.replace("\\s".toRegex(), "")
            return phoneRegex.matches(phoneNumber)
        }

        fun isValidEmail(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun isValidDate(startDate : String, endDate : String): Boolean {
            val sDate = startDate.split(",")
            val eDate = endDate.split(",")
            if(sDate[2].toInt() > eDate[2].toInt()) return false
            else if(sDate[2].toInt() <= eDate[2].toInt()) {
                if(sDate[0].toInt() > eDate[0].toInt()) return false
                else if(sDate[0].toInt() <= eDate[0].toInt()){
                    if(sDate[1].toInt() > eDate[1].toInt()) return false
                }
            }
            return true
        }

        fun isEmailValid(email: String): Boolean {
            val pattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$"
            val emailRegex = Regex(pattern)
            return emailRegex.matches(email)
        }

        //TODO: full name adit
        fun isFullNameValid(fullname: String): Boolean {
            val pattern = Regex("^[a-zA-Z\\s]+\$")
            return fullname.isNotEmpty() && pattern.matches(fullname)
        }
        //TODO: full name adit end

        fun reregistration(currentUser: User): Boolean {

            return when {
                TextUtils.isEmpty(currentUser.user_email) -> false
                TextUtils.isEmpty(currentUser.user_password) -> false
                TextUtils.isEmpty(currentUser.user_full_name) -> false
                TextUtils.isEmpty(currentUser.user_image) -> false
                TextUtils.isEmpty(currentUser.user_organization) -> false
                else -> true
            }
        }

        fun isValidPassword(password: String): Boolean {
            if (password.length < 8) return false
            if (password.filter { it.isDigit() }.firstOrNull() == null) return false
            if (password.filter { it.isLetter() }.filter { it.isUpperCase() }.firstOrNull() == null) return false
            if (password.filter { it.isLetter() }.filter { it.isLowerCase() }.firstOrNull() == null) return false
            if (password.filter { !it.isLetterOrDigit() }.firstOrNull() == null) return false
            return true
        }

        fun isValidTitle(title: String): Boolean {
            val pattern = Regex("^.{1,12}\$")
            return title.isNotEmpty() && pattern.matches(title)
        }
    }
}