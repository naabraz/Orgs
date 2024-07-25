package br.com.nataliabraz.orgs.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.nataliabraz.orgs.database.AppDatabase
import br.com.nataliabraz.orgs.database.dao.UsuarioDao
import br.com.nataliabraz.orgs.databinding.ActivityFormularioCadastroUsuarioBinding
import br.com.nataliabraz.orgs.extensions.toHash
import br.com.nataliabraz.orgs.extensions.toast
import br.com.nataliabraz.orgs.model.Usuario
import kotlinx.coroutines.launch

class FormularioCadastroUsuarioActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormularioCadastroUsuarioBinding.inflate(layoutInflater)
    }

    private val dao: UsuarioDao by lazy {
        AppDatabase.instancia(this).usuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoCadastrar()
    }

    private fun configuraBotaoCadastrar() {
        binding.activityFormularioCadastroBotaoCadastrar.setOnClickListener {
            val novoUsuario = criaUsuario()
            cadastra(novoUsuario)
        }
    }

    private fun cadastra(usuario: Usuario) {
        lifecycleScope.launch {
            try {
                dao.salva(usuario)
                finish()
            } catch (e: Exception) {
                Log.i("CadastroUsuario", "configuraBotaoCadastrar: ", e)
                toast("Falha ao cadastrar usuário")
            }
        }
    }

    private fun criaUsuario(): Usuario {
        val usuario = binding.activityFormularioCadastroUsuario.text.toString()
        val nome = binding.activityFormularioCadastroNome.text.toString()
        val senha = binding.activityFormularioCadastroSenha.text.toString().toHash()
        return Usuario(usuario, nome, senha)
    }
}