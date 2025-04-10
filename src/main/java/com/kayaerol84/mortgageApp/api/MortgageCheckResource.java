package com.kayaerol84.mortgageApp.api;

import com.kayaerol84.mortgageApp.api.adapter.MortgageCheckRequestAdapter;
import com.kayaerol84.mortgageApp.api.dto.MortgageCheckRequest;
import com.kayaerol84.mortgageApp.api.dto.MortgageCheckResponse;
import com.kayaerol84.mortgageApp.service.InterestRateService;
import com.kayaerol84.mortgageApp.service.MortgageCheckService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MortgageCheckResource {

    private final MortgageCheckService mortgageCheckService;
    private final InterestRateService interestRateService;
    private final MortgageCheckRequestAdapter mortgageCheckRequestAdapter;

    public MortgageCheckResource(MortgageCheckService mortgageCheckService, InterestRateService interestRateService, MortgageCheckRequestAdapter mortgageCheckRequestAdapter) {
        this.mortgageCheckService = mortgageCheckService;
        this.interestRateService = interestRateService;
        this.mortgageCheckRequestAdapter = mortgageCheckRequestAdapter;
    }

    @PostMapping("/mortgage-check")
    public ResponseEntity<MortgageCheckResponse> checkMortgage(@RequestBody @Valid MortgageCheckRequest request) {
        var mortgageRate = interestRateService.getByMaturityPeriod(request.maturityPeriod());
        var mortgageCheck = mortgageCheckRequestAdapter.adapt(request, mortgageRate);
        var result = mortgageCheckService.checkMortgage(mortgageCheck);
        return ResponseEntity.ok(MortgageCheckResponse.from(result));
    }
}
