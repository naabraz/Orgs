package br.com.nataliabraz.orgs.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import br.com.nataliabraz.orgs.database.AppDatabase
import br.com.nataliabraz.orgs.databinding.ActivityListaTodosProdutosBinding
import br.com.nataliabraz.orgs.extensions.vaiPara
import br.com.nataliabraz.orgs.model.Produto
import br.com.nataliabraz.orgs.ui.recyclerview.adapter.CabecalhoUsuarioAdapter
import br.com.nataliabraz.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ListaTodosProdutosActivity : UsuarioBaseActivity() {
    private val binding by lazy {
        ActivityListaTodosProdutosBinding.inflate(layoutInflater)
    }

    private val produtoDao by lazy {
        AppDatabase.instancia(this).produtoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        lifecycleScope.launch {
            buscaTodosProdutos()
        }
    }

    private suspend fun buscaTodosProdutos() {
        val recyclerView = binding.activityListaTodosProdutosRecyclerView
        produtoDao.buscaTodos()
            .map { produtos ->
                produtos
                    .sortedBy {
                        it.usuarioId
                    }
                    .groupBy {
                        it.usuarioId
                    }.map { produtosUsuario ->
                        criaAdapterDeProdutosComCabecalho(produtosUsuario)
                    }.flatten()
            }.collect { adapter ->
                recyclerView.adapter = ConcatAdapter(adapter)
            }
    }

    private fun criaAdapterDeProdutosComCabecalho(
        produtosUsuario: Map.Entry<String?, List<Produto>>) =
        listOf(
            CabecalhoUsuarioAdapter(this, listOf(produtosUsuario.key)),
            ListaProdutosAdapter(this, produtosUsuario.value, { produtoClicado ->
                vaiPara(DetalhesProdutoActivity::class.java) {
                    putExtra(CHAVE_PRODUTO_ID, produtoClicado.id)
                }
            }, {}, {}
        ))
}