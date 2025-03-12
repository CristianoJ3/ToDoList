package com.example.listtodo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
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

        init {
                itemView.setOnClickListener {
                    // verificação da existência de valores em property lateinit
                    if (::listToDoDetails.isInitialized) {
                        Log.i("Formulario", "Lista clicada no Adapter: $listToDoDetails")
                        quandoClicaNoItem(listToDoDetails)
                    }
                }
            }

        fun vincula(listToDoDataModel: ListToDoDataModel) {
            this.listToDoDetails = listToDoDataModel
            val title = binding.titleListToDo
            title.text = listToDoDetails.listTitle
            val description = binding.textoInformativo
            description.text = listToDoDetails.listDescription
            val image = binding.iconListToDo
            image.setImageResource(listToDoDetails.imageBackground)
        }


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
//        holder.tituloListaToDo.text = listaToDo.listTitle
//        holder.descricaoListaToDo.text = listaToDo.listDescription
//        holder.iconListToDo.setImageResource(listaToDo.imageBackground)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun atualiza(listToDo: List<ListToDoDataModel>){
        this.listaTarefasToDo.clear()
        this.listaTarefasToDo.addAll(listToDo)
        notifyDataSetChanged()
    }

}