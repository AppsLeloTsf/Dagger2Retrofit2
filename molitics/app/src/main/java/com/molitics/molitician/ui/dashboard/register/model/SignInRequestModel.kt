package com.molitics.molitician.ui.dashboard.register.model

data class SignInRequestModel(val user: User? = null, var device: DeviceInfo? = null, var verification_for: String? = null,
                              val otp: String? = null, val mobile: String? = null)

data class User(val mobile: String? = null,
                var source: Int? = null,
                val password: String? = null,
                val otp: String? = null,
                var verification_for: String? = null,
                val gender: Int? = null,
                val password_confirmation: String? = null,
                val birth_date: String? = null,
                val email: String? = null,
                val state: Int? = null,
                val district: Int? = null,
                val username: String? = null,
                val auth_key: String? = null,
                val name: String? = null)

data class DeviceInfo(val type: String, val app_version: String, val device_id: String, val token: String)