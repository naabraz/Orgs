package br.com.nataliabraz.orgs.ui.dialog

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import br.com.nataliabraz.orgs.databinding.FormularioImagemBinding
import br.com.nataliabraz.orgs.extensions.carregar

class FormularioImagemDialog(private val context: Context) {
    fun mostrar() {
        val binding = FormularioImagemBinding.inflate(LayoutInflater.from(context))
        binding.formularioImagemBotaoCarregar.setOnClickListener {
            val url = binding.formularioImagemUrl.text.toString()
            binding.formularioImagemImageview.carregar(context, url)
        }

        AlertDialog.Builder(context)
            .setView(binding.root)
            .setPositiveButton("Confirmar") { _, _->
                val url = binding.formularioImagemUrl.text.toString()
                Log.i("FormularioImagemDialog", "mostra: $url")
//                binding.activityFormularioProdutoImagem.carregar(this, url)
            }
            .setNegativeButton("Cancelar") { _, _->

            }
            .show()
    }
}