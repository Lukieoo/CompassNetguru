package com.lukieoo.compassnetguru.di

import android.content.Context
import com.lukieoo.compassnetguru.utils.Compass
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object CompassModule {
    @Singleton
    @Provides
    fun provideCompass(@ApplicationContext context: Context): Compass {
        return Compass(context)
    }
}