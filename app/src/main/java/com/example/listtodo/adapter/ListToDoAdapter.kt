package com.example.listtodo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.listtodo.R
import com.example.listtodo.databinding.ActivityToDoListBinding
import com.example.listtodo.model.ListToDoDataModel


class ListToDoAdapter(
    private val context: Context,
    listToDo: List<ListToDoDataModel> = emptyList(),
    var quandoClicaNoItem: (listToDoDetails: ListToDoDataModel) -> Unit = {},
    var quandoClicaEmEditar: (listToDoDetails: ListToDoDataModel) -> Unit = {},
    var quandoClicaEmRemover: (listToDoDetails: ListToDoDataModel) -> Unit = {}
    ) :
    RecyclerView.Adapter<ListToDoAdapter.ListToDoViewHolder>() {

    private val listaTarefasToDo = listToDo.toMutableList()


    inner class ListToDoViewHolder(private val binding: ActivityToDoListBinding) :
        RecyclerView.ViewHolder(binding.root), PopupMenu.OnMenuItemClickListener  {

        private lateinit var listToDoDetails: ListToDoDataModel

        // Verificação para habilitar o clique em cada item da lista e retornar os respectivos
        // dados a página de detalhes
        init {
                itemView.setOnClickListener {
                    // Verificação da existência de valores em property lateinit
                    if (::listToDoDetails.isInitialized) {
                        Log.i("Formulario", "Lista clicada no Adapter: $listToDoDetails")
                        quandoClicaNoItem(listToDoDetails)
                    }
                }
            }

        // Função para vincular os dados recebidos pelo adapter aos referidos campos
        // da classe ListToDoDataModel
        fun vincula(listToDoDataModel: ListToDoDataModel) {
            this.listToDoDetails = listToDoDataModel
            val title = binding.titleListToDo
            title.text = listToDoDetails.listTitle
            val description = binding.textoInformativo
            description.text = listToDoDetails.listDescription
            val image = binding.iconListToDo
            image.setImageResource(listToDoDetails.imageBackground)
        }


        // Função para habilitar o evento de clique nos menus de editar e remover
        override fun onMenuItemClick(item: MenuItem?): Boolean {
            item?.let {
                when (it.itemId) {
                    R.id.menu_detalhes_lista_editar -> {
                        quandoClicaEmEditar(listToDoDetails)
                    }
                    R.id.menu_detalhes_lista_remover -> {
                        quandoClicaEmRemover(listToDoDetails)
                    }
                }
            }
            return true
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListToDoViewHolder {
        val binding = ActivityToDoListBinding.inflate(LayoutInflater.from(context), parent, false)

        return ListToDoViewHolder(binding)
    }

    override fun getItemCount(): Int = listaTarefasToDo.size

    override fun onBindViewHolder(holder: ListToDoViewHolder, position: Int) {
        val listaToDo = listaTarefasToDo[position]
        holder.vincula(listaToDo)
    }

    // Notação SupressLint para notificar o adapter quando
    // os dados forem alterados no banco de dados
    @SuppressLint("NotifyDataSetChanged")
    // Função para atualizar os dados salvos no banco de dados e apresentá-las
    // na tela principal
    fun atualiza(listToDo: List<ListToDoDataModel>){
        this.listaTarefasToDo.clear()
        this.listaTarefasToDo.addAll(listToDo)
        notifyDataSetChanged()
    }

}