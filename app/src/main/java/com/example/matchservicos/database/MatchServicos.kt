package com.example.matchservicos.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.example.matchservicos.dao.ServicoDAO
import com.example.matchservicos.entity.UsuarioEntidade
import com.example.matchservicos.dao.UsuarioDAO
import com.example.matchservicos.entity.EnderecoDTO
import com.example.matchservicos.entity.ServicoEntidade

@Database(
    version = 1,
    entities = [
        UsuarioEntidade::class,
        ServicoEntidade::class
    ]
)
abstract class MatchServicos : RoomDatabase() {

    abstract fun getUsuarioDAO(): UsuarioDAO
    abstract fun getServicoDAO(): ServicoDAO

    companion object {

        private var instance: MatchServicos? = null

        fun getInstance(context: Context): MatchServicos? {

            if (instance == null) {
                instance = databaseBuilder(
                    context.applicationContext,
                    MatchServicos::class.java,
                    "app_matchservicos_db"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return instance
        }
    }

}