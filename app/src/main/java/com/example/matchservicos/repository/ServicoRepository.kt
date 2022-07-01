package com.example.matchservicos.repository

import android.content.Context
import com.example.matchservicos.database.MatchServicos
import com.example.matchservicos.entity.ServicoEntidade

class ServicoRepository(private val context: Context) : DefaultRepository<ServicoEntidade> {

    override fun inserir(target: ServicoEntidade) {
        MatchServicos.getInstance(context)?.getServicoDAO()?.inserir(target)
    }

    override fun buscar(): List<ServicoEntidade> {
        return MatchServicos.getInstance(context)?.getServicoDAO()?.buscar()!!
    }

    override fun buscarPorId(id: Int): ServicoEntidade {
        return MatchServicos.getInstance(context)?.getServicoDAO()?.buscarPorId(id)!!
    }

    fun buscarPorCategoria(categoria: String): List<ServicoEntidade> {
        return MatchServicos.getInstance(context)?.getServicoDAO()?.buscarPorCategoria(categoria)!!
    }

    fun buscarServicoPorIdUsuario(id: Int): List<ServicoEntidade>? {
        return MatchServicos.getInstance(context)?.getServicoDAO()?.buscarPorIdUsuario(id)
    }

    override fun deletar(target: ServicoEntidade) {
        MatchServicos.getInstance(context)?.getServicoDAO()?.deletar(target)
    }

}