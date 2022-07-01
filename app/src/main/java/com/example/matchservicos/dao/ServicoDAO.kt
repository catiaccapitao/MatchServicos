package com.example.matchservicos.dao

import androidx.room.*
import com.example.matchservicos.entity.ServicoEntidade

@Dao
interface ServicoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserir(servicoEntidade: ServicoEntidade)

    @Delete
    fun delete(servicoEntidade: ServicoEntidade)

    @Query("SELECT * FROM servico_table")
    fun buscar(): List<ServicoEntidade>

    @Query("select * from servico_table where servico_id = :id")
    fun buscarPorId(id: Int): ServicoEntidade

    @Query("select * from servico_table where servico_categoria = :categoria")
    fun buscarPorCategoria(categoria: String): List<ServicoEntidade>

    @Query("select * from servico_table where usuario_id = :id")
    fun buscarPorIdUsuario(id: Int): List<ServicoEntidade>


}