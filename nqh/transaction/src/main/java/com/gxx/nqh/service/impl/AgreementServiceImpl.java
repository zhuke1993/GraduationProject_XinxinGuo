package com.gxx.nqh.service.impl;

import com.gxx.nqh.dto.*;
import com.gxx.nqh.entity.*;
import com.gxx.nqh.enumtype.AccountEntryType;
import com.gxx.nqh.enumtype.AgreementRequestType;
import com.gxx.nqh.enumtype.AgreementStatus;
import com.gxx.nqh.exceptions.LoginTimeOutException;
import com.gxx.nqh.exceptions.NQHException;
import com.gxx.nqh.service.AgreementService;
import com.gxx.nqh.service.CommunicationService;
import com.gxx.nqh.service.MailService;
import com.gxx.nqh.service.UserInfoService;
import com.gxx.nqh.util.CreditScoreUtil;
import com.gxx.nqh.util.DateFormatUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
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
 * Created by ZHUKE on 2016/3/28.
 */
@Service
public class AgreementServiceImpl implements AgreementService, ApplicationContextAware {
    private Logger logger = LogManager.getLogger(AgreementService.class);
    @Autowired
    private HibernateTemplate hibernateTemplate;
    private ApplicationContext applicationContext;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private CommunicationService communicationService;

    @Transactional
    public void create(UserInfo loginUser, Agreement agreement) {
        if (loginUser.getIsAvailable() == 0) {
            throw new NQHException("请提交资质或等待资质审核通过");
        }
        hibernateTemplate.save(agreement);
        logger.info("创建一个新的借款合同:" + agreement.getId());
    }

    @Transactional
    public void audit(long agreementId, String auditResult) {
        Agreement agreement = hibernateTemplate.load(Agreement.class, agreementId);
        agreement.setStatus(auditResult);
        hibernateTemplate.update(agreement);
    }

