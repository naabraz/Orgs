package br.com.nataliabraz.orgs.ui.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.nataliabraz.orgs.databinding.ActivityDetalhesProdutoBinding
import br.com.nataliabraz.orgs.extensions.carregar
import br.com.nataliabraz.orgs.extensions.formataParaMoedaBrasileira
import br.com.nataliabraz.orgs.model.Produto

class DetalhesProdutoActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarProduto(this)
    }

    private fun tentaCarregarProduto(context: Context) {
        intent.getParcelableExtra<Produto>(CHAVE_PRODUTO)?.let { produtoCarregado ->
            preencheCampos(context, produtoCarregado)
        } ?: finish()
    }

    private fun preencheCampos(context: Context, produtoCarregado: Produto) {
        with(binding) {
            activityDetalheProdutoImagem.carregar(context, produtoCarregado.imagem)
            activityDetalheProdutoItemNome.text = produtoCarregado.nome
            activityDetalheProdutoItemDescricao.text = produtoCarregado.descricao
            activityDetalheProdutoValor.text = produtoCarregado.valor.formataParaMoedaBrasileira()
        }
    }
}