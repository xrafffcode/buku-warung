package com.app.bukuwarung

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.app.bukuwarung.room.Barang
import com.app.bukuwarung.room.BarangDb
import com.app.bukuwarung.room.Constant
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {

    val db by lazy { BarangDb(this) }

    private var barangId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        setupView()
        setuplistener()

    }

    fun setupView(){

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType){
            Constant.TYPE_CREATE -> {
                btnUpdate.visibility = View.GONE
                supportActionBar!!.setTitle("Tambah Data")
            }
            Constant.TYPE_READ -> {
                btnSave.visibility = View.GONE
                btnUpdate.visibility = View.GONE
                supportActionBar!!.setTitle("Data Barang")
                getBarang()
            }
            Constant.TYPE_UPDATE -> {
                btnSave.visibility = View.GONE
                getBarang()
                supportActionBar!!.setTitle("Update Data")
            }
        }
    }

    fun setuplistener() {
        btnSave.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.barangDao().addBarang(
                    Barang(0, etNama.text.toString(),
                    etStok.text.toString())
                )

                finish()
            }
        }

        btnUpdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.barangDao().updateBarang(
                    Barang(barangId, etNama.text.toString(),
                        etStok.text.toString())
                )

                finish()
            }
        }
    }

    fun getBarang(){
        barangId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val barang = db.barangDao().getBarangs(barangId)[0]
            etNama.setText(barang.nama)
            etStok.setText(barang.stok)
        }
    }

    override fun onNavigateUp(): Boolean {
        onBackPressed()
        return super.onNavigateUp()
    }
}