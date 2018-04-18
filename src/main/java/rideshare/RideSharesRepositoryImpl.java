package rideshare;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 *
 */
public class RideSharesRepositoryImpl implements RideSharesRepositoryCustom {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public int update(String id, String fieldToChange, String fieldValue) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update();
        if (fieldToChange.equals("passengers")) {
            update.set(fieldToChange, Integer.valueOf(fieldValue));
        } else {
            update.set(fieldToChange, fieldValue);
        }
        WriteResult result = mongoTemplate.updateFirst(query, update, RideShares.class);
        return result == null ? 0 : result.getN();
    }

    @Override
    public List<RideShares> search(String departure, String destination, String time, int passengers) {
        Query query = new Query(new Criteria().andOperator(
                Criteria.where("departure").is(departure),
                Criteria.where("destination").is(destination),
                Criteria.where("time").is(time),
                Criteria.where("passengers").gte(passengers)));
        return mongoTemplate.find(query, RideShares.class);
    }
}