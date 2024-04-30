package br.com.nataliabraz.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.com.nataliabraz.orgs.R
import br.com.nataliabraz.orgs.database.AppDatabase
import br.com.nataliabraz.orgs.databinding.ActivityListaProdutosBinding
import br.com.nataliabraz.orgs.model.Produto
import br.com.nataliabraz.orgs.ui.recyclerview.adapter.ListaProdutosAdapter

class ListaProdutosActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        ListaProdutosAdapter(context = this)
    }

    private val produtoDao by lazy {
        val db = AppDatabase.instancia(this)
        db.produtoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        configuraRecyclerView()
        configuraFab()
    }

    override fun onResume() {
        super.onResume()

        adapter.atualiza(produtoDao.buscaTodos())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_ordena_lista, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val produtosOrdenados: List<Produto>? = when (item.itemId) {
            R.id.menu_ordena_lista_filtrar_nome_asc -> {
                produtoDao.ordenaPorNomeAsc()
            }
            R.id.menu_ordena_lista_filtrar_nome_desc -> {
                produtoDao.ordenaPorNomeDesc()
            }
            R.id.menu_ordena_lista_filtrar_descricao_asc -> {
                produtoDao.ordenaPorDescricaoAsc()
            }
            R.id.menu_ordena_lista_filtrar_descricao_desc -> {
                produtoDao.ordenaPorDescricaoDesc()
            }
            R.id.menu_ordena_lista_filtrar_valor_asc -> {
                produtoDao.ordenaPorValorAsc()
            }
            R.id.menu_ordena_lista_filtrar_valor_desc -> {
                produtoDao.ordenaPorValorDesc()
            }
            R.id.menu_ordena_lista_filtrar_sem_ordenacao -> {
                produtoDao.buscaTodos()
            }
            else -> null
        }

        produtosOrdenados?.let {
            adapter.atualiza(it)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun configuraFab() {
        val fab = binding.activityListaProdutosFab
        fab.setOnClickListener {
            vaiParaFormularioProduto()
        }
    }

    private fun vaiParaFormularioProduto() {
        val intent = Intent(this, FormularioProdutoActivity::class.java)
        startActivity(intent)
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.activityListaProdutosRecyclerView
        recyclerView.adapter = adapter

        adapter.quandoClicaNoItem = {
            val intent = Intent(
                this,
                DetalhesProdutoActivity::class.java
            ).apply {
                putExtra(CHAVE_PRODUTO_ID, it.id)
            }
            startActivity(intent)
        }
    }
}