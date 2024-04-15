package br.com.nataliabraz.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import br.com.nataliabraz.orgs.dao.ProdutosDAO
import br.com.nataliabraz.orgs.database.AppDatabase
import br.com.nataliabraz.orgs.databinding.ActivityListaProdutosBinding
import br.com.nataliabraz.orgs.model.Produto
import br.com.nataliabraz.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import java.math.BigDecimal

class ListaProdutosActivity : AppCompatActivity() {
    private val dao = ProdutosDAO()

    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        ListaProdutosAdapter(context = this, produtos = dao.buscaTodos())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        configuraRecyclerView()
        configuraFab()

        val db = AppDatabase.instancia(this)
        val produtoDao = db.produtoDao()

        adapter.atualiza(produtoDao.buscaTodos())
    }

    override fun onResume() {
        super.onResume()

        val db = AppDatabase.instancia(this)
        val produtoDao = db.produtoDao()

        adapter.atualiza(produtoDao.buscaTodos())
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
                putExtra(CHAVE_PRODUTO, it)
            }
            startActivity(intent)
        }
    }
}