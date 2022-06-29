package com.scanny.qrcodereader.presentation.list

import androidx.lifecycle.*
import com.scanny.domain.usecases.*
import com.scanny.qrcodereader.entities.Code
import com.scanny.qrcodereader.mappers.CodesMapper
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

class CodesViewModel(
    private val getCodesUseCase: GetCodesUseCase,
    private val addCodeUseCase: AddCodeUseCase,
    private val deleteCodeUseCase: DeleteCodeUseCase,
    private val updateCodeUserCase: UpdateCodeUserCase,
    private val getFavouritesCodesUseCase: GetFavouritesCodesUseCase,
    private val mapper: CodesMapper
) : ViewModel() {

    private val _codes = MutableLiveData<List<Code>>()
    val codes: LiveData<List<Code>> = _codes

    private val _favouriteCodes = MutableLiveData<List<Code>>()
    val favouriteCodes: LiveData<List<Code>> = _favouriteCodes

    private val _dataLoading = MutableLiveData(true)
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _refreshButtons = MutableLiveData(false)
    val refreshButtons: LiveData<Boolean> = _refreshButtons

    private val _isAllOpen = MutableLiveData(true)
    val isAllOpen: LiveData<Boolean> = _isAllOpen

    fun refreshButtons() {
        _refreshButtons.postValue(true)
    }

    fun stopRefreshButtons() {
        _refreshButtons.postValue(false)
    }


    fun changePage(isAllPage: Boolean) {
        _isAllOpen.postValue(isAllPage)
    }

    @OptIn(InternalCoroutinesApi::class)
    fun getCodes() {
        viewModelScope.launch {
            _dataLoading.postValue(true)
            val codesFlow = getCodesUseCase.invoke()
            codesFlow.collect(){ codes ->
                this@CodesViewModel._codes.value = mapper.fromVolumeToCodes(codes)
                _dataLoading.postValue(false)
            }
        }
    }

    @OptIn(InternalCoroutinesApi::class)
    fun getFavouriteCodes() {
        viewModelScope.launch {
            _dataLoading.postValue(true)
            val codesFlow = getFavouritesCodesUseCase.invoke()
            codesFlow.collect { codes ->
                this@CodesViewModel._favouriteCodes.value = mapper.fromVolumeToCodes(codes)
                _dataLoading.postValue(false)
            }
        }
    }

    fun addCode(code: Code) {
        viewModelScope.launch {
            addCodeUseCase.invoke(mapper.fromCodeToVolume(code))
            getCodes()
        }
    }

    fun deleteCode(code: Code) {
        viewModelScope.launch {
            deleteCodeUseCase.invoke(mapper.fromCodeToVolume(code))
            getCodes()
        }
    }

    fun addToFavourites(code: Code) {
        viewModelScope.launch {
            code.favourite = !code.favourite
            updateCodeUserCase.invoke(mapper.fromCodeToVolume(code))
            getCodes()
        }
    }


    class CodesViewModelFactory(
        private val getCodesUseCase: GetCodesUseCase,
        private val addCodeUseCase: AddCodeUseCase,
        private val deleteCodeUseCase: DeleteCodeUseCase,
        private val updateCodeUserCase: UpdateCodeUserCase,
        private val getFavouritesCodesUseCase: GetFavouritesCodesUseCase,
        private val mapper: CodesMapper
    ) :
        ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CodesViewModel(
                getCodesUseCase,
                addCodeUseCase,
                deleteCodeUseCase,
                updateCodeUserCase,
                getFavouritesCodesUseCase,
                mapper
            ) as T
        }
    }
}