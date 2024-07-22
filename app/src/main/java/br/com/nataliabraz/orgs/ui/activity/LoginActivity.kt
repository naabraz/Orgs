package br.com.nataliabraz.orgs.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import br.com.nataliabraz.orgs.database.AppDatabase
import br.com.nataliabraz.orgs.database.dao.UsuarioDao
import br.com.nataliabraz.orgs.databinding.ActivityLoginBinding
import br.com.nataliabraz.orgs.extensions.toHash
import br.com.nataliabraz.orgs.extensions.vaiPara
import br.com.nataliabraz.orgs.preferences.USUARIO_LOGADO_PREFERENCES
import br.com.nataliabraz.orgs.preferences.dataStore
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val usuarioDao: UsuarioDao by lazy {
        AppDatabase.instancia(this).usuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoEntrar()
        configuraBotaoCadastrar()
    }

    private fun configuraBotaoEntrar() {
        binding.activityLoginBotaoEntrar.setOnClickListener {
            val usuario = binding.activityLoginUsuario.text.toString()
            val senha = binding.activityLoginSenha.text.toString().toHash()

            Log.i("LoginActivity", "onCreate: $usuario - $senha")

            lifecycleScope.launch {
                usuarioDao.autentica(usuario, senha)?.let { usuario ->
                    dataStore.edit { preferences ->
                        preferences[USUARIO_LOGADO_PREFERENCES] = usuario.id
                    }

                    vaiPara(ListaProdutosActivity::class.java)
                } ?: Toast.makeText(
                    this@LoginActivity,
                    "Falha na autenticação",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun configuraBotaoCadastrar() {
        binding.activityLoginBotaoCadastrar.setOnClickListener {
            vaiPara(FormularioCadastroUsuarioActivity::class.java)
        }
    }
}