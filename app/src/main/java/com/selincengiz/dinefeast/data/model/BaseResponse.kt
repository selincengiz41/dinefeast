package com.selincengiz.dinefeast.data.model


import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?
)