package com.app.bukuwarung

import android.content.Intent
import android.graphics.Movie
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.bukuwarung.room.Barang
import com.app.bukuwarung.room.BarangDb
import com.app.bukuwarung.room.Constant
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    val db by lazy { BarangDb(this) }
    lateinit var barangAdapter: BarangAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()

        val btnAdd = findViewById<Button>(R.id.btnAdd)

        btnAdd.setOnClickListener {
            intentEdit(0,Constant.TYPE_CREATE)
        }

        supportActionBar?.hide()
    }

    fun loadBarang(){
        CoroutineScope(Dispatchers.IO).launch {
            val barang = db.barangDao().getBarang()
            Log.d("MainActivity", "dbresponse: $barang")
            withContext(Dispatchers.Main){
                barangAdapter.setData(barang)
            }
        }
    }

    fun intentEdit(barangId: Int, intentType: Int){
        startActivity(
            Intent(applicationContext, AddActivity::class.java)
                .putExtra("intent_id", barangId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun setupRecyclerView() {
        barangAdapter = BarangAdapter(arrayListOf(), object : BarangAdapter.OnAdapterListener{
            override fun onClick(barang: Barang) {
               intentEdit(barang.id,Constant.TYPE_READ)
            }

            override fun onUpdate(barang: Barang) {
                intentEdit(barang.id,Constant.TYPE_UPDATE)
            }

            override fun onDelete(barang: Barang) {
                deleteDialog(barang)
            }

        })
        rv_barang.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            addItemDecoration(
                DividerItemDecoration(
                    baseContext,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
            adapter = barangAdapter
        }
    }

    private fun deleteDialog(barang: Barang){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("yakin hapus ${barang.nama}?")
            setNegativeButton("Batal"){ dialogInterface, i->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus"){ dialogInterface, i->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.barangDao().deleteBarang(barang)
                    loadBarang()
                }
            }
        }
        alertDialog.show()
    }

    override fun onStart() {
        super.onStart()
        loadBarang()
    }
}