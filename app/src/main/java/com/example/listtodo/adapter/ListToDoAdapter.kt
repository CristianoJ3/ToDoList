package com.example.listtodo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.listtodo.databinding.ActivityToDoListBinding
import com.example.listtodo.model.ListToDoDataModel


class ListToDoAdapter(
    private val context: Context,
    listToDo: List<ListToDoDataModel> = emptyList()
) :
    RecyclerView.Adapter<ListToDoAdapter.ListToDoViewHolder>() {

    private val listaTarefasToDo = listToDo.toMutableList()


    class ListToDoViewHolder(private val binding: ActivityToDoListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val tituloListaToDo: TextView = binding.titleListToDo
        val descricaoListaToDo: TextView = binding.textoInformativo
        val iconListToDo: ImageView = binding.iconListToDo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListToDoViewHolder {
        val binding = ActivityToDoListBinding.inflate(LayoutInflater.from(context), parent, false)

        return ListToDoViewHolder(binding)
    }

    override fun getItemCount(): Int = listaTarefasToDo.size

    override fun onBindViewHolder(holder: ListToDoViewHolder, position: Int) {
        val listaToDo = listaTarefasToDo[position]
        holder.tituloListaToDo.text = listaToDo.listTitle
        holder.descricaoListaToDo.text = listaToDo.listDescription
        holder.iconListToDo.setImageResource(listaToDo.imageBackground)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun atualiza(listToDo: List<ListToDoDataModel>){
        this.listaTarefasToDo.clear()
        this.listaTarefasToDo.addAll(listToDo)
        notifyDataSetChanged()
    }

}