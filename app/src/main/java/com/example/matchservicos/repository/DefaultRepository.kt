package com.example.matchservicos.repository

interface DefaultRepository<T> {

    fun inserir(target: T)
    fun buscar(): List<T>
    fun buscarPorId(id: Int): T
    fun deletar(target: T)

}