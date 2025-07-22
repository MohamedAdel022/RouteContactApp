package com.route.routecontactsapp.models

data class ContactDM(
    val name: String,
    val phoneNumber: String,
    val email: String? = null,
    val photoUri: String? = null
)
