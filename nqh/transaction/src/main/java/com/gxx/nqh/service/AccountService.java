package com.gxx.nqh.service;

import com.gxx.nqh.dto.AccountDetailDto;
import com.gxx.nqh.dto.LoanDto;
import com.gxx.nqh.dto.MyInvestDto;
import com.gxx.nqh.dto.MyLoanDto;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by GXX on 2016/4/3.
 */
public interface AccountService {

    /**
     * 账户充值
     *
     * @param userId
     * @param amount
     */
    void rechargeAccount(Long userId, BigDecimal amount);

    /**
     * 借款列表
     *
     * @param userId
     * @return
     */
    ArrayList<MyLoanDto> getLoanList(Long userId);

    /**
     * 投资列表
     *
     * @param userId
     * @return
     */
    ArrayList<MyInvestDto> investList(Long userId);

    void withdraw(Long userId, BigDecimal amount);

}
