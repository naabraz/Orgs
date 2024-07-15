package br.com.nataliabraz.orgs.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.nataliabraz.orgs.database.AppDatabase
import br.com.nataliabraz.orgs.database.dao.UsuarioDao
import br.com.nataliabraz.orgs.databinding.ActivityFormularioCadastroUsuarioBinding
import br.com.nataliabraz.orgs.model.Usuario
import kotlinx.coroutines.launch

class FormularioCadastroUsuarioActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormularioCadastroUsuarioBinding.inflate(layoutInflater)
    }

    private val usuarioDao: UsuarioDao by lazy {
        val db = AppDatabase.instancia(this)
        db.usuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoCadastrar()
    }

    private fun configuraBotaoCadastrar() {
        binding.activityFormularioCadastroBotaoCadastrar.setOnClickListener {
            val novoUsuario = criaUsuario()
            Log.i("CadastroUsuario", "onCreate: $novoUsuario")

            lifecycleScope.launch {
                try {
                    usuarioDao.salva(novoUsuario)
                    finish()
                } catch (e: Exception) {
                    Log.i("CadastroUsuario", "configuraBotaoCadastrar: ", e)
                    Toast.makeText(
                        this@FormularioCadastroUsuarioActivity,
                        "Falha ao cadastrar usuário",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun criaUsuario(): Usuario {
        val usuario = binding.activityFormularioCadastroUsuario.text.toString()
        val nome = binding.activityFormularioCadastroNome.text.toString()
        val senha = binding.activityFormularioCadastroSenha.text.toString()
        return Usuario(usuario, nome, senha)
    }
}