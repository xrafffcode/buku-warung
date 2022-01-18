package com.app.bukuwarung.room

import androidx.room.*


@Dao
interface BarangDao {

    @Insert
    suspend fun addBarang(barang: Barang)

    @Update
    suspend fun updateBarang(barang: Barang)

    @Delete
    suspend fun deleteBarang(barang: Barang)

    @Query ("SELECT * FROM barang")
    suspend fun getBarang():List<Barang>

    @Query ("SELECT * FROM barang WHERE id=:barang_id")
    suspend fun getBarangs(barang_id: Int):List<Barang>
}    