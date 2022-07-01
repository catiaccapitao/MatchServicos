package com.example.matchservicos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.matchservicos.repository.UsuarioRepository

class HomeActivity : AppCompatActivity() {

    lateinit var tvNome: TextView
    lateinit var tvEmail: TextView
    lateinit var tvTelefone: TextView
    lateinit var tvBuscarServico: TextView
    lateinit var tvCadastrarServico: TextView
    lateinit var tvMeusServicos: TextView
    lateinit var ivEditar: ImageView
    lateinit var ivSair: ImageView

    private var usuarioId: Int? = null
    private val usuarioRepository = UsuarioRepository(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()

        iniciarComponentes()
        buscarUsuarioLogado()
        criarListeners()
    }

    override fun onResume() {
        super.onResume()
        buscarUsuarioLogado()
    }

    private fun iniciarComponentes() {
        tvCadastrarServico = findViewById(R.id.tvCadastrarServicoTelaHome)
        tvNome = findViewById(R.id.tvNomeTelaHome)
        tvEmail = findViewById(R.id.tvEmailTelaHome)
        tvTelefone = findViewById(R.id.tvTelefoneTelaHome)
        tvBuscarServico = findViewById(R.id.tvBuscarServicoTelaHome)
        tvMeusServicos = findViewById(R.id.tvMeusServicosTelaHome)
        ivEditar = findViewById(R.id.ivEditarUsuarioTelaHome)
        ivSair = findViewById(R.id.ivSairTelaHome)
    }

    private fun criarListeners() {
        ivEditar.setOnClickListener { proximaTela(CadastroUsuarioActivity::class.java) }
        tvBuscarServico.setOnClickListener {
            Singleton.meusServicos = ""
            proximaTela(BuscarServicoActivity::class.java)
        }
        tvCadastrarServico.setOnClickListener { proximaTela(CadastroServicoActivity::class.java) }
        tvMeusServicos.setOnClickListener {
            Singleton.meusServicos = "meusServicos"
            proximaTela(BuscarServicoActivity::class.java)
        }
        ivSair.setOnClickListener {
            Singleton.meusServicos = ""
            proximaTela(LoginActivity::class.java)
            finish()
        }
    }

    private fun buscarUsuarioLogado() {
        usuarioId = intent.getIntExtra("usuario_id", 0)
        val usuarioLogado = usuarioRepository.buscarPorId(usuarioId!!)

        usuarioLogado?.let { u ->
            tvNome.setText(u.nome)
            tvEmail.setText(u.email)
            tvTelefone.setText(u.telefone)
        }
    }

    private fun proximaTela(classe: Class<*>) {
        val it = Intent(this, classe)
        it.putExtra("usuario_id", usuarioId)
        startActivity(it)
    }
}