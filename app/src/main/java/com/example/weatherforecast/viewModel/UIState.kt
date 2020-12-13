package com.example.weatherforecast.viewModel

import androidx.annotation.StringRes

sealed class UIState {
    object Loading : UIState()
    object Success : UIState()
    data class Error(@StringRes val stringId: Int) : UIState()
}