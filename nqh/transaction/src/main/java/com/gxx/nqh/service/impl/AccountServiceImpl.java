package com.gxx.nqh.service.impl;

import com.gxx.nqh.dto.LoanDto;
import com.gxx.nqh.dto.MyInvestDto;
import com.gxx.nqh.dto.MyLoanDto;
import com.gxx.nqh.entity.Account;
import com.gxx.nqh.entity.AccountEntry;
import com.gxx.nqh.entity.Agreement;
import com.gxx.nqh.enumtype.AccountEntryType;
import com.gxx.nqh.exceptions.LoginTimeOutException;
import com.gxx.nqh.exceptions.NQHException;
import com.gxx.nqh.service.AccountService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ZHUKE on 2016/4/3.
 */
@Service
public class AccountServiceImpl implements AccountService {

    private Logger logger = LogManager.getLogger(AccountService.class);


    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void rechargeAccount(Long userId, BigDecimal amount) {
        Account account = (Account) hibernateTemplate.find("from Account a where a.userId = ?", userId).get(0);
        if (!(account.getUserId() == null)) {

            AccountEntry accountEntry = new AccountEntry();
            accountEntry.setCreatedOn(new Date());
            accountEntry.setAccountId(account.getId());
            accountEntry.setAmount(amount);
            accountEntry.setType(AccountEntryType.RECHARGE.getValue());

            hibernateTemplate.save(accountEntry);

            BigDecimal newBalance = account.getAmount().add(amount);
            account.setAmount(newBalance);

            hibernateTemplate.update(account);
        } else {
            throw new NQHException("请先绑定银行卡。");
        }
    }

    public ArrayList<MyLoanDto> getLoanList(Long userId) {

        ArrayList<MyLoanDto> myLoanDtoList = new ArrayList<MyLoanDto>();

        List<Agreement> agreementList = (List<Agreement>) hibernateTemplate.find("from Agreement ag where ag.userId = ?", userId);

        Iterator<Agreement> it = agreementList.iterator();
        while (it.hasNext()) {
            MyLoanDto myLoanDto = new MyLoanDto();
            Agreement ag = it.next();
            myLoanDto.setLoanTitle(ag.getLoanTitle());
            myLoanDto.setAmount(ag.getAmount());
            myLoanDto.setDate(ag.getCreatedOn());
            myLoanDtoList.add(myLoanDto);
        }
        return myLoanDtoList;
    }

    public ArrayList<MyInvestDto> investList(Long userId) {
        String sql = "SELECT\n" +
                "\tag.loan_title,\n" +
                "\tar.created_on,\n" +
                "\tar.amount\n" +
                "FROM\n" +
                "\tagreement_request ar\n" +
                "INNER JOIN agreement ag ON ar.agreement_id = ag.id\n" +
                "WHERE\n" +
                "\tar.opt_user_id = ?;";

        final ArrayList<MyInvestDto> myInvestDtoArrayList = new ArrayList<MyInvestDto>();

        jdbcTemplate.query(sql, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                MyInvestDto myInvestDto = new MyInvestDto();
                myInvestDto.setAmount(resultSet.getBigDecimal("amount"));
                myInvestDto.setLoanTitle(resultSet.getString("loan_title"));
                myInvestDto.setDate(resultSet.getDate("created_on"));

                myInvestDtoArrayList.add(myInvestDto);
            }
        });

        return myInvestDtoArrayList;
    }

    @Transactional
    public void withdraw(Long userId, BigDecimal amount) {
        Account account = (Account) hibernateTemplate.find("from Account a where a.userId = ?", userId).get(0);
        if (!(account.getUserId() == null)) {

            AccountEntry accountEntry = new AccountEntry();
            accountEntry.setCreatedOn(new Date());
            accountEntry.setAccountId(account.getId());
            accountEntry.setAmount(new BigDecimal(0).subtract(amount));
            accountEntry.setType(AccountEntryType.WITHDRAW.getValue());

            hibernateTemplate.save(accountEntry);

            BigDecimal newBalance = account.getAmount().subtract(amount);
            account.setAmount(newBalance);

            hibernateTemplate.update(account);
        } else {
            throw new NQHException("请先绑定银行卡。");
        }
    }
}
