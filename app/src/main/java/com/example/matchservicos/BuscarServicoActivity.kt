package com.example.matchservicos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.matchservicos.adapter.ServicoAdapter
import com.example.matchservicos.entity.ServicoEntidade
import com.example.matchservicos.repository.ServicoRepository

class BuscarServicoActivity : AppCompatActivity() {

    lateinit var imBtVoltarPesquisar: ImageButton
    lateinit var spPesquisarCategorias: Spinner
    lateinit var rvListaServicos: RecyclerView
    lateinit var imBtPesquisar: ImageButton
    lateinit var tvSemServicoCadastrado: TextView
    lateinit var llPesquisar: LinearLayout

    lateinit var servicoViewModel: ServicoViewModel

    private var usuarioId: Int? = null
    private var meusServicos: String? = null

    private val servicoRepository = ServicoRepository(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_servico)
        supportActionBar?.hide()

        servicoViewModel = ViewModelProvider(this)[ServicoViewModel::class.java]

        iniciarComponentes()
        buscarUsuarioLogado()
        atualizarListasDeServicos()
        startSpinner()
        criarListeners()
    }

    override fun onResume() {
        super.onResume()
        atualizarListasDeServicos()
    }

    private fun iniciarComponentes() {
        imBtVoltarPesquisar = findViewById(R.id.imBtVoltarDaTelaBuscarServico)
        spPesquisarCategorias = findViewById(R.id.spPesquisarCategoriasTelaBuscarServico)
        rvListaServicos = findViewById(R.id.rvListaServicosTelaBuscarServico)
        imBtPesquisar = findViewById(R.id.imBtPesquisarTelaBuscarServico)
        tvSemServicoCadastrado = findViewById(R.id.tvSemServicoCadastradoTelaBuscarServico)
        llPesquisar = findViewById(R.id.llPesquisarTelaBuscarServico)
    }

    private fun criarListeners() {
        imBtVoltarPesquisar.setOnClickListener {
            if (usuarioId!! <= 0) {
                proximaTela(LoginActivity::class.java)
            }
            finish()
        }
        imBtPesquisar.setOnClickListener { buscarServicosPorCategoria() }
    }

    private fun startSpinner() {
        val adapter =
            ArrayAdapter<String>(this, R.layout.item_spinner, servicoViewModel.categorias2)
        spPesquisarCategorias.adapter = adapter
        spPesquisarCategorias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(
                    this@BuscarServicoActivity,
                    "Categoria selecionada: " + servicoViewModel.categorias2[position],
                    Toast.LENGTH_LONG
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun buscarUsuarioLogado() {
        usuarioId = intent.getIntExtra("usuario_id", 0)
    }

    private fun buscarTodosOsServicos() {
        val listaDeServicos = servicoRepository.buscar()

        listaDeServicos?.let {
            startRecyclerView(listaDeServicos)
        }

        if (listaDeServicos.isEmpty()) {
            tvSemServicoCadastrado.text = "Por enquanto não temos serviços cadastrados!"
            tvSemServicoCadastrado.visibility = View.VISIBLE
        }
        Singleton.meusServicos = ""
    }

    private fun buscarServicosPorCategoria() {
        val categoria = spPesquisarCategorias.selectedItem.toString()
        val listaServicosPorCategoria = servicoRepository.buscarPorCategoria(categoria)
        val adapter = rvListaServicos.adapter as ServicoAdapter
        tvSemServicoCadastrado.visibility = View.INVISIBLE

        listaServicosPorCategoria?.let {
            adapter.refresh(it)
        }

        if (categoria != "Escolha uma categoria!") {
            if (listaServicosPorCategoria.isEmpty()) {
                tvSemServicoCadastrado.text =
                    "Por enquanto não temos serviços cadastrados nessa categoria!"
                tvSemServicoCadastrado.visibility = View.VISIBLE
            }
        } else {
            Toast.makeText(this, "Selecione uma categoria válida!", Toast.LENGTH_LONG).show()
            tvSemServicoCadastrado.visibility = View.INVISIBLE
        }
    }

    private fun buscarServicosPorIdUsuario() {
        if (usuarioId!! > 0) {
            val listaServicosPorIdUsuario = servicoRepository.buscarPorIdUsuario(usuarioId!!)

            listaServicosPorIdUsuario?.let {
                startRecyclerView(listaServicosPorIdUsuario)
            }

            if (listaServicosPorIdUsuario != null) {
                if (listaServicosPorIdUsuario.isEmpty()) {
                    tvSemServicoCadastrado.visibility = View.VISIBLE
                }
            }
            Singleton.meusServicos = "meusServicos"
        }
    }

    private fun startRecyclerView(listaDeServicos: List<ServicoEntidade>) {
        rvListaServicos.adapter =
            ServicoAdapter(listaDeServicos.toMutableList(), onClickEditar = { servico ->
                Intent(this, CadastroServicoActivity::class.java).let {
                    it.putExtra("servico_id", servico.id)
                    it.putExtra("usuario_id", usuarioId)
                    startActivity(it)
                }
            }, onClickDeletar = { servico ->
                val servicoASerDeletado: ServicoEntidade =
                    servicoRepository.buscarPorId(servico.id)!!
                servicoRepository.deletar(servicoASerDeletado)
                atualizarListasDeServicos()
            })
        rvListaServicos.layoutManager = LinearLayoutManager(this)
    }

    private fun atualizarListasDeServicos() {
        meusServicos = intent.getStringExtra("meusServicos")
        if (meusServicos == "meusServicos") {
            llPesquisar.visibility = View.GONE
            buscarServicosPorIdUsuario()
        } else {
            buscarTodosOsServicos()
        }
    }

    private fun proximaTela(classe: Class<*>) {
        val it = Intent(this, classe)
        startActivity(it)
    }
}