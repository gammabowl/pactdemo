package pact;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;
import au.com.dius.pact.consumer.ConsumerPactTest;
import java.util.Map;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class PactTest extends ConsumerPactTest {

    @Rule
    public PactProviderRule mockProvider = new PactProviderRule("test_provider", "localhost", 1234, this);

    String v3Path = "/v3/searches/37ca5b7c-2f31-530c-9d08-bc334cb434fd";

    @Override
    protected PactFragment createFragment(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        String jsonBody = "{\"active\": true, \"name\": \"test\", \"created\": \"2013-12-17T03:14:33.611223\"}";
        PactFragment fragment = builder
                .uponReceiving("API v3 endpoint response")
                .path(v3Path)
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(jsonBody)
                .toFragment();
        return fragment;
    }

    @Override
    protected PactFragment createFragment2(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
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
            response = new ConsumerClient(url).getAsMap(v3Path, "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}