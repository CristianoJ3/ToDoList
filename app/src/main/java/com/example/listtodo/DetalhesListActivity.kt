package com.example.listtodo

import android.os.Bundle
import android.util.Log
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

        tryLauncherList()
    }

    override fun onResume() {
        super.onResume()
        buscaLista()
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