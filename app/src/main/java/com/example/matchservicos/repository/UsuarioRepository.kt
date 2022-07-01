package com.example.matchservicos.repository

import android.content.Context
import com.example.matchservicos.database.MatchServicos
import com.example.matchservicos.entity.UsuarioEntidade

class UsuarioRepository(private val context: Context) : DefaultRepository<UsuarioEntidade> {

    override fun inserir(target: UsuarioEntidade) {
        MatchServicos.getInstance(context)?.getUsuarioDAO()?.inserir(target)
    }

    override fun buscar(): List<UsuarioEntidade> {
        TODO("Not yet implemented")
    }

    override fun buscarPorId(id: Int): UsuarioEntidade {
        return MatchServicos.getInstance(context)?.getUsuarioDAO()?.buscarPorId(id)!!
    }

    fun buscarPorEmail(email: String): UsuarioEntidade? {
        return MatchServicos.getInstance(context)?.getUsuarioDAO()?.buscarPorEmail(email)
    }

    override fun deletar(target: UsuarioEntidade) {
        TODO("Not yet implemented")
    }

}