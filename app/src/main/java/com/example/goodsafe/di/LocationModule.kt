package com.example.goodsafe.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
object LocationModule {

    @Provides
    fun provideFusedLocationProviderClient(@ApplicationContext context : Context) : FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)

    }
}