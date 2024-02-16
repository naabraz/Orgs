package br.com.nataliabraz.orgs.ui.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import br.com.nataliabraz.orgs.databinding.ActivityDetalhesProdutoBinding
import br.com.nataliabraz.orgs.extensions.carregar
import br.com.nataliabraz.orgs.model.Produto

class DetalhesProdutoActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Detalhes produto"
        setContentView(binding.root)

//        if (supportActionBar != null) {
//            supportActionBar!!.hide()
//        }

        val intent = intent
        val produto = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("produto", Produto::class.java)
        } else {
            intent.getParcelableExtra("produto")
        }

        binding.activityDetalheProdutoImagem.carregar(this, produto?.imagem)

        Log.i("===Produto", "nome: ${produto?.nome}")
        Log.i("===Produto", "descricao: ${produto?.descricao}")
        Log.i("===Produto", "valor: ${produto?.valor}")
        Log.i("===Produto", "imagem: ${produto?.imagem}")
    }
}