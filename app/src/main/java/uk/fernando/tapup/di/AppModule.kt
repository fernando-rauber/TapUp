package uk.fernando.tapup.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uk.fernando.logger.AndroidLogger
import uk.fernando.logger.MyLogger
import uk.fernando.tapup.BaseApplication
import uk.fernando.tapup.BuildConfig
import uk.fernando.tapup.datastore.PrefsStoreImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun provideLogger() : MyLogger {
        return if (BuildConfig.DEBUG)
            AndroidLogger(MyLogger.LogLevel.DEBUG)
        else
            AndroidLogger(MyLogger.LogLevel.ERROR)
    }

    @Singleton
    @Provides
    fun provideSharePreferences(@ApplicationContext app: Context) : PrefsStoreImpl =  PrefsStoreImpl(app)

}
