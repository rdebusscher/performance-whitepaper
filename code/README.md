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

The upload with JOOQ creates the JOOQ insert statement equivalent for each record and these are executed in batches of 5000. The commit is performed at the end of the upload process.

Although the performance should be better than with Hibernate, the tests shows that it is 3 times slower. So I assume,
this JOOQ example could be improved.

# Compare Read performance

For the comparison of the Query performance, we also generate test data.  The data represent a Book Store. It has shops in different countries, tracks the stock and keeps purchases by customers.

The code within the directory/module _generator_ creates the (realistic) test data using Java Faker library. You should first create data in the MicroStream format and use this to upload it in the PostgreSQL database (using Hibernate)  
This to make sure all read performance tests later on make use of exact the same data.

This generation of data is not part of the test itself.

## Book store data

Have a look at the white paper PDF as it contains a database schema if you like to explore the schema more in detail.

The program can generate 3 'sizes' of data (Medium, Large and Huge). Have a look at the `GenerateData` program within the _read/generator_ module.

If you want to (re-) generate the datasets, you can follow the following steps.

- Make sure the _bookstore_ and _lucene-data_ directories are removed. They contain the MicroStream and Lucene index data from a previous run.
- Run the program `GenerateData` to have the data created and stored in the MicroStream format.
- Start the PostgreSQL server, we make again use of the Docker container  
```
docker run --name some-postgres -e POSTGRES_PASSWORD=mysecretpassword -d -p 5432:5432 postgres
```
- Create the database tables by executing the program `SchemaCreationDatabase`.  It reads the sql file _create_database.sql_ that (re-)creates all tables and indexes.  
- Run the program `UploadIntoDatabase` to load the data into the database (this can take a while)

## Some general aspects

A few remarks about the code and why some additional 'actions' are coded.  They allow to have 'similar' data with each of the frameworks.

- For MicroStream, we compare the time to read the data from the storage (when _StorageManager_ is created) with the time to create connection and initialize Hibernate when using the database.
- Since MicroStream has always all referential data available (like _Customers_ and _Shops_ always have the _address_), Hibernate example has additional _JOIN FETCH_ statements and JDBC SQL additional joins, to retrieve all data. But we do not load all data from the database of course.
- Since MicroStream and Hibernate return the results as 'objects', a small and simple mapping framework is created. It converts fields from the _ResultSet_ into objects and make sure that a record is only represented by a single object. For example, the same `Country` record is created as a single object and referenced from the different `State` objects that belong to the same _country_.  Have a look at the `be.rubus.microstream.performance.jdbc.query` and sub-package `framework` classes.  

## Use cases

For the moment, 2 use cases are already implemented and ready. A simple one reading just some data from a 'table' and a more complex one with many joins and many records.

### Read customers

In this example, we read data from the Customers 'table' in a paginated form. The test performs 3 queries where we always read 100 records. The first query reads the first 100, the second query the next 100, and so on.

For the code, have a look at the class `AllCustomersPaged` that perform the test.

- Package _be.rubus.microstream.performance.microstream.tests_  for MicroStream (module read/microstream).
- Package _be.rubus.microstream.performance.jdbc.tests_  for JDBC (module read/jdbc).
- Package _be.rubus.microstream.performance.hibernate.tests_  for Hibernate (module read/hibernate).

### Purchases by 'foreigners'

In this more complex query, we retrieve all purchases made by customers that are not living in the same city as the shop. We perform this action 9 times (for 3 random countries and 3 random years)

Some remarks

- The JDBC query doesn't retrieve the purchase details (items) as that would complicate the single query too much. So there should be another query executed to retrieve them (it can be a single query if we use a large _in ()_ query condition)
- Since _purchase_ data is Lazy loaded in the MicroStream case, the first query loads the data for the 3 years in question. 

The example also performs the queries for a second time, using the same countries and years.  This to have the query performance when your application is running (no more overhead of startup, only query time is taken).

For the code, have a look at the class `PurchaseOfForeigners` that perform the test.

- Package _be.rubus.microstream.performance.microstream.tests_  for MicroStream (module read/microstream).
- Package _be.rubus.microstream.performance.jdbc.tests_  for JDBC (module read/jdbc).
- Package _be.rubus.microstream.performance.hibernate.tests_  for Hibernate (module read/hibernate).
