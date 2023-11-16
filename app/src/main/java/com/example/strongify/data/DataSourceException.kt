package com.example.strongify.data

class DataSourceException(
    var code: Int,
    message: String,
    var details: List<String>?
) : Exception(message)