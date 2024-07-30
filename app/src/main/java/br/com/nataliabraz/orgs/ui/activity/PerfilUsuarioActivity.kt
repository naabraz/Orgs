package br.com.nataliabraz.orgs.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import br.com.nataliabraz.orgs.R
import br.com.nataliabraz.orgs.databinding.ActivityPerfilUsuarioBinding
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class PerfilUsuarioActivity : UsuarioBaseActivity() {
    private val binding by lazy {
        ActivityPerfilUsuarioBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        preencheDados()
        Log.i("PerfilUsuarioActivity", "onCreate: ")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_perfil_usuario, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_perfil_usuario_logout -> {
                lifecycleScope.launch {
                    deslogaUsuario()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun preencheDados() {
        lifecycleScope.launch {
            usuario.filterNotNull().collect {
                    binding.usuarioId.text = it.id
                    binding.usuarioNome.text = it.nome
                }
        }
    }
}