package com.example.weatherforecast.viewModel

import androidx.annotation.StringRes

sealed class UIState {
    object Default : UIState()
    object StartLoading : UIState()
    object UpdateSuccess : UIState()
    object QuerySuccess : UIState()
    sealed class Loading: UIState() {
        object FromDefault : Loading()
        object FromUpdateSuccess : Loading()
        object FromQuerySuccess : Loading()
        data class FromError(@StringRes val stringId: Int) : Loading()
    }
    data class Error(@StringRes val stringId: Int) : UIState()
}