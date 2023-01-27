package it.gov.pagopa;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.process.runtime.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.ReflectionUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.net.UnknownHostException;
import java.util.Objects;

@SpringBootTest
@TestPropertySource(
        properties = {
                //region mongodb
                "logging.level.org.mongodb.driver=WARN",
                "logging.level.org.springframework.boot.autoconfigure.mongo.embedded=WARN",
                "spring.mongodb.embedded.version=4.0.21",
                //endregion

                //region wiremock
                "logging.level.WireMock=OFF",
                "onetrust.privacy-notices.client.url=http://localhost:${wiremock.server.port}",
                //endregion
        })
@AutoConfigureDataMongo
@AutoConfigureMockMvc
@AutoConfigureWireMock(stubs = "classpath:/stub/onetrust", port = 0)
public abstract class BaseIntegrationTest {

    @Autowired(required = false)
    private MongodExecutable embeddedMongoServer;

    @Value("${spring.data.mongodb.uri}")
    private String mongodbUri;

    @Autowired
    private WireMockServer wireMockServer;

    @PostConstruct
    public void logEmbeddedServerConfig() throws NoSuchFieldException, UnknownHostException {
        String mongoUrl;
        if (embeddedMongoServer != null) {
            Field mongoEmbeddedServerConfigField = Executable.class.getDeclaredField("config");
            mongoEmbeddedServerConfigField.setAccessible(true);
            MongodConfig mongodConfig = (MongodConfig) ReflectionUtils.getField(mongoEmbeddedServerConfigField, embeddedMongoServer);
            Net mongodNet = Objects.requireNonNull(mongodConfig).net();

            mongoUrl = "mongodb://%s:%s".formatted(mongodNet.getServerAddress().getHostAddress(), mongodNet.getPort());
        } else {
            mongoUrl = mongodbUri.replaceFirst(":[^:]+(?=:[0-9]+)", "");
        }
        System.out.printf("""
                        ************************
                        Embedded mongo: %s
                        Wiremock HTTP: http://localhost:%s
                        Wiremock HTTPS: %s
                        ************************
                        """,
                mongoUrl,
                wireMockServer.getOptions().portNumber(),
                wireMockServer.baseUrl());
    }
}
