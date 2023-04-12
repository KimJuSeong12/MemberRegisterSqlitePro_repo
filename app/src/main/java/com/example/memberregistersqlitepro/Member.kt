package com.example.memberregistersqlitepro

data class Member(
    var id: String,
    var name: String,
    var password: String,
    var phone: String,
    var email: String,
    var address: String,
    var level: String
)