package me.santio.common.services;

import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.UnirestInstance;
import me.santio.common.exceptions.InvalidMinehutEndpointException;
import me.santio.common.exceptions.InvalidMinehutTokenException;
import me.santio.common.exceptions.MinehutException;
import me.santio.common.exceptions.MismatchedMinehutTokenException;
import me.santio.common.models.Vote;

import java.util.concurrent.CompletableFuture;

/**
 * This is a simple service for contacting the Minehut API to check if a player has voted for a server.
 */
public class VoteService {
    
    private final String serverId;
    private final UnirestInstance client;
    
    /**
     * Creates a new VoteService instance.
     * @param serverId The server ID to check votes for.
     * @param client The HTTP client to use for requests.
     */
    public VoteService(String serverId, UnirestInstance client) {
        this.serverId = serverId;
        this.client = client;
    }
    
    private static final Gson GSON = new Gson();
    private static final String ENDPOINT = "/api/v1/servers/<server>/last-vote/<username>";
    
    /**
     * Validates a response sent from the Minehut API by checking the status code, if the response is invalid
     * then an exception will be thrown, else you will get a vote object.
     * @param response The response to validate.
     * @return The vote object if the response is valid.
     * @throws MinehutException If the response is invalid.
     * @see InvalidMinehutTokenException
     * @see MismatchedMinehutTokenException
     * @see InvalidMinehutEndpointException
     */
    public Vote validateResponse(HttpResponse<String> response) {
        switch (response.getStatus()) {
            case 401:
                throw new InvalidMinehutTokenException("The token used to contact the Minehut API is invalid or not supplied.");
            case 403:
                throw new MismatchedMinehutTokenException("The token used to contact the Minehut API isn't allowed to access the specific server");
            case 404:
                throw new InvalidMinehutEndpointException("The endpoint used to contact the Minehut API was invalid.");
            default:
                break;
        }
        
        return GSON.fromJson(response.getBody(), Vote.class);
    }
    
    /**
     * Sends a request to the Minehut API to check if a player has voted for a server.
     * @param username The username to check.
     * @return A future that will be completed with the response.
     */
    @SuppressWarnings("TypeMayBeWeakened")
    public CompletableFuture<HttpResponse<String>> request(
        String username
    ) {
        final String uri = ENDPOINT
            .replace("<server>", serverId)
            .replace("<username>", username);
        
        return client.get(uri).asStringAsync();
    }
    
    
    /**
     * Sends a batch request to the Minehut API to check if multiple players have for a server.
     * @param usernames The usernames to check.
     * @return A future that will be completed with the response.
     */
    public CompletableFuture<HttpResponse<String>> requestBatch(
        String... usernames
    ) {
        return null;
    }
    
    
    
}
