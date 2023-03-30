package be.rubus.microstream.performance.write

import org.jetbrains.exposed.sql.Database

object DBSettings {

    val db by lazy {
        Database.connect(
            "jdbc:postgresql://localhost:5432/postgres?rewriteBatchedInserts=true", driver = "org.postgresql.ds.PGSimpleDataSource",
            user = "postgres", password = "mysecretpassword"
        )
    }
}