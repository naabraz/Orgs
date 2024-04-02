package br.com.nataliabraz.orgs.ui.activity

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.nataliabraz.orgs.databinding.ActivityDetalhesProdutoBinding
import br.com.nataliabraz.orgs.extensions.carregar
import br.com.nataliabraz.orgs.model.Produto
import br.com.nataliabraz.orgs.ui.formatter.formataParaMoedaBrasileira

class DetalhesProdutoActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Detalhes Produto"
        setContentView(binding.root)

        val intent = intent
        val produto = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("produto", Produto::class.java)
        } else {
            intent.getParcelableExtra("produto")
        }

        if (produto !== null) {
            binding.activityDetalheProdutoImagem.carregar(this, produto.imagem)

            val valorEmMoeda = formataParaMoedaBrasileira(produto.valor)

            binding.activityDetalheProdutoValor.text = valorEmMoeda;
            binding.activityDetalheProdutoItemNome.text = produto.nome
            binding.activityDetalheProdutoItemDescricao.text = produto.descricao
        }
    }
}