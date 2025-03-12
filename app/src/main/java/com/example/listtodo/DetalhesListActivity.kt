package com.example.listtodo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.listtodo.database.AppDatabase
import com.example.listtodo.databinding.ActivityDetalhesListBinding
import com.example.listtodo.model.ListToDoDataModel
import kotlinx.coroutines.launch

class DetalhesListActivity : AppCompatActivity(){

    private var lisToDoId: Long = 0L

    private var listToDo: ListToDoDataModel? = null
    private val binding by lazy {
        ActivityDetalhesListBinding.inflate(layoutInflater)
    }

    private val lisToDoDAO by lazy {
        AppDatabase.instancia(this).listToDoDAO()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Log.i("FAB", "ACESSANDO NOVA ACTIVITY")
        setSupportActionBar(binding.toolbar)

        tryLauncherList()
    }

    override fun onResume() {
        super.onResume()
        buscaLista()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.i("TESTANDO", "onCreateOptionsMenu chamado")

        menuInflater.inflate(R.menu.menu_detalhes_lista, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_detalhes_lista_editar -> {
                Intent(this, RegistroNovaListaActivity::class.java).apply {
                    putExtra(CHAVE_LIST_ID, lisToDoId)
                    startActivity(this)
                }
            }

            R.id.menu_detalhes_lista_remover -> {
                lifecycleScope.launch {
                    listToDo?.let { lisToDoDAO.remove(it) }
                    finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun buscaLista() {
        lifecycleScope.launch {
            lisToDoDAO.searchAllLists(lisToDoId).collect { listFounded ->
                listToDo = listFounded
                listToDo?.let {
                    preencheCampos(it)
                } ?: finish()
            }
        }
    }

    private fun tryLauncherList() {
        lisToDoId = intent.getLongExtra(CHAVE_LIST_ID, 0L)
    }

    private fun preencheCampos(listaCarregada: ListToDoDataModel) {
        with(binding) {
            listToDoTitle.text = listaCarregada.listTitle
            listToDoDescription.text = listaCarregada.listDescription
            imageDetails.setImageResource(listaCarregada.imageBackground)
        }
    }
}