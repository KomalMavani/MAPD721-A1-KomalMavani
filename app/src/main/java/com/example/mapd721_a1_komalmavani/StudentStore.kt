package com.example.mapd721_a1_komalmavani

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StudentStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("studentData")
        private val ID_KEY = stringPreferencesKey("id")
        private val STUDENT_NAME_KEY = stringPreferencesKey("studentName")
        private val COURSE_NAME_KEY = stringPreferencesKey("coursename")
    }

    val getID: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[ID_KEY] ?: "922"
    }

    val getStudentName: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[STUDENT_NAME_KEY] ?: ""
    }

    val getCourseName: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[COURSE_NAME_KEY] ?: ""
    }

    suspend fun saveStudentDetails(id:String, studentName:String, courseName:String) {
        context.dataStore.edit { preferences ->
            preferences[ID_KEY] = id
            preferences[STUDENT_NAME_KEY] = studentName
            preferences[COURSE_NAME_KEY] = courseName

        }
    }
}