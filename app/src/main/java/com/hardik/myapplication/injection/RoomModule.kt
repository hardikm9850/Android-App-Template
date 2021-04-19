package com.hardik.myapplication.injection

import android.content.Context
import androidx.room.Room
import com.hardik.myapplication.database.SongsDAO
import com.hardik.myapplication.database.SongsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val DATABASE_NAME = "SongsDB"

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): SongsDatabase {
        return Room.databaseBuilder(context, SongsDatabase::class.java, DATABASE_NAME)
            .build()
    }

    @Provides
    @Singleton
    fun provideSongsDAO(profileDatabase: SongsDatabase): SongsDAO {
        return profileDatabase.profileDAO()
    }
}