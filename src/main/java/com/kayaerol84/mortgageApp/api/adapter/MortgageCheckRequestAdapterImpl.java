package com.kayaerol84.mortgageApp.api.adapter;

import com.kayaerol84.mortgageApp.api.dto.MortgageCheckRequest;
import com.kayaerol84.mortgageApp.model.Amount;
import com.kayaerol84.mortgageApp.model.MortgageCheck;
import com.kayaerol84.mortgageApp.model.MortgageRate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MortgageCheckRequestAdapterImpl implements MortgageCheckRequestAdapter {

    @Override
    public MortgageCheck adapt(MortgageCheckRequest request, MortgageRate mortgageRate) {
        String currency = Objects.requireNonNullElse(request.currency(), "EUR");
        return new MortgageCheck(
                new Amount(request.income(), currency),
                mortgageRate,
                new Amount(request.loanValue(), currency),
                new Amount(request.homeValue(), currency));
    }
}
