package be.rubus.microstream.performance.microstream.database;

import one.microstream.exceptions.IORuntimeException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.util.QueryBuilder;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Lucene based full text search index for Java objects.
 * <p>
 * All operations on this type are thread safe.
 *
 * @param <T> the object type
 */
public class Index<T> implements Closeable {
    /**
     * External handler to populate index documents based on Java objects
     *
     * @param <T> the object type
     */
    public interface DocumentPopulator<T> extends BiConsumer<Document, T> {
        // simple typing interface
    }

    /**
     * External handler which matches Java objects to index documents.
     *
     * @param <T> the object type
     */
    public interface EntityMatcher<T> extends Function<Document, T> {
        // simple typing interface
    }


    private final Class<T> entityType;
    private final DocumentPopulator<T> documentPopulator;
    private final EntityMatcher<T> entityMatcher;
    private MMapDirectory directory;
    private IndexWriter writer;
    private DirectoryReader reader;
    private IndexSearcher searcher;

    /**
     * Constructor to create a new {@link Index}.
     *
     * @param <T>               the object type
     * @param entityType        not <code>null</code>
     * @param documentPopulator not <code>null</code>
     * @param entityMatcher     not <code>null</code>
     */
    public Index(Class<T> entityType, DocumentPopulator<T> documentPopulator, EntityMatcher<T> entityMatcher) {
        this.entityType = Objects.requireNonNull(entityType, () -> "EntityType cannot be null");
        this.documentPopulator = Objects.requireNonNull(documentPopulator, () -> "DocumentPopulator cannot be null");
        this.entityMatcher = Objects.requireNonNull(entityMatcher, () -> "EntityMatcher cannot be null");
    }

    /**
     * Adds an Java object to this index
     *
     * @param entity the java object to add
     */
    public synchronized void add(T entity) {
        this.lazyInit();

        try {
            Document document = new Document();
            this.documentPopulator.accept(document, entity);
            this.writer.addDocument(document);
            this.writer.flush();
            this.writer.commit();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Adds Java objects to this index, in a bulk operation.
     *
     * @param entities the java objects to add
     */
    public synchronized void addAll(Collection<? extends T> entities) {
        this.lazyInit();

        try {
            for (T entity : entities) {
                Document document = new Document();
                this.documentPopulator.accept(document, entity);
                this.writer.addDocument(document);
            }

            writer.flush();
            writer.commit();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }


    /**
     * Removes all entries from this index.
     */
    public synchronized void clear() {
        lazyInit();

        try {
            writer.deleteAll();
            writer.flush();
            writer.commit();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Queries this index.
     *
     * @param query      the search query
     * @param maxResults maximum number of results
     * @return the list of found objects
     */
    public synchronized List<T> search(Query query, int maxResults) {
        lazyInit();

        try {
            TopDocs topDocs = this.searcher.search(query, maxResults);
            List<T> result = new ArrayList<>(topDocs.scoreDocs.length);
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                Document document = this.searcher.doc(scoreDoc.doc);
                T entity = this.entityMatcher.apply(document);
                if (entity != null) {
                    result.add(entity);
                }
            }
            return result;
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Creates a new Lucene query builder.
     *
     * @return a new query builder.
     */
    public synchronized QueryBuilder createQueryBuilder() {
        this.lazyInit();

        return new QueryBuilder(this.writer.getAnalyzer());
    }

    /**
     * Get the amount of entries in this index.
     *
     * @return amount of entries
     */
    public synchronized int size() {
        this.lazyInit();

        return searcher.getIndexReader().numDocs();
    }

    private void lazyInit() {
        try {
            if (directory == null) {
                Path path = Paths.get("lucene-data", "index", this.entityType.getSimpleName());
                directory = new MMapDirectory(path);
                writer = new IndexWriter(directory, new IndexWriterConfig(new StandardAnalyzer()));
                searcher = new IndexSearcher(reader = DirectoryReader.open(this.writer));
            } else {
                DirectoryReader newReader = DirectoryReader.openIfChanged(this.reader);
                if (newReader != null && newReader != reader) {
                    reader.close();
                    reader = newReader;
                    searcher = new IndexSearcher(reader);
                }
            }
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    @Override
    public synchronized void close() throws IOException {
        if (this.directory != null) {
            this.writer.close();
            this.reader.close();
            this.directory.close();

            this.directory = null;
            this.writer = null;
            this.reader = null;
            this.searcher = null;
        }
    }

}
