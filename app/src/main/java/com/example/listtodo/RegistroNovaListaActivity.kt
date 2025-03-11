package com.example.listtodo

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

    }

    private fun configuraBotaoSubmeter() {

        val submiteButton = binding.submiteDataButton

        submiteButton.setOnClickListener {
            Log.i("NEW-SAVE", "CLICANDO EM SALVAR")

            val titulo = binding.editTextTitle.text.toString()
            val descricao = binding.editDescriptionText.text.toString()

            if (titulo.isNotBlank() && descricao.isNotBlank()) {
                val newList = ListToDoDataModel(
                    listTitle = titulo,
                    listDescription = descricao,
                    finished = false
                )

                lifecycleScope.launch {
                    listToDoDAO.saveList(newList)
                    Log.i("NEW-SAVE", "ITENS SALVO Titulo = $titulo , Descricao = $descricao")
                    finish() // Fecha a Activity após salvar
                }
            }

        }
    }

    private fun createList(): ListToDoDataModel {
        val textTitle = binding.editTextTitle
        val title = textTitle.text.toString()
        val descriptionText = binding.editDescriptionText
        val description = descriptionText.text.toString()
        val switchButton = binding.switchListFinished
        var boolean = true
        if (switchButton.isChecked) {
            boolean = true
        } else {
            boolean = false
        }

        return ListToDoDataModel(
            listTitle = title,
            listDescription = description,
            finished = boolean
        )
    }
}