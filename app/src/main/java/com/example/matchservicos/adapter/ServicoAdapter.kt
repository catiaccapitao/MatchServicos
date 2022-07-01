package com.example.matchservicos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.matchservicos.R
import com.example.matchservicos.Singleton
import com.example.matchservicos.entity.ServicoEntidade

class ServicoAdapter(
    private val listaDeServicos: MutableList<ServicoEntidade>,
    private val onClickEditar: (ServicoEntidade) -> Unit,
    private val onClickDeletar: (ServicoEntidade) -> Unit
) :
    RecyclerView.Adapter<ServicoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_lista_servico, parent, false)
        return ServicoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServicoViewHolder, position: Int) {
        val servicoSelecionado = listaDeServicos[position]
        holder.preencher(servicoSelecionado)
        holder.ivEditarServico.setOnClickListener { onClickEditar.invoke(servicoSelecionado) }
        holder.ivExcluirServico.setOnClickListener { onClickDeletar.invoke(servicoSelecionado) }
        if (Singleton.meusServicos == "meusServicos") {
            holder.ivEditarServico.visibility = View.VISIBLE
            holder.ivExcluirServico.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return listaDeServicos.size
    }

    fun refresh(novaLista: List<ServicoEntidade>) {
        listaDeServicos.clear()
        listaDeServicos.addAll(novaLista)
        notifyDataSetChanged()
    }
}