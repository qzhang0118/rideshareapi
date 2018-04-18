package rideshare;

import java.util.List;

/**
 *
 */
public interface RideSharesRepositoryCustom {
    int update(String id, String fieldToChange, String fieldValue);
    List<RideShares> search(String departure, String destination, String time, int passengers);
}