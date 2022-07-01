package com.example.matchservicos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.matchservicos.entity.EnderecoDTO
import com.example.matchservicos.entity.ServicoEntidade
import com.example.matchservicos.entity.UsuarioEntidade
import com.example.matchservicos.repository.ServicoRepository
import com.example.matchservicos.repository.UsuarioRepository
import com.google.android.material.textfield.TextInputLayout

class CadastroServicoActivity : AppCompatActivity() {

    lateinit var imBtVoltar: ImageButton
    lateinit var tvCadastroServico: TextView
    lateinit var spCategorias: Spinner
    lateinit var tilDescricao: TextInputLayout
    lateinit var tilLogradouro: TextInputLayout
    lateinit var tilNumero: TextInputLayout
    lateinit var tilBairro: TextInputLayout
    lateinit var tilCidade: TextInputLayout
    lateinit var tilEstado: TextInputLayout
    lateinit var tilPais: TextInputLayout
    lateinit var btCadastrar: Button

    lateinit var servicoViewModel: ServicoViewModel

    private val servicoRepository = ServicoRepository(this)
    private val usuarioRepository = UsuarioRepository(this)
    var usuarioEntidade: UsuarioEntidade? = null

    private var servicoId: Int? = null
    private var usuarioId: Int? = null
    private var modoEditar = false

