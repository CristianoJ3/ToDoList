package com.example.listtodo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
        setSupportActionBar(binding.toolbar)

        tryLauncherList()
    }

    override fun onResume() {
        super.onResume()
        buscaLista()
    }

    // Função para chamar o menu manualmente na página de detalhes
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.i("TESTANDO", "onCreateOptionsMenu chamado")

        menuInflater.inflate(R.menu.menu_detalhes_lista, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Função para realizar as atividades referentes em cada item de menu
    // que for selecionado
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
                    mostrarDialogoConfirmacao()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Função para buscar cada lista e seus respectivos dados pela chave ID
    private fun tryLauncherList() {
        lisToDoId = intent.getLongExtra(CHAVE_LIST_ID, 0L)
    }

    // Função para buscar os dados da lista que foi encontrada pelo tryLauncherList
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

    // Função para preencher na página de detalhes os campos da lista encontrada
    private fun preencheCampos(listaCarregada: ListToDoDataModel) {
        with(binding) {
            listToDoTitle.text = listaCarregada.listTitle
            listToDoDescription.text = listaCarregada.listDescription
            imageDetails.setImageResource(listaCarregada.imageBackground)
        }
    }

    // Um Alert Dialog para o usuário confirmar ou cancelar antes da exclusão de um item
    private fun mostrarDialogoConfirmacao() {
        AlertDialog.Builder(this)
            .setTitle("ATENÇÃO!")
            .setMessage("Deseja apagar esta lista?")
            .setPositiveButton("Apagar") { _, _ ->
                lifecycleScope.launch {
                    listToDo?.let { lisToDoDAO.remove(it) }
                    Toast.makeText(this@DetalhesListActivity, "Lista removida", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}