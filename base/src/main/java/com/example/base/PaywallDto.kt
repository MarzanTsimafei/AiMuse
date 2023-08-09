package com.example.base

import com.adapty.models.AdaptyPaywall
import com.adapty.models.AdaptyPaywallProduct

class PaywallDto(
    val paywall: AdaptyPaywall,
    val products: List<AdaptyPaywallProduct>,
) {
}