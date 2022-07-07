package it.gov.pagopa.config;

//import com.mongodb.Mongo;
//import com.mongodb.reactivestreams.client.MongoClient;
//import cz.jirutka.spring.embedmongo.EmbeddedMongoBuilder;
//import de.flapdoodle.embed.mongo.MongodExecutable;
//import de.flapdoodle.embed.mongo.MongodProcess;
//import de.flapdoodle.embed.mongo.MongodStarter;
//import de.flapdoodle.embed.mongo.config.Net;
//import de.flapdoodle.embed.mongo.distribution.Version;
import it.gov.pagopa.model.converter.StringToModeEnumConverter;
import it.gov.pagopa.model.converter.ModeEnumToStringConverter;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;

@Configuration
public class MongoConfig {

//    @Getter
//    @Value("${data.version:3.6.1}")
//    private String version;
//
//    @Getter
//    @Value("${data.host:127.0.0.1}")
//    private String host;
//
//    @Getter
//    @Value("${data.port:12345}")
//    private int port;
//
//    @Value("${one.spring.data.mongodb.uri}")
//    private String dbOne;

    @Bean
    public MongoCustomConversions mongoCustomConversions() {

        return new MongoCustomConversions(
                Arrays.asList(
                        new ModeEnumToStringConverter(),
                        new StringToModeEnumConverter()));
    }

//    private static final String MONGO_DB_URL = "localhost";
//    private static final int MONGO_DB_PORT = 12345;
//
//    MongodStarter starter = MongodStarter.getDefaultInstance();
//    MongodExecutable mongodExecutable;
//
//    @Bean
//    public MongoDbFactory mongoDbFactory() throws UnknownHostException {
//        Mongo mongo = new MongoClient(MONGO_DB_URL, MONGO_DB_PORT);
//        return new SimpleMongoClientDatabaseFactory(mongo, "store");
//    }
//
//    @Bean
//    public MongoTemplate mongoTemplate() throws UnknownHostException {
//        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
//        return mongoTemplate;
//    }
//
//    @PostConstruct
//    public void construct() throws UnknownHostException, IOException {
//        IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION).net(new Net(MONGO_DB_URL, MONGO_DB_PORT, true)).build();
//        mongodExecutable = starter.prepare(mongodConfig);
//        MongodProcess mongod = mongodExecutable.start();
//    }
//
//    @PreDestroy
//    public void destroy() {
//        if (mongodExecutable != null) {
//            mongodExecutable.stop();
//        }
//    }

//    private static final String MONGO_DB_URL = "localhost";
//    private static final String MONGO_DB_NAME = "embeded_db";
//
//    @Bean
//    public MongoTemplate mongoTemplate() throws IOException {
//
//        EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
//        mongo.setBindIp(MONGO_DB_URL);
//        MongoClient mongoClient = mongo.getObject();
//        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, MONGO_DB_NAME);
//        return mongoTemplate;
//
//    }
//
//    @Bean(destroyMethod = "close")
//    public Mongo mongo() throws IOException {
//        return new EmbeddedMongoBuilder()
//                .version(version)
//                .bindIp(host)
//                .port(port)
//                .build();
//    }

//    @Bean
////    @Qualifier("one")
//    public ModelMongoRepository modelMongoRepositoryOne() throws DataAccessException, Exception {
//        MongoRepositoryFactoryBean<ModelMongoRepository, Model, String> myFactory = new MongoRepositoryFactoryBean<ModelMongoRepository, Model, String>();
//        myFactory.setRepositoryInterface(ModelMongoRepository.class);
//        myFactory.setMongoOperations(createMongoOperations(dbOne));
//        myFactory.afterPropertiesSet();
//        return myFactory.getObject();
//    }
//
//    private MongoOperations createMongoOperations(String dbConnection) throws DataAccessException, Exception {
//        MongoClientURI mongoClientURI = new MongoClientURI(dbConnection);
//        MongoClient mongoClient = new MongoClient(mongoClientURI);
//        Mongo mongo = new SimpleMongoDbFactory(mongoClient, mongoClientURI.getDatabase()).getDb().getMongo();
//        return new MongoTemplate(mongo, mongoClientURI.getDatabase());
//    }
}