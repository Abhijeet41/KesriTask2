package com.abhi41.kesritravelstask2.di

import android.content.Context
import androidx.room.Room
import com.abhi41.kesritravelstask2.local.StudentDao
import com.abhi41.kesritravelstask2.local.StudentDatabase
import com.abhi41.kesritravelstask2.util.Converters
import com.abhi41.kesritravelstask2.util.GsonParser
import com.abhi41.kesritravelstask2.util.UtilConstants
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(
        @ApplicationContext context: Context
    ): StudentDatabase {
        return Room.databaseBuilder(
            context,
            StudentDatabase::class.java,
            UtilConstants.DATABASE_STUDENT
        ).addTypeConverter(Converters(GsonParser(Gson()))).build()
    }

    @Singleton
    @Provides
    fun provideDao(database: StudentDatabase): StudentDao{
        return database.dao
    }

}