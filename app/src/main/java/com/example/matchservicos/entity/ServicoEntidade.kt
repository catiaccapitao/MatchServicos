package com.example.matchservicos.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "servico_table")
class ServicoEntidade(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "servico_id")
    val id: Int = 0,

    @ColumnInfo(name = "servico_categoria")
    val categoria: String,

    @ColumnInfo(name = "servico_descricao")
    val descricao: String,

    @Embedded
    val endereco: EnderecoDTO,

    @Embedded
    val usuario: UsuarioEntidade

)