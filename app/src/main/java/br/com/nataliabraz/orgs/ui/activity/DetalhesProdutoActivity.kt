package br.com.nataliabraz.orgs.ui.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import br.com.nataliabraz.orgs.model.Produto

class DetalhesProdutoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Detalhes produto"

        val intent = intent
        val produto = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("produto", Produto::class.java)
        } else {
            intent.getParcelableExtra("produto")
        }

        Log.i("===Produto", "nome: ${produto?.nome}")
        Log.i("===Produto", "descricao: ${produto?.descricao}")
        Log.i("===Produto", "valor: ${produto?.valor}")
        Log.i("===Produto", "imagem: ${produto?.imagem}")
    }
}