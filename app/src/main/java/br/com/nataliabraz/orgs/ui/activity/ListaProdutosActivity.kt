package br.com.nataliabraz.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.PopupMenu.OnMenuItemClickListener
import androidx.appcompat.app.AppCompatActivity
import br.com.nataliabraz.orgs.R
import br.com.nataliabraz.orgs.database.AppDatabase
import br.com.nataliabraz.orgs.databinding.ActivityListaProdutosBinding
import br.com.nataliabraz.orgs.ui.recyclerview.adapter.ListaProdutosAdapter

class ListaProdutosActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        ListaProdutosAdapter(context = this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        configuraRecyclerView()
        configuraFab()
    }

    override fun onResume() {
        super.onResume()

        val db = AppDatabase.instancia(this)
        val produtoDao = db.produtoDao()

        adapter.atualiza(produtoDao.buscaTodos())
    }

    private fun ordenaPorNome(ordem: String) {
        val db = AppDatabase.instancia(this)
        val produtoDao = db.produtoDao()

        when(ordem) {
            "asc" -> adapter.atualiza(produtoDao.ordenaPorNomeAsc())
            "desc" -> adapter.atualiza(produtoDao.ordenaPorNomeDesc())
        }
    }

    private fun ordenaPorDescricao(ordem: String) {
        val db = AppDatabase.instancia(this)
        val produtoDao = db.produtoDao()

        when(ordem) {
            "asc" -> adapter.atualiza(produtoDao.ordenaPorDescricaoAsc())
            "desc" -> adapter.atualiza(produtoDao.ordenaPorDescricaoDesc())
        }
    }

    private fun ordenaPorValor(ordem: String) {
        val db = AppDatabase.instancia(this)
        val produtoDao = db.produtoDao()

        when(ordem) {
            "asc" -> adapter.atualiza(produtoDao.ordenaPorValorAsc())
            "desc" -> adapter.atualiza(produtoDao.ordenaPorValorDesc())
        }
    }

    private fun removeOrdenacao() {
        val db = AppDatabase.instancia(this)
        val produtoDao = db.produtoDao()

        adapter.atualiza(produtoDao.buscaTodos())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_ordena_lista, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_ordena_lista_filtrar_nome_asc -> {
                ordenaPorNome("asc")
            }
            R.id.menu_ordena_lista_filtrar_nome_desc -> {
                ordenaPorNome("desc")
            }
            R.id.menu_ordena_lista_filtrar_descricao_asc -> {
                ordenaPorDescricao("asc")
            }
            R.id.menu_ordena_lista_filtrar_descricao_desc -> {
                ordenaPorDescricao("desc")
            }
            R.id.menu_ordena_lista_filtrar_valor_asc -> {
                ordenaPorValor("asc")
            }
            R.id.menu_ordena_lista_filtrar_valor_desc -> {
                ordenaPorValor("desc")
            }
            R.id.menu_ordena_lista_filtrar_sem_ordenacao -> {
                removeOrdenacao()
            }
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