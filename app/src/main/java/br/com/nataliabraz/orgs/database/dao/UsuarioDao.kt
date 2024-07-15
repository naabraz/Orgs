package br.com.nataliabraz.orgs.database.dao

import androidx.room.Dao
import androidx.room.Insert
import br.com.nataliabraz.orgs.model.Usuario

@Dao
interface UsuarioDao {
    @Insert
    suspend fun salva(usuario: Usuario)
}