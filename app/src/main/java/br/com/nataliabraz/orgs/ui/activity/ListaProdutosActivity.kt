package br.com.nataliabraz.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.com.nataliabraz.orgs.dao.ProdutosDAO
import br.com.nataliabraz.orgs.databinding.ActivityListaProdutosBinding
import br.com.nataliabraz.orgs.ui.recyclerview.adapter.ListaProdutosAdapter

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

        AlertDialog.Builder(this)
            .setTitle("Titulo de teste")
            .setMessage("Mensagem de teste")
            .setPositiveButton("Confirmar") { _, _->

            }
            .setNegativeButton("Cancelar") {_, _->

            }
            .show()
    }

    override fun onResume() {
        super.onResume()
        adapter.atualiza(dao.buscaTodos())
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
    }
}