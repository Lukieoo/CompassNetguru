package com.lukieoo.compassnetguru.di

import android.content.Context
import com.lukieoo.compassnetguru.utils.Compass
import com.lukieoo.compassnetguru.utils.Localization
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object LocalizationModule {
    @Singleton
    @Provides
    fun provideLocalization(@ApplicationContext context: Context): Localization {
        return Localization(context)
    }
}