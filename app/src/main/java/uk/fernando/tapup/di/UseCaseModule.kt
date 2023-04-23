package uk.fernando.tapup.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.fernando.logger.MyLogger
import uk.fernando.tapup.datastore.PrefsStore
import uk.fernando.tapup.repository.ScoreRepository
import uk.fernando.tapup.usecase.GameUseCase
import uk.fernando.tapup.usecase.PrefUseCase
import uk.fernando.tapup.usecase.ScoreUseCase

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGameUseCase(logger: MyLogger) = GameUseCase(logger)

    @Provides
    fun providePrefUseCase(prefs: PrefsStore) = PrefUseCase(prefs)

    @Provides
    fun provideScoreUseCase(repository: ScoreRepository, prefsUseCase: PrefUseCase, logger: MyLogger) = ScoreUseCase(repository, prefsUseCase, logger)
}
