package be.rubus.microstream.performance.exposed

import org.jetbrains.exposed.sql.Database

object DBSettings {

    val db by lazy {
        Database.connect(
            "jdbc:postgresql://localhost:5432/postgres", driver = "org.postgresql.ds.PGSimpleDataSource",
            user = "postgres", password = "mysecretpassword"
        )
    }

    const val PAGE_SIZE: Int = 100
}