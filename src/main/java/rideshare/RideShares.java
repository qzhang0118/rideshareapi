package rideshare;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 *
 */
public class RideShares {
    @Id
    public ObjectId id;

    public String userId;
    public String departure;
    public String destination;
    public String time;
    public int passengers;
    public String bookUserId;

    public RideShares(){}

    public RideShares(String userId, String departure, String destination, String time, int passengers) {
        this.userId = userId;
        this.departure = departure;
        this.destination = destination;
        this.time = time;
        this.passengers = passengers;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getDeparture() {
        return departure;
    }

    public String getDestination() {
        return destination;
    }

    public String getTime() {
        return time;
    }

    public int getPassengers() {
        return passengers;
    }

    public String getId() {
        return id.toString();
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setRideShare(String departure, String destination, String time, int passengers) {
        this.departure = departure;
        this.destination = destination;
        this.time = time;
        this.passengers = passengers;
    }

    public String toString() {
        return String.format("RideShares[id=%s, departure=%s, destination=%s, time=%s, passengers=%d]",
                id, departure, destination, time, passengers);
    }
}