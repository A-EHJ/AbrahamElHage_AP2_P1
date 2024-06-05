package edu.ucne.abrahamelhage_ap2_p1.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Servicios")
data class ServicioEntity(
    @PrimaryKey
    val servicioId: Int? = null,
    val descripcion: String? = null,
    val precio: Double = 0.0
)