    public List<AgreementSummaryDto> getAgreementSummaryList() {
        String sql = "SELECT\n" +
                "\tag.id, loan_title,\n" +
                "\tui.school_name,\n" +
                "\tag.rate_monthly,\n" +
                "\tag.repayment_limit,\n" +
                "\tag.amount,\n" +
                "\tag. STATUS,\n" +
                "\tui.credit_score\n" +
                "FROM\n" +
                "\tAgreement ag\n" +
                "LEFT JOIN user_info ui ON ui.id = ag.user_id\n" +
                "where ag.status = '筹集中';";

        final List<AgreementSummaryDto> agreementSummaryDtoList = new ArrayList<AgreementSummaryDto>();

        jdbcTemplate.query(sql, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                AgreementSummaryDto agreementSummaryDto = new AgreementSummaryDto();
                agreementSummaryDto.setAgreementId(resultSet.getLong("id"));
                agreementSummaryDto.setRateMonthly(resultSet.getBigDecimal("rate_monthly"));
                agreementSummaryDto.setAmount(resultSet.getBigDecimal("amount"));
                agreementSummaryDto.setLoanTitle(resultSet.getString("loan_title"));
                agreementSummaryDto.setRepaymentLimit(resultSet.getInt("repayment_limit"));
                agreementSummaryDto.setSchoolName(resultSet.getString("school_name"));
                agreementSummaryDto.setStatus(resultSet.getString("status"));

                int creditScore = resultSet.getInt("credit_score");
                agreementSummaryDto.setCreditScore(creditScore);
                agreementSummaryDto.setCreditScoreStr(CreditScoreUtil.getLevel(creditScore));

                agreementSummaryDtoList.add(agreementSummaryDto);
            }
        });
        return agreementSummaryDtoList;
    }


    public List<AgreementDetailDto> getAgreementDetail(Long agreementId) {
        String sql = "SELECT\n" +
                "\tag.loan_title, ag.raised_amount, ag.raised_limitation,ui.realname, \n" +
                "\tui.school_name,\n" +
                "\tag.rate_monthly,\n" +
                "\tag.repayment_limit,\n" +
                "\tag.amount,\n" +
                "\tag. STATUS,\n" +
                "\tag.loan_for,\n" +
                "\tag.description,\n" +
                "\tui.add_detail,\n" +
                "\tui.credit_score\n" +
                "FROM\n" +
                "\tAgreement ag\n" +
                "LEFT JOIN user_info ui ON ui.id = ag.user_id\n" +
                "where ag.id = ?;";

        final List<AgreementDetailDto> agreementDetailDtoList = new ArrayList<AgreementDetailDto>();
        jdbcTemplate.query(sql, new Object[]{agreementId}, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                AgreementDetailDto agreemenDetailDto = new AgreementDetailDto();
                agreemenDetailDto.setLoanUserName(resultSet.getString("realname"));
                agreemenDetailDto.setRateMonthly(resultSet.getBigDecimal("rate_monthly"));
                agreemenDetailDto.setAmount(resultSet.getBigDecimal("amount"));
                agreemenDetailDto.setLoanTitle(resultSet.getString("loan_title"));
                agreemenDetailDto.setRepaymentLimit(resultSet.getInt("repayment_limit"));
                agreemenDetailDto.setSchoolName(resultSet.getString("school_name"));
                agreemenDetailDto.setStatus(resultSet.getString("status"));
                agreemenDetailDto.setLoanFor(resultSet.getString("loan_for"));
                agreemenDetailDto.setDescription(resultSet.getString("description"));
                agreemenDetailDto.setAddress(resultSet.getString("add_detail"));
                agreemenDetailDto.setRaisedAmount(resultSet.getBigDecimal("raised_amount"));
                agreemenDetailDto.setRaisedLimitationAmount(resultSet.getBigDecimal("raised_limitation"));

                int creditScore = resultSet.getInt("credit_score");
                agreemenDetailDto.setCreditScore(creditScore);
                agreemenDetailDto.setCreditScoreStr(CreditScoreUtil.getLevel(creditScore));

                agreementDetailDtoList.add(agreemenDetailDto);
            }
        });
        return agreementDetailDtoList;
    }


    @Transactional
    public void invest(Long agreementId, Long userId, BigDecimal amount) {
        Account account = userInfoService.getAccount(userId);
        if (amount.compareTo(account.getAmount()) == 1) {
            throw new LoginTimeOutException("投资额超过账户余额");
        } else {
            AgreementRequest agreementRequest = new AgreementRequest();
            agreementRequest.setAgreementId(agreementId);
            agreementRequest.setRequestType(AgreementRequestType.INVESTMENT.getValue());
            agreementRequest.setStatus("active");
            agreementRequest.setCreatedOn(new Date());
            agreementRequest.setAmount(amount);
            agreementRequest.setOptUserId(userId);
            agreementRequest.setOptAccountId(account.getId());
            hibernateTemplate.save(agreementRequest);

            BigDecimal newAmount = account.getAmount().subtract(amount);
            account.setAmount(newAmount);

            AccountEntry accountEntry = new AccountEntry();
            accountEntry.setType(AccountEntryType.INVEST.getValue());
            accountEntry.setAmount(new BigDecimal(0).subtract(amount));
            accountEntry.setAccountId(account.getId());
            accountEntry.setCreatedOn(new Date());

            hibernateTemplate.save(accountEntry);
            hibernateTemplate.update(account);

            Agreement agreement = hibernateTemplate.load(Agreement.class, agreementId);
            BigDecimal newRaisedAmount = agreement.getRaisedAmount().add(amount);
            agreement.setRaisedAmount(newRaisedAmount);

            BigDecimal newRaisedLimit = agreement.getRaisedLimitation().subtract(amount);
            agreement.setRaisedLimitation(newRaisedLimit);

            if (newRaisedLimit.compareTo(new BigDecimal(0)) == 0) {
                agreement.setStatus(AgreementStatus.RAISING_COMPLETED.getValue());
            }
            hibernateTemplate.update(agreement);

            Account loanUserAccount = userInfoService.getAccount(agreement.getUserId());
            BigDecimal newLoanUserAccount = loanUserAccount.getAmount().add(amount);
            loanUserAccount.setAmount(newLoanUserAccount);
            hibernateTemplate.update(loanUserAccount);

            Communication communication = new Communication();
            communication.setUserName(hibernateTemplate.get(UserInfo.class, userId).getEmail());
            communication.setContent("您好，你在拿去花平台投资的" + amount + "元已经成功投资，谢谢您的支持。");

            Communication communication1 = new Communication();
            communication1.setUserName(hibernateTemplate.get(UserInfo.class, agreement.getUserId()).getEmail());
            communication1.setContent("您好，你在拿去花平台发起的借款请求，收到投资" + amount + "元，谢谢您的支持。");

            communicationService.sendConnunication(communication);
            communicationService.sendConnunication(communication1);

        }
    }

    public List<LoanDto> getLoanDto(Long userId) {
        List<Agreement> list = (List<Agreement>) hibernateTemplate.find("from Agreement a where a.userId = ?", userId);
        List<LoanDto> loanDtoList = new ArrayList<LoanDto>(list.size());
        for (int i = 0; i < list.size(); i++) {
            LoanDto loanDto = new LoanDto();
            loanDto.setAgreementId(list.get(i).getId());
            loanDto.setAmount(list.get(i).getAmount());
            loanDto.setLoanTitle(list.get(i).getLoanTitle());
            loanDto.setDate(DateFormatUtil.formatDate(list.get(i).getCreatedOn(), null));
            loanDtoList.add(loanDto);
        }
        return loanDtoList;
    }

    public List<InvestDto> getInvestDto(Long userId) {
        List<AgreementRequest> list = (List<AgreementRequest>) hibernateTemplate.find("from AgreementRequest ar where ar.optUserId = ?", userId);
        ArrayList<InvestDto> investDtos = new ArrayList<InvestDto>(list.size());
        Iterator<AgreementRequest> i = list.iterator();
        while (i.hasNext()) {
            AgreementRequest agreementRequest = i.next();
            Agreement agreement = hibernateTemplate.load(Agreement.class, agreementRequest.getAgreementId());
            investDtos.add(new InvestDto(agreementRequest.getId(),
                    agreement.getId(),
                    agreement.getLoanTitle(),
                    agreementRequest.getAmount().toString(),
                    DateFormatUtil.formatDate(agreementRequest.getCreatedOn(), null)));
        }
        return investDtos;
    }

    public UserInfo getUserInfo(Long agreementId) {
        Agreement agreement = hibernateTemplate.load(Agreement.class, agreementId);
        return hibernateTemplate.get(UserInfo.class, agreement.getUserId());
    }

    @Transactional
    public void repayment(Agreement agreement) {
        UserInfo userInfo = getUserInfo(agreement.getId());
        Account account = userInfoService.getAccount(userInfo.getId());
        if (account.getAmount().compareTo(agreement.getAmount()) == -1) {
            logger.info("自动还款失败，正在通知用户充值。agreementId=" + agreement.getId());
            agreement.setStatus(AgreementStatus.IN_REPAYMENT.getValue());

            Communication communication = new Communication();
            communication.setUserName(userInfo.getUserName());
            communication.setContent("您好，您于" + agreement.getCreatedOn() + "在拿去花平台的用于"
                    + agreement.getLoanFor() + "的借款" + agreement.getRaisedAmount()
                    + "已经处于到期还款状态，请尽快登陆拿去花平台进行充值，系统将在余额充足的情况下自动进行还款动作。谢谢您的配合和支持。");
            communicationService.sendConnunication(communication);
            throw new NQHException("余额不足,还款失败。AgreementId = " + agreement.getId());
        } else {
            //还款金额计算规则：(月利率/100+1)*(借款天数/30）[向上取整]*已筹集金额
            BigDecimal repaymentAmount = (new BigDecimal(1).add(agreement.getRateMonthly().divide(new BigDecimal(100)))).multiply(new BigDecimal(Math.ceil(agreement.getRepaymentLimit() / 30))).multiply(agreement.getRaisedAmount());
            BigDecimal newAmount = account.getAmount().subtract(repaymentAmount);

            AccountEntry accountEntry = new AccountEntry();
            accountEntry.setCreatedOn(new Date());
            accountEntry.setAmount(new BigDecimal(0).subtract(agreement.getAmount()));
            accountEntry.setAccountId(account.getId());
            accountEntry.setType(AccountEntryType.REPAYMENT.getValue());

            hibernateTemplate.save(accountEntry);

            account.setAmount(newAmount);
            agreement.setStatus(AgreementStatus.REPAYMENT_COMPLETED.getValue());
            hibernateTemplate.update(account);

            logger.info("自动还款成功，正在通知。agreementId=" + agreement.getId());

            Communication communication = new Communication();
            communication.setUserName(userInfo.getUserName());
            communication.setContent("您好，您于" + agreement.getCreatedOn() + "在拿去花平台的用于"
                    + agreement.getLoanFor() + "的借款" + agreement.getRaisedAmount()
                    + "已经处于到期还款状态，账户资金充足，已经自动还款成功。谢谢您的配合和支持。");

            communicationService.sendConnunication(communication);

            //还款到分用户
            List<AgreementRequest> agreementRequestList = (List<AgreementRequest>) hibernateTemplate.find("from AgreementRequest ar where ar.agreementId = ?", agreement.getId());
            for (int i = 0; i < agreementRequestList.size(); i++) {
                Account investAccount = hibernateTemplate.load(Account.class, agreementRequestList.get(i).getOptAccountId());
                BigDecimal gainAmount = (new BigDecimal(1).add(agreement.getRateMonthly().divide(new BigDecimal(100)))).multiply(new BigDecimal(Math.ceil(agreement.getRepaymentLimit() / 30))).multiply(agreementRequestList.get(i).getAmount());
                BigDecimal newAccount = investAccount.getAmount().add(gainAmount);
                investAccount.setAmount(newAccount);
                hibernateTemplate.update(investAccount);

                UserInfo investUserInfo = hibernateTemplate.get(UserInfo.class, agreementRequestList.get(i).getOptUserId());

                Communication communication1 = new Communication();
                communication1.setUserName(investUserInfo.getUserName());
                communication1.setContent("您好，您于" + agreementRequestList.get(i).getCreatedOn() + "在拿去花平台的投资"
                        + agreementRequestList.get(i).getAmount() + "元" + "已经处于到期自动还款，请登陆APP查看账户余额。谢谢您的配合和支持。");

                communicationService.sendConnunication(communication1);
            }

        }
    }

    public List<AgreementRequest> getAgreementRequest(Long agreementId) {

        return null;
    }


    public SystemReportDto getSystemReport() {
        String sql = "SELECT\n" +
                "\tcount(id) as total,\n" +
                "\tsum(case when status = '筹集中' then 1 else 0 end) as in_raising,\n" +
                "\tsum(case when status = '已完成' then 1 else 0 end) as raise_completed,\n" +
                "\tsum(case when status = '还款中' then 1 else 0 end) as in_repayment,\n" +
                "\tsum(case when status = '还款完成' then 1 else 0 end) as repayment_completed\n" +
                "FROM\n" +
                "\tagreement;";

        return jdbcTemplate.query(sql, new ResultSetExtractor<SystemReportDto>() {
            public SystemReportDto extractData(ResultSet rs) throws SQLException, DataAccessException {
                rs.next();
                return new SystemReportDto(rs.getInt("total"), rs.getInt("in_raising"),
                        rs.getInt("raise_completed"), rs.getInt("in_repayment"), rs.getInt("repayment_completed"));
            }
        });
    }


    public ArrayList<LoanDto> getLoanList(String status) {

        List<Agreement> agreements = new ArrayList<Agreement>();
        if (status == null) {
            agreements = (List<Agreement>) hibernateTemplate.find("from Agreement ar order by createdOn desc");
        } else if (AgreementStatus.isInEnum(status)) {
            agreements = (List<Agreement>) hibernateTemplate.find("from Agreement ar where ar.status = ? order by createdOn desc", status);
        }
        ArrayList<LoanDto> loanDtoArrayList = new ArrayList<LoanDto>();
        for (int i = 0; i < agreements.size(); i++) {
            Agreement agreement = agreements.get(i);
            LoanDto loanDto = new LoanDto(agreement.getId(), agreement.getLoanTitle(), agreement.getAmount(), DateFormatUtil.formatDate(agreement.getCreatedOn(), null), hibernateTemplate.get(UserInfo.class, agreement.getUserId()).getRealname(), agreement.getStatus());
            loanDtoArrayList.add(loanDto);
        }
        return loanDtoArrayList;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
