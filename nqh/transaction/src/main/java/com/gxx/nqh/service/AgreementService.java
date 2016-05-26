package com.gxx.nqh.service;

import com.gxx.nqh.dto.*;
import com.gxx.nqh.entity.Agreement;
import com.gxx.nqh.entity.UserInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GXX on 2016/3/28.
 */
public interface AgreementService {

    void create(UserInfo userInfo, Agreement agreement);

    void audit(long agreementId, String auditResult);

    List<AgreementSummaryDto> getAgreementSummaryList();

    List<AgreementDetailDto> getAgreementDetail(Long agreementId);

    void invest(Long agreementId, Long userId, BigDecimal amount);

    List<LoanDto> getLoanDto(Long userId);

    List<InvestDto> getInvestDto(Long userId);

    UserInfo getUserInfo(Long agreementId);

    SystemReportDto getSystemReport();

    ArrayList<LoanDto> getLoanList(String status);

    void repayment(Agreement agreement);
}
