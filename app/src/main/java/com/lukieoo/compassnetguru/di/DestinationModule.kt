package com.lukieoo.compassnetguru.di

import android.content.Context
import com.lukieoo.compassnetguru.utils.MathematicalOperations
import com.lukieoo.compassnetguru.utils.DestinationHolder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DestinationModule {

    @Singleton
    @Provides
    fun provideMathematicalOperations(): MathematicalOperations {
        return MathematicalOperations()
    }
    @Singleton
    @Provides
    fun provideTargetHolder(mathematicalOperations:MathematicalOperations,@ApplicationContext context: Context): DestinationHolder {
        return DestinationHolder(mathematicalOperations,context)
    }
}