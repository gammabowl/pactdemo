package pact;

import java.util.logging.Level;
import java.util.logging.Logger;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;
import au.com.dius.pact.consumer.ConsumerPactTest;
import java.util.Map;
import java.util.HashMap;
import au.com.dius.pact.consumer.PactProviderRule;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import org.junit.Rule;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import org.junit.Assert;


public class PactDslJsonTest extends ConsumerPactTest {

    Logger logger = Logger.getLogger(PactDslJsonTest.class.getName());

    @Rule
    public PactProviderRule mockProvider = new PactProviderRule("hydra_provider", "localhost", 1234, this);

    String v3Path = "/v3";
    private DslPart body = new PactDslJsonBody()
            .decimalType("dbQueryTime", 0.003489971160888672)
            .decimalType("redisQueryTime", 0.0006480216979980469)
            .stringType("status", "api is up.")
            .decimalType("totalTime", 0.005939006805419922);

    protected PactFragment createFragment(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");

        PactFragment fragment = builder
                .uponReceiving("API v3 endpoint response")
                .path(v3Path)
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(body)
                .toFragment();
        return fragment;
    }

    @Override
    protected String providerName() {
        return "hydra_provider";
    }

    @Override
    protected String consumerName() {
        return "cpt_consumer";
    }

    @Override
    protected void runTest(String url) {
        Map actualResponse;
        try {
            actualResponse = new ConsumerClient(url).getAsMap(v3Path, "");
            Map expectedResponse = new HashMap();
            expectedResponse.put("dbQueryTime", 0.003489971160888672);
            expectedResponse.put("redisQueryTime", 0.0006480216979980469);
            expectedResponse.put("status", "api is up.");
            expectedResponse.put("totalTime", 0.005939006805419922);
            logger.info(expectedResponse.toString());
            logger.info(actualResponse.toString());
            Assert.assertEquals(expectedResponse.toString(), actualResponse.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
