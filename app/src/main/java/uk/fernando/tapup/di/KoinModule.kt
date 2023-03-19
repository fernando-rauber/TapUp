package uk.fernando.tapup.di


import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import uk.fernando.logger.AndroidLogger
import uk.fernando.logger.MyLogger
import uk.fernando.tapup.BuildConfig
import uk.fernando.tapup.datastore.PrefsStore
import uk.fernando.tapup.datastore.PrefsStoreImpl
import uk.fernando.tapup.usecase.GameUseCase
import uk.fernando.tapup.viewmodel.GameViewModel

object KoinModule {

    /**
     * Keep the order applied
     * @return List<Module>
     */
    fun allModules(): List<Module> = listOf(coreModule, repositoryModule, useCaseModule, viewModelModule)

    private val coreModule = module {
        single { getAndroidLogger() }
        single<PrefsStore> { PrefsStoreImpl(androidApplication()) }
    }


    private val repositoryModule: Module
        get() = module {

        }

    private val useCaseModule: Module
        get() = module {
            single { GameUseCase(get()) }
        }

    private val viewModelModule: Module
        get() = module {
            viewModel { GameViewModel(get()) }
        }

    fun getAndroidLogger(): MyLogger {
        return if (BuildConfig.BUILD_TYPE == "debug")
            AndroidLogger(MyLogger.LogLevel.DEBUG)
        else
            AndroidLogger(MyLogger.LogLevel.ERROR)
    }
}