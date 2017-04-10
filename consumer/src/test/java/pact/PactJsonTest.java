package pact;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
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
import static java.lang.System.out;
import org.junit.Assert;

public class PactJsonTest extends ConsumerPactTest {

    //private static Logger logger = LogManager.getLogger();
    Logger logger = Logger.getLogger(PactTest.class.getName());

    @Rule
    public PactProviderRule mockProvider = new PactProviderRule("test_provider", "localhost", 1234, this);

    String v3SearchId = "/v3/searches/37ca5b7c-2f31-530c-9d08-bc334cb434fd";
    String v3JobId = "/v3/searches/37ca5b7c-2f31-530c-9d08-bc334cb434fd/jobs/83560b80-0d31-11e7-88df-0242ac110003";

    protected PactFragment createFragment(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        String jsonSearchBody = "{\"active\": true, \"activeDailySearchSetID\": null, \"advancedQuery\": null, \"created\": \"2013-12-17T03:14:33.611223\", \"criteria\": \"[[\\\"any\\\", \\\"included\\\", \\\"proactiv\\\"]]\", \"customSources\": null, \"forkedFrom\": null, \"freebaseTopicIds\": [], \"ftsQuery\": \"((\\\"proactiv\\\"))\", \"id\": \"37ca5b7c-2f31-530c-9d08-bc334cb434fd\", \"language\": \"english\", \"maxDepth\": 25, \"name\": \"Proactiv\", \"priorityId\": 1, \"runDaily\": false, \"runawayThreshold\": 50000, \"searchTerms\": [\"proactiv\", \"proactiv commercial\", \"skin care\", \"acne wash\", \"face wash\"], \"searchType\": \"basic\", \"stemTerms\": true }";
        String jsonJobBody = "{\"app\": \"amg\", \"jobFilter\": null, \"jobId\": \"83560b80-0d31-11e7-88df-0242ac110003\", \"jobsFinished\": 1, \"jobsProcessing\": 0, \"jobsSubmitted\": 1, \"lastSeen\": \"2017-03-20T05:53:17.165041\", \"maxDepth\": 0, \"runawayThreshold\": 350000, \"searchId\": \"37ca5b7c-2f31-530c-9d08-bc334cb434fd\", \"started\": \"2017-03-20T05:53:16.610297\", \"status\": 7, \"videosFound\": 5235 }";

        PactFragment fragment = builder
                .uponReceiving("API")
                .path("/")
                .method("OPTIONS")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body("")
                .uponReceiving("API v3 endpoint response")
                .path(v3SearchId)
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(jsonSearchBody)
                .uponReceiving("API v3 jobid response")
                .path(v3JobId)
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(jsonJobBody)
                .toFragment();
        return fragment;
    }

    @Override
    protected String providerName() {
        return "test_provider";
    }

    @Override
    protected String consumerName() {
        return "test_consumer";
    }

    @Override
    protected void runTest(String url) {
        Map response;
        try {
            response = new ConsumerClient(url).getAsMap(v3SearchId, "");
            logger.info(response.toString());
            //logger.info(Integer.toString(new ConsumerClient(url).getAsMap(v3SearchId)));
            Assert.assertEquals(new ConsumerClient(url).options("/"), 200);
            //assert(jsonSearchBody, response.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            response = new ConsumerClient(url).getAsMap(v3JobId, "");
            //Assert.assertEquals(new ConsumerClient(url).options(v3JobId), 200);
            //assert(jsonJobBody, response.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
