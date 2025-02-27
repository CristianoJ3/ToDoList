package com.example.listtodo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.listtodo.databinding.ActivityRegistroNovaListaBinding

class RegistroNovaListaActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityRegistroNovaListaBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Log.i("FAB", "ACESSANDO NOVA ACTIVITY")


    }
}