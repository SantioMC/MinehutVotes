package me.santio.common.bucket;

/**
 * Represents a task that can be run by a bucket.
 * @param <T> The type of object that is stored in the bucket.
 */
@FunctionalInterface
public interface BucketTask<T> {
    
    /**
     * The task to be ran by the bucket.
     * @param item The item to run the task on.
     */
    void run(T item, boolean done);
    
}
