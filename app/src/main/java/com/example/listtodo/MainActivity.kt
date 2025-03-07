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
            ListToDoDataModel(1,"EXEMPLO TEXTO", "UMA BREVE DESCRICAO!!!", true),
            ListToDoDataModel(2, "EXEMPLO TEXTO 2", "UMA SEGUNDA BREVE DESCRICAO!!!", false),
            ListToDoDataModel(3, "EXEMPLO TEXTO", "UMA BREVE DESCRICAO!!!", true),
            ListToDoDataModel(4, "EXEMPLO TEXTO 2", "UMA SEGUNDA BREVE DESCRICAO!!!", false),
            ListToDoDataModel(5, "EXEMPLO TEXTO", "UMA BREVE DESCRICAO!!!", false),
            ListToDoDataModel(6 ,"EXEMPLO TEXTO 2", "UMA SEGUNDA BREVE DESCRICAO!!!",true)
        )

        val recyclerView = binding.listItensToDo
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