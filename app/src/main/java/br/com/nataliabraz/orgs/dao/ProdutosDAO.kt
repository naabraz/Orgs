package br.com.nataliabraz.orgs.dao

import br.com.nataliabraz.orgs.model.Produto
import java.math.BigDecimal

class ProdutosDAO {

    fun adiciona(produto: Produto) {
        produtos.add(produto)
    }

    fun buscaTodos() : List<Produto> {
        return produtos.toList()
    }

    companion object {
        private val produtos = mutableListOf<Produto>(
            Produto(
                nome = "Salada de Frutas",
                descricao="Laranja, Maçãs e Uva",
                valor = BigDecimal("20.98")
            )
        )
    }
}