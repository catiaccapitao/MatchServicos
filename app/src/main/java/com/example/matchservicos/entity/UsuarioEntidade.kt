package com.example.matchservicos.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "usuario_table")
class UsuarioEntidade(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "usuario_id")
    val id: Int = 0,

    @ColumnInfo(name = "usuario_nome")
    val nome: String,

    @ColumnInfo(name = "usuario_email")
    val email: String,

    @ColumnInfo(name = "usuario_telefone")
    val telefone: String,

    @ColumnInfo(name = "usuario_senha")
    val senha: String

)
