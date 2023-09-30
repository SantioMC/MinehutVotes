package me.santio.common;

import kong.unirest.*;
import me.santio.common.common.BuildConfig;
import me.santio.common.services.VoteService;

/**
 * A utility class for interacting with the Minehut Votes API.
 */
@SuppressWarnings("WeakerAccess")
public final class MinehutVotes {
    private MinehutVotes() {}
    
    private static final String INFERRED_SERVER_ID = System.getenv("SERVER_ID");
    
    /**
     * The base URL for the Minehut API.
     */
    public static final String BASE_URL = "https://api.beta.minehut.com";
    
    private static final UnirestInstance client = new UnirestInstance(
        new Config().connectTimeout(5000)
            .setDefaultHeader("Accept", "application/json")
            .setDefaultHeader("Content-Type", "application/json")
            .setDefaultHeader("User-Agent", "MinehutVotes/" + BuildConfig.PLUGIN_VERSION)
            .defaultBaseUrl(envOr("MINEHUT_VOTES_API", BASE_URL))
    );
    
    private static String envOr(String key, String fallback) {
        final String value = System.getenv(key);
        return value == null ? fallback : value;
    }
    
    /**
     * Closes any resources used by the Minehut Votes client.
     */
    public static void close() {
        client.shutDown();
    }
    
    /**
     * Authenticate the Minehut Votes client, allowing it to make authenticated requests to the API.
     * You will receive a {@link me.santio.common.exceptions.InvalidMinehutTokenException} if you don't run this method.
     * @param server The Server ID for the server you want to check votes for.
     * @param token The token to authenticate with.
     * @return A {@link VoteService} instance for the server.
     */
    public static VoteService authenticate(String server, String token) {
        final String serverId = server.trim().toLowerCase().startsWith("auto")
            ? INFERRED_SERVER_ID
            : server.trim().toLowerCase();
        
        client.config().setDefaultHeader("Authorization", "Bearer " + token);
        return new VoteService(serverId, client);
    }

}