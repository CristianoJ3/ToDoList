package com.example.listtodo

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.listtodo.dao.ListToDoDAO
import com.example.listtodo.database.AppDatabase
import com.example.listtodo.databinding.ActivityRegistroNovaListaBinding
import com.example.listtodo.model.ListToDoDataModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.math.BigDecimal

class RegistroNovaListaActivity : AppCompatActivity() {

    private var listToDoId: Long = 0L

    val binding by lazy {
        ActivityRegistroNovaListaBinding.inflate(layoutInflater)
    }
    private val listToDoDAO: ListToDoDAO by lazy {
        val db = AppDatabase.instancia(this)
        db.listToDoDAO()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Log.i("FAB", "ACESSANDO NOVA ACTIVITY")

        configuraBotaoSubmeter()
        tryLauncherList()
    }

    private fun tryLauncherList() {
        listToDoId = intent.getLongExtra(CHAVE_LIST_ID, 0L)
    }

    private fun configuraBotaoSubmeter() {

        val submiteButton = binding.submiteDataButton

        submiteButton.setOnClickListener {
            Log.i("NEW-SAVE", "CLICANDO EM SALVAR")

            val titulo = binding.editTextTitle.text.toString()
            val descricao = binding.editDescriptionText.text.toString()
            var finishChecked = false
            var imageSet = R.drawable.icone_lista_to_do

            if (binding.switchListFinished.isChecked) {
                finishChecked = true
                imageSet = R.drawable.icone_lista_to_do_checked
                binding.iconeListaToDo.setImageResource(R.drawable.icone_lista_to_do_checked)
            } else {
                finishChecked = false
                imageSet = R.drawable.icone_lista_to_do
                binding.iconeListaToDo.setImageResource(R.drawable.icone_lista_to_do)
            }

            if (titulo.isNotBlank() && descricao.isNotBlank()) {
                val newList = ListToDoDataModel(
                    listTitle = titulo,
                    listDescription = descricao,
                    finished = finishChecked,
                    imageBackground = imageSet
                )

                lifecycleScope.launch {
                    if (listToDoId > 0) {
                        newList.id = listToDoId
                        listToDoDAO.updateList(newList)
                        Log.i("NEW-SAVE", "ITENS SALVO Titulo = $titulo , Descricao = $descricao , Imagem = $imageSet")
                    } else {
                        listToDoDAO.saveList(newList)
                    }
                    //Log.i("NEW-SAVE", "ITENS SALVO Titulo = $titulo , Descricao = $descricao , Imagem = $imageSet")
                    finish() // Fecha a Activity após salvar
                }
            }

        }
    }

    override fun onResume() {
        super.onResume()
        carregaDados()
    }

    private fun carregaDados() {
        lifecycleScope.launch {
            listToDoDAO.searchAllLists(listToDoId).collect {
                it?.let { listFounded ->
                    preencheCampos(listFounded)
                }
            }
        }
    }

    private fun preencheCampos(listaCarregada: ListToDoDataModel) {
        with(binding) {
            editTextTitle.setText(listaCarregada.listTitle)
            editDescriptionText.setText(listaCarregada.listDescription)
            iconeListaToDo.setImageResource(listaCarregada.imageBackground)
            switchListFinished.isChecked = listaCarregada.finished
        }
    }
}