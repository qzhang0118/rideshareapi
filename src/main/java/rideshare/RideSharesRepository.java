package rideshare;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 */
public interface RideSharesRepository extends MongoRepository<RideShares, String>, RideSharesRepositoryCustom {
}