package uk.fernando.tapup.di

import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.fernando.tapup.repository.ScoreRepository
import uk.fernando.tapup.repository.ScoreRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideScoreRepository(database: FirebaseDatabase): ScoreRepository = ScoreRepositoryImpl(database)
}
