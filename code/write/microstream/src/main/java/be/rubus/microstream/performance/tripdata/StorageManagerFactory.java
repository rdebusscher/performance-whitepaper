package be.rubus.microstream.performance.tripdata;

import one.microstream.afs.nio.types.NioFileSystem;
import one.microstream.storage.embedded.types.EmbeddedStorageFoundation;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import one.microstream.storage.types.Storage;
import one.microstream.storage.types.StorageChannelCountProvider;
import one.microstream.storage.types.StorageConfiguration;
import one.microstream.storage.types.StorageManager;

public final class StorageManagerFactory {

    private StorageManagerFactory() {
    }

    public static StorageManager create(String location, int channels, Object root) {
        NioFileSystem fileSystem = NioFileSystem.New();
        EmbeddedStorageManager storageManager = EmbeddedStorageFoundation.New()
                .setConfiguration(
                        StorageConfiguration.Builder()
                                .setStorageFileProvider(
                                        Storage.FileProviderBuilder(fileSystem)
                                                .setDirectory(fileSystem.ensureDirectoryPath(location))
                                                .createFileProvider()
                                )
                                .setChannelCountProvider(StorageChannelCountProvider.New(channels))
                                .createConfiguration()
                )
                .setRoot(root)
                .createEmbeddedStorageManager();

        // Start ir
        storageManager.start();

        storageManager.storeRoot();
        return storageManager;
    }
}
