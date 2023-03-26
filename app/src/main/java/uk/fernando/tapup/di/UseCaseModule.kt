package uk.fernando.tapup.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.fernando.logger.MyLogger
import uk.fernando.tapup.usecase.GameUseCase

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGameUseCase(logger: MyLogger) = GameUseCase(logger)
}
