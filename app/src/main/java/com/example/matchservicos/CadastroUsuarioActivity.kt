package com.example.matchservicos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.matchservicos.entity.UsuarioEntidade
import com.example.matchservicos.repository.UsuarioRepository
import com.google.android.material.textfield.TextInputLayout

class CadastroUsuarioActivity : AppCompatActivity() {

    lateinit var tvCadastroUsuario: TextView
    lateinit var tilNome: TextInputLayout
    lateinit var tilEmail: TextInputLayout
    lateinit var tilTelefone: TextInputLayout
    lateinit var tilSenha: TextInputLayout
    lateinit var tilConfirmarSenha: TextInputLayout
    lateinit var btCadastrar: Button
    lateinit var imBtVoltar: ImageButton

    private val usuarioRepository = UsuarioRepository(this)

    private var usuarioId: Int? = null
    private var modoEditar = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_usuario)
        supportActionBar?.hide()

        iniciarComponentes()
        buscarUsuarioLogado()
        criarListeners()
    }

    private fun iniciarComponentes() {
        tvCadastroUsuario = findViewById(R.id.tvCadastroUsuario)
        tilNome = findViewById(R.id.tilNomeTelaCadastroUsuario)
        tilEmail = findViewById(R.id.tilEmailTelaCadastroUsuario)
        tilTelefone = findViewById(R.id.tilTelefoneTelaCadastroUsuario)
        tilSenha = findViewById(R.id.tilSenhaTelaCadastroUsuario)
        tilConfirmarSenha = findViewById(R.id.tilConfirmarSenhaTelaCadastroUsuario)
        btCadastrar = findViewById(R.id.btCadastrarTelaCadastroUsuario)
        imBtVoltar = findViewById(R.id.imBtVoltarDaTelaCadastroUsuario)
    }

    private fun criarListeners() {
        btCadastrar.setOnClickListener { validarCampos() }
        imBtVoltar.setOnClickListener {
            if (!modoEditar) {
                proximaTela(LoginActivity::class.java)
            }
            finish()
        }
    }

    private fun validarCampos() {
        val nome = tilNome.editText?.text.toString()
        val email = tilEmail.editText?.text.toString()
        val telefone = tilTelefone.editText?.text.toString()
        val senha = tilSenha.editText?.text.toString()
        val confirmarSenha = tilConfirmarSenha.editText?.text.toString()

        if (nome.isNotEmpty()) {
            tilNome.error = null
        } else {
            tilNome.error = "O nome é obrigatório!"
        }
        if (email.isNotEmpty()) {
            tilEmail.error = null
        } else {
            tilEmail.error = "O e-mail é obrigatório!"
        }
        if (telefone.isNotEmpty()) {
            tilTelefone.error = null
        } else {
            tilTelefone.error = "O telefone é obrigatório!"
        }
        if (senha.isNotEmpty()) {
            tilSenha.error = null
        } else {
            tilSenha.error = "A senha é obrigatória!"
        }
        if (confirmarSenha.isNotEmpty()) {
            tilConfirmarSenha.error = null
            if (nome.isNotEmpty() && email.contains("@") && ((modoEditar && !validarSeEmailJaEstaCadastrado(
                    email
                )) ||
                        validarSeEmailJaEstaCadastrado(email)) && telefone.isNotEmpty()
            ) {
                if (senha.equals(confirmarSenha)) {
                    tilEmail.error = null
                    tilConfirmarSenha.error = null
                    salvarOuAlterarDados()
                } else {
                    tilConfirmarSenha.error = "As senhas devem ser iguais!"
                }
            } else {
                tilEmail.error = "E-mail inválido ou já cadastrado!"
            }
        } else {
            tilConfirmarSenha.error = "Confirmar a senha é obrigatória!"
        }
    }

    fun validarSeEmailJaEstaCadastrado(email: String): Boolean {
        val usuarioRepository = UsuarioRepository(this)
        val usuario = usuarioRepository.buscarPorEmail(email)
        return if (usuario == null) true else false
    }

    private fun salvarOuAlterarDados() {
        val usuarioEntidade: UsuarioEntidade
        if (modoEditar) {
            usuarioEntidade = UsuarioEntidade(
                id = usuarioId!!,
                nome = tilNome.editText?.text.toString(),
                email = tilEmail.editText?.text.toString(),
                telefone = tilTelefone.editText?.text.toString(),
                senha = tilSenha.editText?.text.toString()
            )
        } else {
            usuarioEntidade = UsuarioEntidade(
                nome = tilNome.editText?.text.toString(),
                email = tilEmail.editText?.text.toString(),
                telefone = tilTelefone.editText?.text.toString(),
                senha = tilSenha.editText?.text.toString()
            )
        }
        usuarioRepository.inserir(usuarioEntidade)

        if (!modoEditar) {
            proximaTela(LoginActivity::class.java)
        }
        finish()
    }

    private fun buscarUsuarioLogado() {
        usuarioId = intent.getIntExtra("usuario_id", 0)
        modoEditar = if (usuarioId!! > 0) true else false

        if (modoEditar) {
            tvCadastroUsuario.text = "Alterar Usuário"
            btCadastrar.text = "Alterar"

            val usuarioSelecionado = usuarioRepository.buscarPorId(usuarioId!!)
            usuarioSelecionado?.let { u ->
                tilNome.editText?.setText(u.nome)
                tilEmail.editText?.setText(u.email)
                tilTelefone.editText?.setText(u.telefone)
                tilSenha.editText?.setText(u.senha)
            }
        }
    }

    private fun proximaTela(classe: Class<*>) {
        val it = Intent(this, classe)
        startActivity(it)
        finish()
    }
}
