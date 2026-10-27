package com.yxhelzvl.umsnh_id.core.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.yxhelzvl.umsnh_id.core.data.SessionManager
import com.yxhelzvl.umsnh_id.core.navigation.NavigationWrapper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.io.File

val dataStoreModule = module {
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            produceFile = {
                File(androidContext().filesDir, "datastore/session_prefs.preferences_pb")
            }
        )
    }

    single { SessionManager(get()) } // Inyección del DataStore aquí
}
