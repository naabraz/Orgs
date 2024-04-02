package br.com.nataliabraz.orgs.ui.recyclerview.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.nataliabraz.orgs.databinding.ProdutoItemBinding
import br.com.nataliabraz.orgs.extensions.carregar
import br.com.nataliabraz.orgs.model.Produto
import br.com.nataliabraz.orgs.ui.activity.DetalhesProdutoActivity
import br.com.nataliabraz.orgs.ui.formatter.formataParaMoedaBrasileira

class ListaProdutosAdapter(
    private val context: Context,
    produtos: List<Produto>
) : RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder>() {

    private val produtos = produtos.toMutableList()

    class ViewHolder(
        private val context: Context,
        private val binding: ProdutoItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun vincula(produto: Produto) {
            val nome = binding.produtoItemNome
            nome.text = produto.nome

            val descricao = binding.produtoItemDescricao
            descricao.text = produto.descricao

            val valor = binding.produtoItemValor
            val valorEmMoeda = formataParaMoedaBrasileira(produto.valor)
            valor.text = valorEmMoeda

            val visibilidade = if (produto.imagem != null) {
                View.VISIBLE
            } else {
                View.GONE
            }

            binding.imageView.visibility = visibilidade
            binding.imageView.carregar(context, produto.imagem)

            binding.produtoItemCard.setOnClickListener {
                vaiParaDetalhes(produto)
            }
        }

        private fun vaiParaDetalhes(produto: Produto) {
            val intent = Intent(context, DetalhesProdutoActivity::class.java).apply {
                putExtra("produto", produto)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProdutoItemBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )

        return ViewHolder(context, binding)
    }

    override fun getItemCount(): Int = produtos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produto = produtos[position]
        holder.vincula(produto)
    }

    fun atualiza(produtos: List<Produto>) {
        this.produtos.clear()
        this.produtos.addAll(produtos)
        notifyDataSetChanged()
    }
}
