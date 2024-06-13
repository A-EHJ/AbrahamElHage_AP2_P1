package edu.ucne.abrahamelhage_ap2_p1.data.repository

import edu.ucne.abrahamelhage_ap2_p1.data.local.dao.ServicioDao
import edu.ucne.abrahamelhage_ap2_p1.data.local.entities.ServicioEntity
import javax.inject.Inject

class ServicioRepository @Inject constructor(
    private val servicioDao: ServicioDao
) {

    suspend fun saveServicio(servicio: ServicioEntity) = servicioDao.save(servicio)

    fun getServicios() = servicioDao.getAll()

    suspend fun deleteServicio(servicio: ServicioEntity) = servicioDao.delete(servicio)

    suspend fun existeServicio(descripcion: String, id: Int) = servicioDao.find(descripcion, id)

    suspend fun getServicio(servicioId: Int) = servicioDao.find(servicioId)
}