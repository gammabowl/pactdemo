package pact;

import java.util.logging.Level;
import java.util.logging.Logger;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;
import au.com.dius.pact.consumer.ConsumerPactTest;
import java.util.*;
import java.io.*;
import java.lang.*;
import au.com.dius.pact.consumer.PactProviderRule;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import org.junit.Rule;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import org.junit.Assert;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PactDslJsonAuthorization extends ConsumerPactTest {

    Logger logger = Logger.getLogger(PactDslJsonAuthorization.class.getName());

    @Rule
    public PactProviderRule mockProvider = new PactProviderRule("test_provider-5", "localhost", 1234, this);

    private String[] getAccountsToken() {
        int status_code = 0;
        HttpClient httpClient = new HttpClient();
        httpClient.getParams().setParameter("Content-Type", "application/x-www-form-urlencoded");

        String accountsUrl = "https://accts.company.com/api/tokens/create";

        PostMethod postMethod = new PostMethod(accountsUrl);
        postMethod.addParameter("email", "email@gmail.com");
        postMethod.addParameter("password", "password1");
        try {
            status_code = httpClient.executeMethod(postMethod);
        } catch (IOException e) {
            logger.info("IOException - executeMethod");
        }
        String response;
        try {
            response = postMethod.getResponseBodyAsString();
            logger.info(response);
            String accountsResponse[] = new String[2];
            accountsResponse[0] = Integer.toString(status_code);
            accountsResponse[1] = response;
            return accountsResponse;
        } catch (IOException e) {
            logger.info("IOException - getResponseBodyAsString");
        }
        logger.info(Integer.toString(status_code));
        return null;
    }

    String v3Path = "/v3";
    private DslPart body = new PactDslJsonBody()
            .decimalType("dbQueryTime", 0.003489971160888672)
            .decimalType("redisQueryTime", 0.0006480216979980469)
            .stringType("status", "api is up.")
            .decimalType("totalTime", 0.005939006805419922);

    protected PactFragment createFragment(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<String, String>();
        String[] accountsResponse = getAccountsToken();
        if(accountsResponse != null) {
            final String regex = "(.+output\\\":\\\")(.+)\\\"\\}";
            final Pattern pattern = Pattern.compile(regex);
            final Matcher matcher = pattern.matcher(accountsResponse[1]);

            String regex_output = "";
            while (matcher.find()) {
                regex_output = matcher.group(2);
            }
            String token = "Bearer " + regex_output;
            headers.put("Content-Type", "application/json");
            headers.put("Authorization", token);
        }

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
        return "test_provider-5";
    }

    @Override
    protected String consumerName() {
        return "test_consumer-5";
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
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}

