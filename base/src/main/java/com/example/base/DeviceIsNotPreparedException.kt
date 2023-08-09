package com.example.base

class DeviceIsNotPreparedException(private val original: Exception) : Exception(original) {
}