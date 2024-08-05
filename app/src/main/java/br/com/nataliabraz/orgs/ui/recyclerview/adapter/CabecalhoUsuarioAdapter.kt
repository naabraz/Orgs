package br.com.nataliabraz.orgs.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.nataliabraz.orgs.databinding.CabecalhoUsuarioBinding

class CabecalhoUsuarioAdapter(private val context: Context, usuario: List<String?> = emptyList()) :
    RecyclerView.Adapter<CabecalhoUsuarioAdapter.ViewHolder>() {

    private val usuarios: List<String?> = usuario.toMutableList()

    inner class ViewHolder(private val binding: CabecalhoUsuarioBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun vincula(usuario: String?) {
            binding.cabecalhoUsuarioId.text = usuario
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        CabecalhoUsuarioBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val usuario = if (usuarios[position].isNullOrBlank()) "Sem usuário"
        else usuarios[position]
        holder.vincula(usuario)
    }

    override fun getItemCount() = usuarios.size
}