package rideshare;

/**
 *
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@EnableWebMvc
@RestController
public class RideSharesController {
    @Autowired
    private RideSharesRepository rideSharesRepository;

    @RequestMapping(value="/rideshares", method = RequestMethod.POST)
    public RideShares create(@RequestParam(value="userId") String userId,
                             @RequestParam(value="departure") String departure,
                             @RequestParam(value="destination") String destination,
                             @RequestParam(value="time") String time,
                             @RequestParam(value="passengers") Integer passengers) {
        return rideSharesRepository.insert(new RideShares(userId, departure, destination, time, passengers));
    }

    @RequestMapping(value="/rideshares", method = RequestMethod.GET)
    public List<RideShares> search(@RequestParam(value="departure") String departure,
                                                @RequestParam(value="destination") String destination,
                                                @RequestParam(value="time") String time,
                                                @RequestParam(value="passengers") Integer passengers) {
        return rideSharesRepository.search(departure, destination, time, passengers);
    }

    @RequestMapping(value="/rideshares", method = RequestMethod.PUT)
    public RideShares update(@RequestParam(value="id") String id,
                       @RequestParam(value="departure") String departure,
                       @RequestParam(value="destination") String destination,
                       @RequestParam(value="time") String time,
                       @RequestParam(value="passengers") Integer passengers) {
        rideSharesRepository.update(id, "departure", departure);
        rideSharesRepository.update(id, "destination", destination);
        rideSharesRepository.update(id, "time", time);
        rideSharesRepository.update(id, "passengers", passengers.toString());
        return rideSharesRepository.findOne(id);
    }

    @RequestMapping(value="/rideshares", method = RequestMethod.DELETE)
    public RideShares delete(@RequestParam(value="id") String id) {
        RideShares toBeDeletedRideshares = rideSharesRepository.findOne(id);
        rideSharesRepository.delete(id);
        return toBeDeletedRideshares;
    }

    @RequestMapping(value="/rideshares/searchAvailableRide", method = RequestMethod.GET)
    @ResponseBody
    public List<RideShares> searchAvailableRide() {
        List<RideShares> availableRides = new ArrayList<RideShares>();
        List<RideShares> rideshares = rideSharesRepository.findAll();
        for (RideShares rideshare : rideshares) {
            if (rideshare.bookUserId == null) {
                availableRides.add(rideshare);
            }
        }
        return availableRides;
    }

    @RequestMapping(value="/rideshares/searchMyBookedRide", method = RequestMethod.GET)
    public List<RideShares> searchMyBookedRide(@RequestParam(value="userId") String bookUserId) {
        List<RideShares> userBookedRides = new ArrayList<RideShares>();
        List<RideShares> rideshares = rideSharesRepository.findAll();
        for (RideShares rideshare : rideshares) {
            if (rideshare.bookUserId != null && rideshare.bookUserId.equals(bookUserId)) {
                userBookedRides.add(rideshare);
            }
        }
        return userBookedRides;
    }

    @RequestMapping(value="/rideshares/searchMyOfferedRide", method = RequestMethod.GET)
    public List<RideShares> searchMyOfferedRide(@RequestParam(value="userId") String userId) {
        List<RideShares> userBookedRides = new ArrayList<RideShares>();
        List<RideShares> rideshares = rideSharesRepository.findAll();
        for (RideShares rideshare : rideshares) {
            if (rideshare.userId.equals(userId)) {
                userBookedRides.add(rideshare);
            }
        }
        return userBookedRides;
    }

    @RequestMapping(value="/rideshares/bookRide", method = RequestMethod.POST)
    public String bookRide(@RequestParam(value="id") String id,
                           @RequestParam(value="userId") String bookUserId) {
        RideShares bookedRide = rideSharesRepository.findOne(id);
        bookedRide.bookUserId = bookUserId;
        rideSharesRepository.save(bookedRide);
        return id;
    }
}