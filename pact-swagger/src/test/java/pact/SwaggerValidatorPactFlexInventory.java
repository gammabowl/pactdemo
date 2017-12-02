package pact;

import com.atlassian.oai.validator.pact.PactProviderValidationResults;
import com.atlassian.oai.validator.pact.PactProviderValidator;
import com.atlassian.oai.validator.pact.ValidatedPactProviderRule;
import org.junit.Test;

import static org.junit.Assert.fail;


public class SwaggerValidatorPactFlexInventory {

    public static final String SWAGGER_JSON_URL = "https://api.env.company.com/swagger.json";
    public String CONSUMER_NAME_VALID = "testconsumer1";
    public String CONSUMER_PACT_URL_VALID = "http://pact-broker.env.company.com/pacts/provider/testprovider/consumer/testconsumer1/latest";
    public String CONSUMER_NAME_SEMI_CONTRACT = "testconsumer2";
    public String CONSUMER_PACT_URL_SEMI_CONTRACT = "http://pact-broker.env.company.com/pacts/provider/testprovider/consumer/testconsumer2/latest";


    @Test
    public void validatePactFromPactBrokerWithValidInteractions() {

        final PactProviderValidator validator = PactProviderValidator
                .createFor(SWAGGER_JSON_URL)
                .withConsumer(CONSUMER_NAME_VALID, CONSUMER_PACT_URL_VALID)
                .build();

        assertNoBreakingChanges(validator.validate());

    }

    @Test
    private void assertNoBreakingChanges(final PactProviderValidationResults results) {
        if (results.hasErrors()) {
            final StringBuilder msg = new StringBuilder("Validation errors found.\n\t");
            msg.append(results.getValidationFailureReport().replace("\n", "\n\t"));
            fail(msg.toString());
        }
    }

}
