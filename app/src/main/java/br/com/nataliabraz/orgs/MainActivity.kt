package br.com.nataliabraz.orgs

import android.app.Activity
import android.os.Bundle
import android.widget.Toast

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this, "Bem vinda(o) ao Orgs!", Toast.LENGTH_SHORT).show()
    }
}