package com.levis.nimblechallenge.core.common

import androidx.lifecycle.ViewModel
import com.levis.nimblechallenge.domain.model.error.ViewError
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseViewModel : ViewModel() {

    val loadingMutableStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val errorMutableSharedFlow: MutableSharedFlow<ViewError> = MutableSharedFlow()

}