package rideshare;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

/**
 *
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private RideSharesRepository rideSharesRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        MongoOperations mongoOperations =
                new MongoTemplate(new SimpleMongoDbFactory(new MongoClient(), "RideSharesDb"));
    }
}