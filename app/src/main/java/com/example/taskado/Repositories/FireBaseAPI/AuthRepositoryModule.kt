package com.example.taskado.Repositories.FireBaseAPI
import com.example.taskado.Repositories.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthRepositoryModule {
    @Binds
    abstract fun AuthRepositoryFirebaseImpl(authRepositoryFirebase: AuthRepositoryFirebase): AuthRepository
}