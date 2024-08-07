package br.com.nataliabraz.orgs.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import br.com.nataliabraz.orgs.database.AppDatabase
import br.com.nataliabraz.orgs.database.dao.ProdutoDao
import br.com.nataliabraz.orgs.databinding.ActivityFormularioProdutoBinding
import br.com.nataliabraz.orgs.extensions.carregar
import br.com.nataliabraz.orgs.model.Produto
import br.com.nataliabraz.orgs.model.Usuario
import br.com.nataliabraz.orgs.ui.dialog.FormularioImagemDialog
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.math.BigDecimal

class FormularioProdutoActivity : UsuarioBaseActivity() {
    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }

    private val produtoDao: ProdutoDao by lazy {
        AppDatabase.instancia(this).produtoDao()
    }

    private var url: String? = null
    private var produtoId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Cadastrar Produto"
        setContentView(binding.root)

        configuraBotaoSalvar()

        binding.activityFormularioProdutoImagem.setOnClickListener {
            FormularioImagemDialog(this).mostrar(url) { imagem ->
                url = imagem
                binding.activityFormularioProdutoImagem.carregar(this, url)
            }
        }

        tentaCarregarProduto()
    }

    private fun tentaCarregarProduto() {
        produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)
    }

    override fun onResume() {
        super.onResume()

        tentaBuscarProduto()
    }

    private fun tentaBuscarProduto() {
        lifecycleScope.launch {
            produtoDao.buscaPorId(produtoId).collect { produto ->
                produto?.let {
                    val campoUsuarioId = binding.activityFormularioProdutoUsuarioId
                    campoUsuarioId.visibility =
                        if (produto.salvoSemUsuario()) {
                            configuraCampoUsuario()
                            VISIBLE
                        } else
                            GONE
                    preencheCampos(produto)
                }
            }
        }
    }

    private fun configuraCampoUsuario() {
        lifecycleScope.launch {
            usuarios()
                .map { usuarios -> usuarios.map { it.id } }
                .collect { usuarios ->
                    configuraAutoCompleteTextView(usuarios)
                }
        }
    }

    private fun configuraAutoCompleteTextView(usuarios: List<String>) {
        val campoUsuarioId = binding.activityFormularioProdutoUsuarioId
        val adapter = ArrayAdapter(
            this@FormularioProdutoActivity,
            android.R.layout.simple_dropdown_item_1line,
            usuarios
        )
        campoUsuarioId.setAdapter(adapter)
        campoUsuarioId.setOnFocusChangeListener { _, focado ->
            if (!focado) {
                usuarioExistenteValido(usuarios)
            }
        }
    }

    private fun usuarioExistenteValido(usuarios: List<String>): Boolean {
        val campoUsuarioId = binding.activityFormularioProdutoUsuarioId
        val usuarioId = campoUsuarioId.text.toString()

        if (!usuarios.contains(usuarioId)) {
            campoUsuarioId.error = "Usuário inexistente"
            return false
        }

        return true
    }

    private fun preencheCampos(produto: Produto) {
        title = "Alterar Produto"
        url = produto.imagem

        binding.activityFormularioProdutoImagem.carregar(this, produto.imagem)
        binding.activityFormularioProdutoNome.setText(produto.nome)
        binding.activityFormularioProdutoDescricao.setText(produto.descricao)
        binding.activityFormularioProdutoValor.setText(produto.valor.toPlainString())
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.activityFormularioProdutoBotaoSalvar

        botaoSalvar.setOnClickListener {
            lifecycleScope.launch {
                tentaSalvarProduto()
            }
        }
    }

    private suspend fun tentaSalvarProduto() {
        usuario.value?.let { usuario ->
            try {
                val usuarioId = defineUsuarioId(usuario)
                val produto = criaProduto(usuarioId)
                produtoDao.salva(produto)
                finish()
            } catch (e: RuntimeException) {
                Log.e("FormularioProduto", "configuraBotaoSalvar: ", e)
            }
        }
    }

    private suspend fun defineUsuarioId(usuario: Usuario): String = produtoDao
        .buscaPorId(produtoId)
        .first()?.let { produtoEncontrado ->
            if (produtoEncontrado.usuarioId.isNullOrBlank()) {
                val usuarios = usuarios()
                    .map { usuariosEncontrados ->
                        usuariosEncontrados.map { it.id }
                    }.first()

                if (usuarioExistenteValido(usuarios)) {
                    val campoUsuarioId = binding.activityFormularioProdutoUsuarioId
                    return campoUsuarioId.text.toString()
                } else {
                    throw RuntimeException("Tentou salvar produto com usuário inexistente")
                }
            }
            null
        } ?: usuario.id

    private fun criaProduto(usuarioId: String): Produto {
        val campoNome = binding.activityFormularioProdutoNome
        val nome = campoNome.text.toString()

        val campoDescricao = binding.activityFormularioProdutoDescricao
        val descricao = campoDescricao.text.toString()

        val campoValor = binding.activityFormularioProdutoValor
        val valorEmTexto = campoValor.text.toString()

        val valor = if (valorEmTexto.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(valorEmTexto)
        }

        return Produto(
            id = produtoId,
            nome = nome,
            descricao = descricao,
            valor = valor,
            imagem = url,
            usuarioId = usuarioId
        )
    }
}