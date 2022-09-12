package be.rubus.microstream.performance.concurrent;

/**
 * Operation with no return value, used by {@link ReadWriteLocked} .
 */
@FunctionalInterface
public interface VoidOperation {
    /**
     * Execute an arbitrary operation
     */
    void execute();
}