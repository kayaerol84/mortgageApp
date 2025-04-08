package com.kayaerol84.mortgageApp.acceptanceTests;

import com.kayaerol84.mortgageApp.api.dto.MortgageCheckResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MortgageCheckSteps {

    private String baseUrl = "http://localhost:8081" ;

    private RequestSpecification request;
    private Response response;
    private String jsonMortgageCheckRequest;

    @Given("a mortgage check request with income $income, maturity period $maturityPeriod, loan value $loanValue, and home value $homeValue with currency $currency")
    public void aMortgageCheckRequestWith(BigDecimal income, int maturityPeriod, BigDecimal loanValue, BigDecimal homeValue, String currency) {
        request = RestAssured.given() ;
        request.contentType(MediaType.APPLICATION_JSON_VALUE);

        jsonMortgageCheckRequest = String.format(
                "{ \"income\": %s, \"maturityPeriod\": %d, \"loanValue\": %s, \"homeValue\": %s, \"currency\": \"%s\" }",
                income, maturityPeriod, loanValue, homeValue, currency
        );
    }

    @Given("a mortgage check request with income $income, maturity period $maturityPeriod, loan value $loanValue, and home value $homeValue")
    public void aMortgageCheckRequestWith(BigDecimal income, int maturityPeriod, BigDecimal loanValue, BigDecimal homeValue) {
        request = RestAssured.given() ;
        request.contentType(MediaType.APPLICATION_JSON_VALUE);

        jsonMortgageCheckRequest = String.format(
                "{ \"income\": %s, \"maturityPeriod\": %d, \"loanValue\": %s, \"homeValue\": %s }",
                income, maturityPeriod, loanValue, homeValue
        );
    }

    @When("the mortgage check is performed")
    public void theMortgageCheckIsPerformed() {

        var apiEndpoint = baseUrl + "/api/mortgage-check";
        response = request.body(jsonMortgageCheckRequest).post(apiEndpoint);
    }

    @Then("the response status code should be $statusCode")
    public void theResponseStatusCodeShouldBe(int statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }

    @Then("the response should indicate feasibility as $feasible and monthly costs as $monthlyCosts")
    public void theResponseShouldIndicateFeasibilityAsAndMonthlyCostsAs(boolean feasible, BigDecimal monthlyCosts) {
        MortgageCheckResponse mortgageCheckResponse = response.as(MortgageCheckResponse.class);
        assertEquals(feasible, mortgageCheckResponse.feasible());
        assertEquals(0, monthlyCosts.compareTo(mortgageCheckResponse.monthlyCosts())); // Use compareTo for BigDecimal
    }

    @Then("the response should indicate feasibility as false")
    public void theResponseShouldIndicateFeasibilityAsFalse() {
        MortgageCheckResponse mortgageCheckResponse = response.as(MortgageCheckResponse.class);
        assertFalse(mortgageCheckResponse.feasible());
        assertEquals(BigDecimal.ZERO, mortgageCheckResponse.monthlyCosts());
    }

    @Given("a mortgage check request that fails validation with income $income, maturity period $maturityPeriod, loan value $loanValue, and home value $homeValue with currency $currency")
    public void aMortgageCheckRequestThatFailsValidationWith(String income, String maturityPeriod, String loanValue, String homeValue, String currency) {
        request = RestAssured.given();
        request.contentType(MediaType.APPLICATION_JSON_VALUE);

        jsonMortgageCheckRequest = String.format(
                "{ \"income\": \"%s\", \"maturityPeriod\": %s, \"loanValue\": \"%s\", \"homeValue\": \"%s\", \"currency\": \"%s\" }",
                income, maturityPeriod, loanValue, homeValue, currency
        );
    }

    @Then("the response should be an error")
    public void theResponseShouldBeAnError() {
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCode());
    }

    @Then("the response should be a Bad request")
    public void theResponseShouldBeABadRequest() {
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }
}