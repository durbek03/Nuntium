package com.example.nuntium.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.nuntium.constants.Constants
import com.example.nuntium.data.locale.AppDatabase
import com.example.nuntium.data.remote.ApiService
import com.example.nuntium.domain.locale.RoomImpl
import com.example.nuntium.domain.locale.RoomRepository
import com.example.nuntium.domain.remote.ApiRepImpl
import com.example.nuntium.domain.remote.ApiRepository
import com.example.nuntium.sharedPreferences.MySharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context) : MySharedPreferences {
        val sharedPreferences = MySharedPreferences
        sharedPreferences.init(context = context)
        return sharedPreferences
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "nuntiumdatabase")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideApiRep(service: ApiService) : ApiRepository {
        return ApiRepImpl(apiService = service)
    }

    @Singleton
    @Provides
    fun provideRoomRep(appDatabase: AppDatabase) : RoomRepository {
        return RoomImpl(appDatabase.newsDao())
    }
}