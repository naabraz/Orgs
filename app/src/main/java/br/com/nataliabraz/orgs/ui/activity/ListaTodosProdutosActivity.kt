package br.com.nataliabraz.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.nataliabraz.orgs.databinding.ActivityListaTodosProdutosBinding

class ListaTodosProdutosActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityListaTodosProdutosBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}