package com.levis.nimblechallenge.presentation.ui.splash

import androidx.lifecycle.viewModelScope
import com.levis.nimblechallenge.core.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel() {
    private val splashShowFlow = MutableStateFlow(true)
    val isSplashShow = splashShowFlow.asStateFlow()

    init {
        viewModelScope.launch {
            splashShowFlow.value = false
        }
    }
}