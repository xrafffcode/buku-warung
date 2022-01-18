package com.app.bukuwarung.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Barang(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nama: String,
    val stok: String
    )