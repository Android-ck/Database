package com.zerir.sqldelight

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zerir.sqldelight.data.PersonDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val personDataSource: PersonDataSource,
) : ViewModel() {

    val allPersons = personDataSource.getAllPersons()

    var personDetails by mutableStateOf<PersonEntity?>(null)
        private set

    var firstNameText by mutableStateOf("")
        private set

    var lastNameText by mutableStateOf("")
        private set

    fun onPersonClicked(id: Long) {
        viewModelScope.launch {
            personDetails = personDataSource.getPersonById(id)
        }
    }

    fun onAddPerson() {
        if(firstNameText.isNotBlank() && lastNameText.isNotBlank()) {
            viewModelScope.launch {
                personDataSource.insertPerson(firstName = firstNameText, lastName = lastNameText)
                firstNameText = ""
                lastNameText = ""
            }
        }
    }

    fun onDeletePerson(id: Long) {
        viewModelScope.launch {
            personDataSource.deletePersonById(id)
        }
    }

    fun onFirstNameChange(firstName: String) {
        this.firstNameText = firstName
    }

    fun onLastNameChange(lastName: String) {
        this.lastNameText = lastName
    }

    fun onPersonDetailsDialogDismiss() {
        personDetails = null
    }
}