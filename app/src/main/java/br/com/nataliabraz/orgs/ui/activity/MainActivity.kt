package br.com.nataliabraz.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import br.com.nataliabraz.orgs.R
import br.com.nataliabraz.orgs.model.Produto
import br.com.nataliabraz.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import java.math.BigDecimal

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = ListaProdutosAdapter(context = this, produtos = listOf(
            Produto(
                nome = "Laranja",
                descricao = "Laranja Lima",
                valor = BigDecimal("19.99")
            ),
            Produto(
                nome = "Manga",
                descricao = "Manga Palmer",
                valor = BigDecimal("11.99")
            ),
            Produto(
                nome = "Banana",
                descricao = "Banana Nanica",
                valor = BigDecimal("13.99")
            )
        ))
    }
}