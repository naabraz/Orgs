package br.com.nataliabraz.orgs.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class DetalhesProdutoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Detalhes produto"

        val intent = intent
        val produto = intent.getBundleExtra("produto")

        Log.i("===DetalhesProdutoActivity", "onCreate: $produto")
    }
}