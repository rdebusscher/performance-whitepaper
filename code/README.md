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

## Prepare database

## JDBC

## JOOQ

## Hibernate

# Compare Read performance

