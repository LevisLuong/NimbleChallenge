package com.levis.nimblechallenge.presentation.ui.home

import androidx.lifecycle.collectFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.levis.nimblechallenge.core.common.BaseViewModel
import com.levis.nimblechallenge.domain.usecases.LogoutUseCase
import com.levis.nimblechallenge.domain.usecases.SurveyListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class SurveyListViewModel @Inject constructor(
    private val surveyListUseCase: SurveyListUseCase,
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel() {

    private var _navEvent = MutableSharedFlow<SurveyNavEvent>()
    val navEvent = _navEvent.asSharedFlow()

    val surveyList = surveyListUseCase()
//        .bindLoading(this)
//        .bindError(this)
//        .returnData()
        .cachedIn(viewModelScope)


    fun logout() {
        collectFlow(
            logoutUseCase()
        ) {
            _navEvent.emit(SurveyNavEvent.Login)
        }
    }
}