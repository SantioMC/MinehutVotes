package me.santio.common.services;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A simple service for the conversion of ISO 8601 timestamps to Unix timestamps.
 */
public class EpochService {
    
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    
    /**
     * Converts an ISO 8601 timestamp to a Unix timestamp (in seconds).
     * @param iso8601 The ISO 8601 timestamp to convert. (ex: 2023-09-28T15:23:38.721360-06:00)
     * @return The Unix timestamp (in seconds).
     */
    public long asSeconds(CharSequence iso8601) {
        return OffsetDateTime.parse(iso8601, formatter).toEpochSecond();
    }
    
    /**
     * Converts an ISO 8601 timestamp to a Unix timestamp (in milliseconds).
     * This is the same as {@link #asSeconds(CharSequence)} * 1000 since we are only being provided with second precision.
     * @param iso8601 The ISO 8601 timestamp to convert. (ex: 2023-09-28T15:23:38.721360-06:00)
     * @return The Unix timestamp (in milliseconds).
     */
    public long asMilliseconds(CharSequence iso8601) {
        return this.asSeconds(iso8601) * 1000;
    }
    

}
