package br.com.nataliabraz.orgs.ui.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.com.nataliabraz.orgs.R
import br.com.nataliabraz.orgs.database.AppDatabase
import br.com.nataliabraz.orgs.databinding.ActivityDetalhesProdutoBinding
import br.com.nataliabraz.orgs.extensions.carregar
import br.com.nataliabraz.orgs.extensions.formataParaMoedaBrasileira
import br.com.nataliabraz.orgs.model.Produto

private const val TAG = "DetalhesProduto"

class DetalhesProdutoActivity : AppCompatActivity() {
    private lateinit var produto: Produto
    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarProduto(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (::produto.isInitialized) {
            val db = AppDatabase.instancia(this)
            val produtoDao = db.produtoDao()

            when(item.itemId) {
                R.id.menu_detalhes_produto_remover -> {
                    produtoDao.remove(produto)
                    finish()
                }
                R.id.menu_detalhes_produto_editar -> {
                    Log.i(TAG, "onOptionsItemSelected: editar")
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun tentaCarregarProduto(context: Context) {
        intent.getParcelableExtra<Produto>(CHAVE_PRODUTO)?.let { produtoCarregado ->
            produto = produtoCarregado
            preencheCampos(context, produtoCarregado)
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