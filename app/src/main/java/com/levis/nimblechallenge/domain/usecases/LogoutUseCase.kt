package com.levis.nimblechallenge.domain.usecases

import com.levis.nimblechallenge.core.utils.useCaseFlow
import com.levis.nimblechallenge.di.qualifiers.IoDispatcher
import com.levis.nimblechallenge.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) {

    operator fun invoke() =
        useCaseFlow(coroutineDispatcher) {
            authRepository.logout()
        }
}