package me.santio.common.bucket;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Represents a bucket that can be used to limit the amount of actions that can be performed in a given time frame.
 * @param <T> The type of object that is stored in the bucket.
 */
@SuppressWarnings("WeakerAccess")
@Getter
public class Bucket<T> extends TimerTask {
    
    private final Duration time;
    private final int buffer;
    
    private Timer timer = null;
    private final ArrayList<T> items = new ArrayList<>();
    private final List<BucketTask<T>> listeners = new ArrayList<>();
    
    /**
     * Creates a new bucket with the given object and time.
     * @param buffer How many actions can be performed in the given time frame.
     * @param time The time between each action
     */
    public Bucket(int buffer, Duration time) {
        this.buffer = buffer;
        this.time = time;
    }
    
    /**
     * Creates a new builder for the bucket.
     * @return A new builder for the bucket.
     * @param <T> The type of object that is stored in the bucket.
     */
    public static <T> Builder<T> builder() {
        return new Builder<>();
    }
    
    /**
     * Start processing the bucket.
     * @return The bucket.
     */
    public Bucket<T> start() {
        if (this.timer != null) return this;
        timer = new Timer();
        timer.scheduleAtFixedRate(this, 0, time.toMillis());
        return this;
    }
    
    /**
     * Stop processing the bucket.
     * @return The bucket.
     */
    public Bucket<T> stop() {
        timer.cancel();
        return this;
    }
    
    /**
     * Adds an item to the bucket.
     * @param item The item to add to the bucket.
     * @return The bucket.
     */
    public Bucket<T> add(T item) {
        items.add(item);
        return this;
    }
    
    /**
     * Gets the next item in the bucket.
     * @return The next item in the bucket.
     */
    public T next() {
        return items.remove(0);
    }
    
    /**
     * Attaches a listener to the bucket.
     * @param listener The listener to attach.
     * @return The bucket.
     * @see BucketTask
     */
    public Bucket<T> listen(BucketTask<T> listener) {
        listeners.add(listener);
        return this;
    }
    
    private void handle(T item) {
        for (BucketTask<T> listener : listeners) {
            listener.run(item, items.isEmpty());
        }
    }
    
    @Override
    public void run() {
        for (int i = 0; i < buffer; i++) {
            if (items.isEmpty()) {
                this.stop();
                break;
            }
            
            this.handle(this.next());
        }
    }
    
    /**
     * A builder for the bucket.
     * @param <T> The type of object that is stored in the bucket.
     */
    @Getter
    @Setter
    @Accessors(chain = true, fluent = true)
    public static final class Builder<T> {
        private Builder() {}
        
        private int buffer = 1;
        private Duration delay = Duration.ofSeconds(1);
        private BucketTask<T> listener;
        
        /**
         * Builds the bucket.
         * @return The built bucket.
         */
        public Bucket<T> build() {
            final Bucket<T> bucket = new Bucket<>(buffer, delay);
            if (listener != null) bucket.listen(listener);
            return bucket;
        }
    }
    
}
