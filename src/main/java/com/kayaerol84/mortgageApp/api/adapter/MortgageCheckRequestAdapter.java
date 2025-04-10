package com.kayaerol84.mortgageApp.api.adapter;

import com.kayaerol84.mortgageApp.api.dto.MortgageCheckRequest;
import com.kayaerol84.mortgageApp.model.MortgageCheck;
import com.kayaerol84.mortgageApp.model.MortgageRate;

public interface MortgageCheckRequestAdapter {
    MortgageCheck adapt(MortgageCheckRequest request, MortgageRate mortgageRate);
}
