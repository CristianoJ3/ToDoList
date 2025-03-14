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

    // Função para buscar todas as listas salvas
    private fun searchLists() {
        lifecycleScope.launch {
            listToDoDAO.getAllLists().collect { lists ->
                Log.i("LISTA-DADOS", "Listas recuperadas: $lists")
                adapter.atualiza(lists)
            }
        }
    }

    // Função para habilitar os eventos de clique realizados pelo adapter
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
        adapter.quandoClicaEmEditar = {
            Log.i("ITEM-CLICADO", "configuraRecyclerView: Editar $it")
        }
        adapter.quandoClicaEmRemover = {
            Log.i("ITEM-CLICADO", "configuraRecyclerView: Remover $it")
        }
    }

    // Configura o clique fo Float Action Button
    private fun configuraFab() {
        val fab = binding.newListFab
        fab.setOnClickListener {
            Log.i("FAB", "BOTAO FAB CLICADO")
            abreRegistroNovaLista()
        }
    }

    // Função para chamar a página de registro para uma nova lista
    private fun abreRegistroNovaLista() {
        val intent = Intent(this, RegistroNovaListaActivity::class.java)
        Log.i("FAB", "VAI INICIAR ACTIVITY")
        startActivity(intent)
    }
}