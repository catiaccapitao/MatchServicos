package com.example.matchservicos.dao

import androidx.room.*
import com.example.matchservicos.entity.ServicoEntidade
import com.example.matchservicos.entity.UsuarioEntidade

@Dao
interface UsuarioDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserir(usuarioEntidade: UsuarioEntidade)

    @Delete
    fun delete(usuarioEntidade: UsuarioEntidade)

    @Query("select * from usuario_table where usuario_id = :id")
    fun buscarPorId(id: Int): UsuarioEntidade

    @Query("select * from usuario_table where usuario_email = :email")
    fun buscarPorEmail(email: String): UsuarioEntidade

}