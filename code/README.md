# Performance Comparison

Comparison between MicroStream and JDBC, JOOQ and Hibernate regarding reading and write data.

When comparing the performance, all processes are running on a single machine. In the case of the programs using JDBC,
JOOQ and Hibernate, the database is running within Docker using Docker desktop on the same machine.
The idea is that you have always the same amount of resources available. In the case you need a database, it is sharing
the sale CPU and memory as the main Java program.

# Compare writes

In the _write_ directory, you can find an example that uploads around 2,8 million or records. They represent taxi trip
details for a certain month. The end goal could be that you want to have some statistics and analysis on these data.

We use this large datasets so that we can have a comparison about the performance of storing data as storing only a few
records would not show the underlying differences clearly.

## Explore the data

The module _data_ within the write project can read the CSV file and hands over each record to a callback method. This
allows us to have a single program for all technologies.

:warning: Unzip the file within the directory _write/data/src/main/resources_ first.

First, run the program `LoadingSpeed`. It just reads the data but the call back method doesn't do anything. This will
give you an idea how much time the reading takes and can be subtracted to the total time required by each framework to
perform the task.

It also allows you to see if everything is working well.

The program `MemoryRequirements` load the data into a hashmap and create a Heap dump. This can be executed to get the
exact number of records within the dataset and to determine the memory size the data take into the JVM memory.

## MicroStream

The MicroStream solution can be found within the module _microstream_ and can be executed by the
program `PerformanceLoad`.

The Root object for MicroStream is a class (see `DataRoot`) that holds th data in a Map structure. The key is the day
number and the value of the map is a Lazy list of TripDetails for that day.

The call back method puts the data in the Map structure and after all data is loaded, each day is stored to the Binary
format.

The data is stored in the directory _user.home/microstream-performance/bydays_. Make sure you clear this directory
before you run the program again.

## Prepare database

The other frameworks all need a database, PostgresSQL is used in this example. The Docker container is started (and
first stopped) before each run of the performance comparison. This way, we make sure there are no effects left over from
a previous run (like deleted records that still take some space or any other interference that we could have from a
previous run)

```
docker run --name some-postgres -e POSTGRES_PASSWORD=mysecretpassword -d -p 5432:5432 postgres
```

After the container is running, we open a connection to the database to create the _trip_details_ table. The script can
be found in the file _database_sql.txt_.

## Hibernate

The first demo of storing the data in the PostgresSQL database is using Hibernate. We define an _entity_ class that
matches the table we have created and use a single transaction to store each record using the Hibernate Session.

The _entity_ class is defined within `TripDetailsEntity` and has just the `Entity`, `Table`, and `Id` annotation. Just
the minimal requirements.

The Hibernate Session is configured using the `hibernate.cfg.xml` file and instantiated through a `HibernateUtil` class
that provides a _SessionFactory_ that uses this file to connect to the database.
The configuration file contains the JDBC connection information and has th Entity class listed.

The callback for each recorded loaded calls the `Session.persist(Object)` method so that when all records are loaded
from the file, they can be written and flushed to the database when the transaction is committed.

During testing, also a _chunked_ approach was tested out where the commit is performed each 10000 records (also other
numbers were tested) but that resulted in a lower overall performance. This approach can be beneficial to have
incremental processing that can be restarted where the previous run stopped.

Within the configuration, a value for the _batch_size_ is configured so that insert statements are sent in bulk to the
database.

The test itself is implemented in the `PerformanceLoad` program.

## JDBC

In order to estimate to effect of the overhead of Hibernate on top f plain JDBC, a version of the upload is created
using plain JDBC. The callback inserts each record using a prepared statement and each 1000 records, the inserted
statements are sent in batch to the database. Transaction commit is only performed at the end of the program.

## JOOQ

The goal of the JOOQ framework is to keep the overhead on top of the JDBC as minimal as possible but have some type safe
ways to handle the data.

After you have generated the classes for your data (see program `GenerateCLass` and configuration file `jooq-config.xml`
but result already available within the project), you can insert, read and update from your table in a Java way using
objects and no longer rely on th JDBC strings.

The upload with JOOQ creates the JOOQ insert statement equivalent for each record and these are executed in batches of
5000. The commit is performed at the end of the upload process.

Although the performance should be better than with Hibernate, the tests shows that it is 3 times slower. So I assume,
this JOOQ example could be improved.

# Compare Read performance

