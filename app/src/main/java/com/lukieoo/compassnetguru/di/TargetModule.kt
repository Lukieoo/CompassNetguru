package com.lukieoo.compassnetguru.di

import android.content.Context
import com.lukieoo.compassnetguru.utils.Localization
import com.lukieoo.compassnetguru.utils.MathematicalOperations
import com.lukieoo.compassnetguru.utils.TargetHolder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object TargetModule {

    @Singleton
    @Provides
    fun provideMathematicalOperations(): MathematicalOperations {
        return MathematicalOperations()
    }
    @Singleton
    @Provides
    fun provideTargetHolder(mathematicalOperations:MathematicalOperations,@ApplicationContext context: Context): TargetHolder {
        return TargetHolder(mathematicalOperations,context)
    }
}