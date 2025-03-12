package com.example.listtodo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listtodo.adapter.ListToDoAdapter
import com.example.listtodo.database.AppDatabase
import com.example.listtodo.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val adapter = ListToDoAdapter(context = this)

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val listToDoDAO by lazy {
        val db = AppDatabase.instancia(this)
        db.listToDoDAO()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        configuraFab()
        chamaRecyclerView()
        lifecycleScope.launch {
            searchLists()
        }


    }

    private fun searchLists() {
        lifecycleScope.launch {
            listToDoDAO.getAllLists().collect { lists ->
                Log.i("LISTA-DADOS", "Listas recuperadas: $lists")
                adapter.atualiza(lists)
            }
        }
    }

    private fun chamaRecyclerView() {

        val recyclerView = binding.listItensToDoRecyclerView

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        adapter.quandoClicaNoItem =
            {
                Log.i("Formulario", "Lista clicada: $it")

                val intent = Intent(this, DetalhesListActivity::class.java).apply {
                    putExtra(CHAVE_LIST_ID, it.id)
                }
                startActivity(intent)
            }
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