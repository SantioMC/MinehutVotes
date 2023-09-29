package me.santio.common.models;

import com.google.gson.annotations.SerializedName;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Represents a vote from the Minehut API.
 */
@Value
@Accessors(fluent = true, chain = true)
public class Vote {
    
    String status;
    
    @SerializedName("time_of_last_vote")
    String lastVote;

}
