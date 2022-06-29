package com.scanny.qrcodereader

import android.app.Application
import com.scanny.domain.repository.CodesRepository
import com.scanny.domain.usecases.*
import com.scanny.qrcodereader.di.ServiceLocator
import com.scanny.qrcodereader.mappers.CodesMapper

class App: Application() {

    private val codesRepository: CodesRepository
        get() = ServiceLocator.provideCodesRepository(this)

    val getCodesUseCase: GetCodesUseCase
        get() = GetCodesUseCase(codesRepository)

    val deleteCodesUseCase: DeleteCodeUseCase
        get() = DeleteCodeUseCase(codesRepository)

    val addCodeUseCase: AddCodeUseCase
        get() = AddCodeUseCase(codesRepository)

    val updateCodeUserCase: UpdateCodeUserCase
        get() = UpdateCodeUserCase(codesRepository)

    val getFavouritesCodesUseCase: GetFavouritesCodesUseCase
        get() = GetFavouritesCodesUseCase(codesRepository)

    val codesMapper = CodesMapper()

}