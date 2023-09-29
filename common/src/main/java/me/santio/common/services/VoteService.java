package me.santio.common.services;

import com.google.gson.Gson;
import me.santio.common.exceptions.InvalidMinehutEndpointException;
import me.santio.common.exceptions.InvalidMinehutTokenException;
import me.santio.common.exceptions.MinehutException;
import me.santio.common.exceptions.MismatchedMinehutTokenException;
import me.santio.common.models.Vote;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

/**
 * This is a simple service for contacting the Minehut API to check if a player has voted for a server.
 */
public class VoteService {
    
    private static final Gson GSON = new Gson();
    private static final String BASE_URL = "https://api.beta.minehut.com";
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
        switch (response.statusCode()) {
            case 400:
                throw new InvalidMinehutTokenException("The token used to contact the Minehut API is invalid or not supplied.");
            case 403:
                throw new MismatchedMinehutTokenException("The token used to contact the Minehut API isn't allowed to access the specific server");
            case 404:
                throw new InvalidMinehutEndpointException("The endpoint used to contact the Minehut API was invalid.");
            default:
                break;
        }
        
        return GSON.fromJson(response.body(), Vote.class);
    }
    
    /**
     * Sends a request to the Minehut API to check if a player has voted for a server.
     * @param client The HTTP client to use for the request.
     * @param server The server ID to check.
     * @param username The username to check.
     * @return A future that will be completed with the response.
     */
    @SuppressWarnings("TypeMayBeWeakened")
    public CompletableFuture<HttpResponse<String>> request(
        HttpClient client,
        String server,
        String username
    ) {
        final URI uri = URI.create(BASE_URL)
            .resolve(ENDPOINT
                .replace("<server>", server)
                .replace("<username>", username)
            );
        
        return client.sendAsync(
            HttpRequest.newBuilder()
                .uri(uri)
                .build(),
            
            HttpResponse.BodyHandlers.ofString()
        );
    }
    
    
    /**
     * Sends a batch request to the Minehut API to check if multiple players have for a server.
     * @param client The HTTP client to use for the request.
     * @param server The server ID to check.
     * @param usernames The usernames to check.
     * @return A future that will be completed with the response.
     */
    public CompletableFuture<HttpResponse<String>> requestBatch(
        HttpClient client,
        String server,
        String... usernames
    ) {
        return null;
    }
    
    
    
}
