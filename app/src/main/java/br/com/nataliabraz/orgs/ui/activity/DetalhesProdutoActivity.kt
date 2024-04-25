package br.com.nataliabraz.orgs.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.com.nataliabraz.orgs.R
import br.com.nataliabraz.orgs.database.AppDatabase
import br.com.nataliabraz.orgs.databinding.ActivityDetalhesProdutoBinding
import br.com.nataliabraz.orgs.extensions.carregar
import br.com.nataliabraz.orgs.extensions.formataParaMoedaBrasileira
import br.com.nataliabraz.orgs.model.Produto

class DetalhesProdutoActivity : AppCompatActivity() {
    private var produtoId: Long? = null
    private var produto: Produto? = null

    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }

    private val produtoDao by lazy {
        AppDatabase.instancia(this).produtoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarProduto()
    }

    override fun onResume() {
        super.onResume()

        produtoId?.let { id ->
            produto = produtoDao.buscaPorId(id)
        }

        produto?.let {
            preencheCampos(this, it)
        } ?: finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_detalhes_produto_remover -> {
                produto?.let {
                    produtoDao.remove(it)
                    finish()
                }
            }
            R.id.menu_detalhes_produto_editar -> {
                Intent(this, FormularioProdutoActivity::class.java).apply {
                    putExtra(CHAVE_PRODUTO, produto)
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun tentaCarregarProduto() {
        intent.getParcelableExtra<Produto>(CHAVE_PRODUTO)?.let { produtoCarregado ->
            produtoId = produtoCarregado.id
        } ?: finish()
    }

    private fun preencheCampos(context: Context, produtoCarregado: Produto) {
        with(binding) {
            activityDetalheProdutoImagem.carregar(context, produtoCarregado.imagem)
            activityDetalheProdutoItemNome.text = produtoCarregado.nome
            activityDetalheProdutoItemDescricao.text = produtoCarregado.descricao
            activityDetalheProdutoValor.text = produtoCarregado.valor.formataParaMoedaBrasileira()
        }
    }
}