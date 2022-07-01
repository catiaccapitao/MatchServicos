package com.example.matchservicos.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.matchservicos.R
import com.example.matchservicos.entity.ServicoEntidade

class ServicoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvCategoria: TextView = itemView.findViewById(R.id.tvCategoria)
    private val tvDescricao: TextView = itemView.findViewById(R.id.tvDescricao)
    private val tvPrestadorDeServico: TextView = itemView.findViewById(R.id.tvPrestadorDeServico)
    private val tvTelefone: TextView = itemView.findViewById(R.id.tvTelefoneTelaHome)
    private val tvLogradouro: TextView = itemView.findViewById(R.id.tvLogradouro)
    private val tvNumero: TextView = itemView.findViewById(R.id.tvNumero)
    private val tvBairro: TextView = itemView.findViewById(R.id.tvBairro)
    private val tvCidade: TextView = itemView.findViewById(R.id.tvCidade)
    private val tvEstado: TextView = itemView.findViewById(R.id.tvEstado)
    private val tvPais: TextView = itemView.findViewById(R.id.tvPais)
    val ivEditarServico: ImageView = itemView.findViewById(R.id.ivEditarServico)
    val ivExcluirServico: ImageView = itemView.findViewById(R.id.ivExcluirServico)

    fun preencher(servicoEntidade: ServicoEntidade) {
        tvCategoria.text = servicoEntidade.categoria
        tvDescricao.text = servicoEntidade.descricao
        tvPrestadorDeServico.text = servicoEntidade.usuario.nome
        tvTelefone.text = servicoEntidade.usuario.telefone
        tvLogradouro.text = servicoEntidade.endereco.logradouro
        tvNumero.text = servicoEntidade.endereco.numero
        tvBairro.text = servicoEntidade.endereco.bairro
        tvCidade.text = servicoEntidade.endereco.cidade
        tvEstado.text = servicoEntidade.endereco.estado
        tvPais.text = servicoEntidade.endereco.pais
    }
}