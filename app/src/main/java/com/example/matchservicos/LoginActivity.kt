package com.example.matchservicos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.matchservicos.repository.UsuarioRepository
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    lateinit var tilEmail: TextInputLayout
    lateinit var tilSenha: TextInputLayout
    lateinit var btEntrar: Button
    lateinit var tvCadastrar: TextView
    lateinit var tvBuscarServico: TextView

    private val usuarioRepository = UsuarioRepository(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        iniciarComponentes()
        criarListeners()
    }

    private fun iniciarComponentes() {
        tilEmail = findViewById(R.id.tilEmailTelaLogin)
        tilSenha = findViewById(R.id.tilSenhaTelaLogin)
        btEntrar = findViewById(R.id.btEntrarTelaLogin)
        tvCadastrar = findViewById(R.id.tvCadastrarTelaLogin)
        tvBuscarServico = findViewById(R.id.tvBuscarServicoTelaLogin)
    }

    private fun criarListeners() {
        btEntrar.setOnClickListener { validarUsuario() }
        tvCadastrar.setOnClickListener { proximaTela(CadastroUsuarioActivity::class.java) }
        tvBuscarServico.setOnClickListener { proximaTela(BuscarServicoActivity::class.java) }
    }

    private fun validarUsuario() {
        val email = tilEmail.editText?.text.toString()
        val senha = tilSenha.editText?.text.toString()

        val usuario = usuarioRepository.buscarPorEmail(email)

        if (email.isNotEmpty()) {
            tilEmail.error = null
            if (email.equals(usuario?.email)) {
                tilEmail.error = null
                if (senha.isNotEmpty()) {
                    tilSenha.error = null
                    if (senha.equals(usuario?.senha)) {
                        val usuarioLogado = usuario?.id
                        usuario?.let {
                            val it = Intent(this, HomeActivity::class.java)
                            it.putExtra("usuario_id", usuarioLogado)
                            startActivity(it)
                            finish()
                        }
                    } else {
                        tilSenha.error = "Senha incorreta!"
                    }
                } else {
                    tilSenha.error = "A senha é obrigatória!"
                }
            } else {
                tilEmail.error = "E-mail não cadastrado!"
                tilSenha.error = "A senha é obrigatória!"
            }
        } else {
            tilEmail.error = "O e-mail é obrigatório!"
            tilSenha.error = "A senha é obrigatória!"
        }
    }

    private fun proximaTela(classe: Class<*>) {
        val it = Intent(this, classe)
        startActivity(it)
        finish()
    }
}