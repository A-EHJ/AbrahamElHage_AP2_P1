package edu.ucne.abrahamelhage_ap2_p1.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.abrahamelhage_ap2_p1.data.local.dao.ServicioDao
import edu.ucne.abrahamelhage_ap2_p1.data.local.database.ServicioDb
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

}