    private var posicao = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_servico)
        supportActionBar?.hide()

        servicoViewModel = ViewModelProvider(this)[ServicoViewModel::class.java]

        iniciarComponentes()
        startSpinner()
        criarListeners()
        buscarUsuarioLogado()
        buscarServicoParaEditar()
    }

    private fun iniciarComponentes() {
        imBtVoltar = findViewById(R.id.imBtVoltarDaTelaCadastroServico)
        tvCadastroServico = findViewById(R.id.tvCadastroServico)
        spCategorias = findViewById(R.id.spCategoriasTelaCadastroServico)
        tilDescricao = findViewById(R.id.tilDescricaoTelaCadastroServico)
        tilLogradouro = findViewById(R.id.tilLogradouroTelaCadastroServico)
        tilNumero = findViewById(R.id.tilNumeroTelaCadastroServico)
        tilBairro = findViewById(R.id.tilBairroTelaCadastroServico)
        tilCidade = findViewById(R.id.tilCidadeTelaCadastroServico)
        tilEstado = findViewById(R.id.tilEstadoTelaCadastroServico)
        tilPais = findViewById(R.id.tilPaisTelaCadastroServico)
        btCadastrar = findViewById(R.id.btCadastrarTelaCadastroServico)
    }

    private fun criarListeners() {
        imBtVoltar.setOnClickListener { finish() }
        btCadastrar.setOnClickListener { validarCampos() }
    }

    private fun startSpinner() {
        val adapter = ArrayAdapter<String>(this, R.layout.item_spinner, servicoViewModel.categorias2)
        spCategorias.adapter = adapter
        spCategorias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@CadastroServicoActivity, "Categoria selecionada: " + servicoViewModel.categorias2[position], Toast.LENGTH_LONG)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun validarCampos() {
        val categoria = spCategorias.selectedItem.toString()
        val descricao = tilDescricao.editText?.text.toString()
        val logradouro = tilLogradouro.editText?.text.toString()
        val numero = tilNumero.editText?.text.toString()
        val bairro = tilBairro.editText?.text.toString()
        val cidade = tilCidade.editText?.text.toString()
        val estado = tilEstado.editText?.text.toString()
        val pais = tilPais.editText?.text.toString()

        if (categoria == "Escolha uma categoria!") {
            Toast.makeText(this, "Selecione uma categoria!", Toast.LENGTH_LONG).show()
        }
        if (descricao.isNotEmpty()) {
            tilDescricao.error = null
        } else {
            tilDescricao.error = "A descrição do serviço é obrigatória!"
        }
        if (logradouro.isNotEmpty()) {
            tilLogradouro.error = null
        } else {
            tilLogradouro.error = "O logradouro é obrigatório!"
        }
        if (numero.isNotEmpty()) {
            tilNumero.error = null
        } else {
            tilNumero.error = "O número é obrigatório!"
        }
        if (bairro.isNotEmpty()) {
            tilBairro.error = null
        } else {
            tilBairro.error = "O bairro é obrigatório!"
        }
        if (cidade.isNotEmpty()) {
            tilCidade.error = null
        } else {
            tilCidade.error = "A cidade é obrigatória!"
        }
        if (estado.isNotEmpty()) {
            tilEstado.error = null
        } else {
            tilEstado.error = "O estado é obrigatório!"
        }
        if (pais.isNotEmpty()) {
            tilPais.error = null
        } else {
            tilPais.error = "O país é obrigatório!"
        }

        if ((categoria != "Escolha uma categoria!") && descricao.isNotEmpty() && logradouro.isNotEmpty() && numero.isNotEmpty() &&
            bairro.isNotEmpty() && cidade.isNotEmpty() && estado.isNotEmpty() && pais.isNotEmpty()
        ) {
            salvarOuAlterarDados()
        } else {
            Toast.makeText(this, "Não foi possível salvar os dados!", Toast.LENGTH_LONG).show()
        }
    }

    private fun salvarOuAlterarDados() {
        if (modoEditar) {
            usuarioEntidade?.let { usu ->
                ServicoEntidade(
                    id = servicoId!!,
                    categoria = spCategorias.selectedItem.toString(),
                    descricao = tilDescricao.editText?.text.toString(),
                    endereco = EnderecoDTO(
                        logradouro = tilLogradouro.editText?.text.toString(),
                        numero = tilNumero.editText?.text.toString(),
                        bairro = tilBairro.editText?.text.toString(),
                        cidade = tilCidade.editText?.text.toString(),
                        estado = tilEstado.editText?.text.toString(),
                        pais = tilPais.editText?.text.toString()
                    ),
                    usuario = usu,
                ).also {
                    servicoRepository.inserir(it)
                }
            }
        } else {
            usuarioEntidade?.let { usu ->
                ServicoEntidade(
                    categoria = spCategorias.selectedItem.toString(),
                    descricao = tilDescricao.editText?.text.toString(),
                    endereco = EnderecoDTO(
                        logradouro = tilLogradouro.editText?.text.toString(),
                        numero = tilNumero.editText?.text.toString(),
                        bairro = tilBairro.editText?.text.toString(),
                        cidade = tilCidade.editText?.text.toString(),
                        estado = tilEstado.editText?.text.toString(),
                        pais = tilPais.editText?.text.toString()
                    ),
                    usuario = usu,
                ).also {
                    servicoRepository.inserir(it)
                }
            }
        }
        finish()
    }

    private fun buscarUsuarioLogado() {
        usuarioId = intent.getIntExtra("usuario_id", 0)
        usuarioEntidade = usuarioRepository.buscarPorId(usuarioId!!)
    }

    private fun buscarServicoParaEditar() {
        servicoId = intent.getIntExtra("servico_id", 0)
        modoEditar = if (servicoId!! > 0) true else false
        if (modoEditar) {
            tvCadastroServico.text = "Alterar Serviço"
            btCadastrar.text = "Alterar"

            val servicoSelecionado = servicoRepository.buscarPorId(servicoId!!)

            if (modoEditar) {
                var categoriaCadastrada = servicoSelecionado.categoria
                while (categoriaCadastrada != servicoViewModel.categorias2[posicao]) {
                    posicao++
                }
            }

            servicoSelecionado?.let { s ->
                posicao?.let { spCategorias.setSelection(it) }
                tilDescricao.editText?.setText(s.descricao)
                tilLogradouro.editText?.setText(s.endereco.logradouro)
                tilNumero.editText?.setText(s.endereco.numero)
                tilBairro.editText?.setText(s.endereco.bairro)
                tilCidade.editText?.setText(s.endereco.cidade)
                tilEstado.editText?.setText(s.endereco.estado)
                tilPais.editText?.setText(s.endereco.pais)
            }
        }
    }
}
