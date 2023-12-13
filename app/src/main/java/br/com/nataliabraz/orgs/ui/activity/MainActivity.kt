package br.com.nataliabraz.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import br.com.nataliabraz.orgs.R
import br.com.nataliabraz.orgs.dao.ProdutosDAO
import br.com.nataliabraz.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val dao = ProdutosDAO()
        Log.i("MainActivity", "onCreate: ${dao.buscaTodos()}")
        recyclerView.adapter = ListaProdutosAdapter(context = this, produtos = dao.buscaTodos())

        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener {
            val intent = Intent(this, FormularioProdutoActivity::class.java)
            startActivity(intent)
        }
    }
}