package edu.ucne.abrahamelhage_ap2_p1.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.abrahamelhage_ap2_p1.data.local.dao.ServicioDao
import edu.ucne.abrahamelhage_ap2_p1.data.local.entities.ServicioEntity

@Database(
    entities = [
        ServicioEntity::class
    ],
    version = 12,
    exportSchema = false
)
abstract class ServicioDb : RoomDatabase() {
    abstract fun servicioDao(): ServicioDao
}
