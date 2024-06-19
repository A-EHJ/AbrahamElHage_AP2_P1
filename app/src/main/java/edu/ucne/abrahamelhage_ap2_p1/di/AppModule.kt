package edu.ucne.abrahamelhage_ap2_p1.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.ucne.myapplication.data.remote.TaskApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.abrahamelhage_ap2_p1.data.local.dao.ServicioDao
import edu.ucne.abrahamelhage_ap2_p1.data.local.database.ServicioDb
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideServicioDb(@ApplicationContext appContext: Context): ServicioDb {
        return Room.databaseBuilder(
            appContext,
            ServicioDb::class.java,
            "Servicio.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideServicioDao(db: ServicioDb): ServicioDao {
        return db.servicioDao()
    }

    @Provides
    @Singleton
    fun providesMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun providesTaskApi(moshi: Moshi): TaskApi {
        return Retrofit.Builder()
            .baseUrl("https://fakerestapi.azurewebsites.net/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TaskApi::class.java)
    }
}