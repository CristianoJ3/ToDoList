package com.example.listtodo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listtodo.adapter.ListToDoAdapter
import com.example.listtodo.databinding.ActivityMainBinding
import com.example.listtodo.model.ListToDoDataModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        configuraFab()
        chamaRecyclerView()

    }

    private fun chamaRecyclerView() {
        val listaTarefasToDo = listOf(
            ListToDoDataModel("EXEMPLO TEXTO", "UMA BREVE DESCRICAO!!!"),
            ListToDoDataModel("EXEMPLO TEXTO 2", "UMA SEGUNDA BREVE DESCRICAO!!!"),
            ListToDoDataModel("EXEMPLO TEXTO", "UMA BREVE DESCRICAO!!!"),
            ListToDoDataModel("EXEMPLO TEXTO 2", "UMA SEGUNDA BREVE DESCRICAO!!!"),
            ListToDoDataModel("EXEMPLO TEXTO", "UMA BREVE DESCRICAO!!!"),
            ListToDoDataModel("EXEMPLO TEXTO 2", "UMA SEGUNDA BREVE DESCRICAO!!!")
        )

        val recyclerView = binding.listaItensToDo
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ListToDoAdapter(listaTarefasToDo)
    }

    private fun configuraFab() {
        val fab = binding.newListFab
        fab.setOnClickListener {
            Log.i("FAB", "BOTAO FAB CLICADO")
            abreRegistroNovaLista()
        }
    }

    private fun abreRegistroNovaLista() {
        val intent = Intent(this, RegistroNovaListaActivity::class.java)
        Log.i("FAB", "VAI INICIAR ACTIVITY")
        startActivity(intent)
    }
}