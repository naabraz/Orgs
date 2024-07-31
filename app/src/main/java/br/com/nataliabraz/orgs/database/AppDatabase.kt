package br.com.nataliabraz.orgs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.nataliabraz.orgs.database.converter.Converters
import br.com.nataliabraz.orgs.database.dao.ProdutoDao
import br.com.nataliabraz.orgs.database.dao.UsuarioDao
import br.com.nataliabraz.orgs.database.migrations.MIGRATION_1_2
import br.com.nataliabraz.orgs.database.migrations.MIGRATION_2_3
import br.com.nataliabraz.orgs.model.Produto
import br.com.nataliabraz.orgs.model.Usuario

@Database(
    entities = [
        Produto::class,
        Usuario::class
    ],
    version = 3,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun produtoDao(): ProdutoDao
    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private lateinit var db: AppDatabase

        fun instancia(context: Context): AppDatabase {
            if (::db.isInitialized) return db

            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "orgs.db"
            ).addMigrations(
                MIGRATION_1_2,
                MIGRATION_2_3
            ).build().also {
                db = it
            }
        }
    }
}