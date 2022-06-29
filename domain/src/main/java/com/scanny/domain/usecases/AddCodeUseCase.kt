package com.scanny.domain.usecases

import com.scanny.domain.entities.Volume
import com.scanny.domain.repository.CodesRepository

class AddCodeUseCase(private val codesRepository: CodesRepository) {
    suspend operator fun invoke(code: Volume) = codesRepository.addCode(code)
}