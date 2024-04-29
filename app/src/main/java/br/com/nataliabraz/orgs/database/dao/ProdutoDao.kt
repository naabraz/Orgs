package br.com.nataliabraz.orgs.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.nataliabraz.orgs.model.Produto

@Dao
interface ProdutoDao {
    @Query("SELECT * FROM Produto")
    fun buscaTodos(): List<Produto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun salva(vararg produto: Produto)

    @Delete
    fun remove(produto: Produto)

    @Query("SELECT * FROM Produto WHERE id = :id")
    fun buscaPorId(id: Long) : Produto?

    @Query("SELECT * FROM Produto ORDER BY nome ASC")
    fun ordenaPorNomeAsc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY nome DESC")
    fun ordenaPorNomeDesc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY descricao ASC")
    fun ordenaPorDescricaoAsc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY descricao DESC")
    fun ordenaPorDescricaoDesc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY valor ASC")
    fun ordenaPorValorAsc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY valor DESC")
    fun ordenaPorValorDesc(): List<Produto>
}