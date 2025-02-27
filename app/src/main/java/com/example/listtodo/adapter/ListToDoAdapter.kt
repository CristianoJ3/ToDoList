package com.example.listtodo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listtodo.databinding.ActivityToDoListBinding


class ListToDoAdapter(private val context: Context) :
    RecyclerView.Adapter<ListToDoAdapter.ListToDoViewHolder>() {
    class ListToDoViewHolder(private val binding: ActivityToDoListBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListToDoViewHolder {
        val binding = ActivityToDoListBinding.inflate(LayoutInflater.from(context), parent, false)

        return ListToDoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ListToDoViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}