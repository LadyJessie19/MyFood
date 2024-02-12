import org.springframework.data.mongodb.repository.MongoRepository;

public interface RankingRepository extends MongoRepository<User, String> {}
