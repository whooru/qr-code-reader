package com.scanny.domain.usecases

import com.scanny.domain.repository.CodesRepository

class GetCodesUseCase(private val codesRepository: CodesRepository) {
    suspend operator fun invoke() = codesRepository.getCodes()
}