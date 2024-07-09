package br.com.nataliabraz.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.nataliabraz.orgs.database.AppDatabase
import br.com.nataliabraz.orgs.database.dao.ProdutoDao
import br.com.nataliabraz.orgs.databinding.ActivityFormularioProdutoBinding
import br.com.nataliabraz.orgs.extensions.carregar
import br.com.nataliabraz.orgs.model.Produto
import br.com.nataliabraz.orgs.ui.dialog.FormularioImagemDialog
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }

    private var url: String? = null
    private var produtoId = 0L
    private val produtoDao: ProdutoDao by lazy {
        val db = AppDatabase.instancia(this)
        db.produtoDao()
    }

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
                    preencheCampos(produto)
                } ?: finish()
            }
        }
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
        val db = AppDatabase.instancia(this)

        val produtoDao = db.produtoDao()

        botaoSalvar.setOnClickListener {
            val produtoNovo = criaProduto()

            lifecycleScope.launch {
                produtoDao.salva(produtoNovo)
                finish()
            }
        }
    }

    private fun criaProduto(): Produto {
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
            imagem = url
        )
    }
}