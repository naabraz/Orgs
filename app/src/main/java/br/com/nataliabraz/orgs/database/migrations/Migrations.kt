package br.com.nataliabraz.orgs.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""CREATE TABLE IF NOT EXISTS `Usuario` (
            `id` TEXT NOT NULL, 
            `nome` TEXT NOT NULL, 
            `senha` TEXT NOT NULL, 
            PRIMARY KEY(`id`)
         )""")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE Produto ADD COLUMN 'usuarioId' TEXT")
    }
}