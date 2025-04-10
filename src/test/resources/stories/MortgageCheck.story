Scenario: Successful mortgage check

Given a mortgage check request with income 100000, maturity period 20, loan value 300000, and home value 400000 with currency EUR
When the mortgage check is performed
Then the response status code should be 200
And the response should indicate feasibility as true and monthly costs as 1897.95

Scenario: Mortgage check fails validation (Loan value exceeds 4 times the income), and returns feasibility as false

Given a mortgage check request that fails validation with income 10000, maturity period 20, loan value 50000, and home value 100000 with currency EUR
When the mortgage check is performed
Then the response should indicate feasibility as false


Scenario: Mortgage check fails validation (Loan value exceeds the home value), and returns feasibility as false

Given a mortgage check request that fails validation with income 200000, maturity period 20, loan value 500000, and home value 400000 with currency EUR
When the mortgage check is performed
Then the response should indicate feasibility as false

Scenario: Mortgage check fails with Bad Request when No interest rate found for given maturity

Given a mortgage check request that fails validation with income 100000, maturity period 40, loan value 300000, and home value 400000 with currency EUR
When the mortgage check is performed
Then the response should be a Bad request

Scenario: Mortgage check fails with Bad Request when input is not valid

Given a mortgage check request that fails validation with income 1AAAA, maturity period 20, loan value 50000, and home value 100000 with currency EUR
When the mortgage check is performed
Then the response should be a Bad request


Scenario: Mortgage check fails with input validation (Minimum values), and returns Bad request

Given a mortgage check request that fails validation with income 0, maturity period 1, loan value 0, and home value 0 with currency EUR
When the mortgage check is performed
Then the response should be a Bad request


