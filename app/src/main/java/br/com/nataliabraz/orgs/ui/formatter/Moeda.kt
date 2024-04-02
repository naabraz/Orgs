package br.com.nataliabraz.orgs.ui.formatter

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

fun formataParaMoedaBrasileira(valor: BigDecimal): String {
    val formatador = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
    return formatador.format(valor)
}