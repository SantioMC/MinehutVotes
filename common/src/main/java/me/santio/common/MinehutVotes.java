package me.santio.common;

import lombok.SneakyThrows;
import lombok.val;
import me.santio.common.bucket.Bucket;
import me.santio.common.models.Vote;
import me.santio.common.services.VoteService;

import java.net.http.HttpClient;
import java.time.Duration;


@SuppressWarnings("MissingJavadoc")
public class MinehutVotes {
    
    private static final HttpClient client = HttpClient.newHttpClient();
    
    @SneakyThrows
    public static void main(String[] args) {
        // TODO: Remove code used for testing
        Vote vote = new Vote(
            "success",
            "2023-09-28T15:23:38.721360-06:00"
        );
        
        val service = new VoteService();
        
        val res = service.request(client, "698cb96d-5bf2-42ad-a82c-0f638637737a", "Santio71").get();
        System.out.println("Response received");
        System.out.println(res.statusCode());
        System.out.println(res.body());
    }

}