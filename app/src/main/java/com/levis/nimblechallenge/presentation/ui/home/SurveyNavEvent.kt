package com.levis.nimblechallenge.presentation.ui.home

sealed interface SurveyNavEvent {
    data object Login : SurveyNavEvent
    data object SurveyDetail : SurveyNavEvent
}