package com.gxx.nqh.api;

import com.google.gson.Gson;
import com.gxx.nqh.dto.*;
import com.gxx.nqh.entity.Account;
import com.gxx.nqh.entity.Agreement;
import com.gxx.nqh.entity.BankCard;
import com.gxx.nqh.entity.UserInfo;
import com.gxx.nqh.enumtype.AgreementStatus;
import com.gxx.nqh.enumtype.BankCardStatus;
import com.gxx.nqh.exceptions.NQHException;
import com.gxx.nqh.service.AccountService;
import com.gxx.nqh.service.AgreementService;
import com.gxx.nqh.service.BankCardService;
import com.gxx.nqh.service.UserInfoService;
import com.gxx.nqh.util.ResponseUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 借贷管理API
 * Created by ZHUKE on 2016/3/29.
 */
@Controller
@RequestMapping("/transaction")
public class TransactionApi {

    public static void main(String[] args) {
        System.out.println(AgreementStatus.IN_RAISING.getName());
    }

    private Logger logger = LogManager.getLogger(TransactionApi.class);

    @Autowired
    private AgreementService agreementService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private BankCardService bankCardService;

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Autowired
    private AccountService accountService;


    @RequestMapping(value = "/agreement/add", method = RequestMethod.GET)
    public void createAgreeement(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletRequestBindingException {
        ResponseUtil responseUtil;
        try {
            String loanFor = URLDecoder.decode(ServletRequestUtils.getStringParameter(request, "loanFor"), "utf-8");
            String loanTitle = URLDecoder.decode(ServletRequestUtils.getStringParameter(request, "loanTitle"), "utf-8");
            BigDecimal amount = new BigDecimal(ServletRequestUtils.getStringParameter(request, "amount"));
            BigDecimal rateMonthly = new BigDecimal(ServletRequestUtils.getStringParameter(request, "rateMonthly"));//利率
            String description = URLDecoder.decode(ServletRequestUtils.getStringParameter(request, "description"), "utf-8");
            int repaymentLimit = ServletRequestUtils.getIntParameter(request, "repaymentLimit");

            HttpSession session = request.getSession();
            UserInfo loginUser = userInfoService.getLoginUser(request);

            Agreement agreement = new Agreement();
            agreement.setCreatedOn(new Date());
            agreement.setStatus(AgreementStatus.IN_RAISING.getName());
            agreement.setAmount(amount);
            agreement.setDescription(description);
            agreement.setLoanFor(loanFor);
            agreement.setLoanTitle(loanTitle);
            agreement.setRateMonthly(rateMonthly);
            agreement.setAccountId(loginUser.getId());
            agreement.setUserId(loginUser.getId());
            agreement.setRepaymentLimit(repaymentLimit);
            agreement.setRaisedAmount(new BigDecimal(0));
            agreement.setRaisedLimitation(amount);
            agreementService.create(loginUser, agreement);

            responseUtil = new ResponseUtil("借款申请成功。");
            responseUtil.write(response);

        } catch (NQHException e) {
            responseUtil = new ResponseUtil(e);
            responseUtil.setCode("888");
            responseUtil.write(response);
        }

    }

    @RequestMapping("/isAvailable")
    public void isAvailableUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserInfo loginUser = userInfoService.getLoginUser(request);
        ResponseUtil responseUtil;
        if (loginUser.getIsAvailable() == 1) {
            responseUtil = new ResponseUtil("OK", "可以申请借款");
        } else {
            responseUtil = new ResponseUtil("8888", "没有权限申请贷款，请提交资质审核或等待审核通过。");
        }
        responseUtil.write(response);
    }


    @RequestMapping(value = "/agreement/{id}", method = RequestMethod.GET)
    public void getAgreeementDetail(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") long id) throws IOException {
        ResponseUtil responseUtil;
        try {
            List<AgreementDetailDto> agreement = agreementService.getAgreementDetail(id);
            String agreementJson = new Gson().toJson(agreement);
            responseUtil = new ResponseUtil(agreement);
            responseUtil.write(response);
        } catch (Exception e) {
            e.printStackTrace();
            responseUtil = ResponseUtil._ServerError;
            responseUtil.write(response);
        }
    }


    @RequestMapping(value = "/agreementSummaryList", method = RequestMethod.GET)
    public void getAgreeementSummaryList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<AgreementSummaryDto> agreementSummayList = agreementService.getAgreementSummaryList();
        ResponseUtil responseUtil = new ResponseUtil();
        responseUtil.setCode("OK");
        responseUtil.setMsg(new Gson().toJson(agreementSummayList));
        response.getWriter().print(responseUtil.toString());
    }


    @RequestMapping(value = "/agreement/audit/{id}", method = RequestMethod.GET)
    public void auditAgreement(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") long id) {
        try {
            String auditResult = ServletRequestUtils.getStringParameter(request, "auditResult");
            agreementService.audit(id, auditResult);
        } catch (ServletRequestBindingException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/agreement/invest", method = RequestMethod.GET)
    public void invest(HttpServletRequest request, HttpServletResponse response) {
        UserInfo loginUser = userInfoService.getLoginUser(request);
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            agreementService.invest(ServletRequestUtils.getLongParameter(request, "agreementId"), loginUser.getId(), new BigDecimal(request.getParameter("amount")));
            responseUtil.setCode("OK");
            responseUtil.setMsg("投资成功");
        } catch (Exception e) {
            responseUtil.setCode("FAILED");
            responseUtil.setMsg("投资失败，" + e.getMessage());
            e.printStackTrace();
        }

        try {
            response.getWriter().print(responseUtil.toString());
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @RequestMapping(value = "/loanList", method = RequestMethod.GET)
    public void getLoanList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseUtil responseUtil;

        try {
            UserInfo loginUser = userInfoService.getLoginUser(request);
            List<LoanDto> loanDtoList = agreementService.getLoanDto(loginUser.getId());

            responseUtil = new ResponseUtil(loanDtoList);
            responseUtil.write(response);
        } catch (IOException e) {
            e.printStackTrace();
            responseUtil = new ResponseUtil(e);
            responseUtil.write(response);
        }

    }


    @RequestMapping(value = "/friendLoanList/{userId}", method = RequestMethod.GET)
    public void getUserLoanList(HttpServletRequest request, HttpServletResponse response, @PathVariable("userId") long userId) throws IOException {
        ResponseUtil responseUtil;

        try {
            List<LoanDto> loanDtoList = agreementService.getLoanDto(userId);
            responseUtil = new ResponseUtil(loanDtoList);
            responseUtil.write(response);
        } catch (IOException e) {
            e.printStackTrace();
            responseUtil = new ResponseUtil(e);
            responseUtil.write(response);
        }

    }

    @RequestMapping(value = "/investList", method = RequestMethod.GET)
    public void getInvestList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseUtil responseUtil;
        try {
            UserInfo loginUser = userInfoService.getLoginUser(request);
            List<InvestDto> investDtoList = agreementService.getInvestDto(loginUser.getId());

            responseUtil = new ResponseUtil(investDtoList);
            responseUtil.write(response);
        } catch (IOException e) {
            e.printStackTrace();
            responseUtil = new ResponseUtil(e);
            responseUtil.write(response);
        }
    }

    @RequestMapping(value = "/isCardBind", method = RequestMethod.GET)
    public void isCardBind(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseUtil responseUtil;
        try {
            boolean isCardBind = bankCardService.isBindCard(userInfoService.getLoginUser(request).getId());
            if (isCardBind) {
                responseUtil = new ResponseUtil("OK", "已经绑卡，可以充值。");
            } else {
                responseUtil = new ResponseUtil("FAILED", "请先绑卡再进行充值。");
            }
            responseUtil.write(response);
        } catch (Exception e) {
            e.printStackTrace();
            responseUtil = new ResponseUtil(e);
            responseUtil.write(response);
        }
    }

    @RequestMapping(value = "/accountDetail", method = RequestMethod.GET)
    public void getAccountDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseUtil responseUtil;
        try {
            Account account = userInfoService.getAccount(userInfoService.getLoginUser(request).getId());
            AccountDetailDto accountDetailDto = new AccountDetailDto(hibernateTemplate.get(BankCard.class, account.getBankCardId()).getBankCardNo(), account.getAmount());
            responseUtil = new ResponseUtil(accountDetailDto);
            responseUtil.write(response);
        } catch (Exception e) {
            e.printStackTrace();
            responseUtil = new ResponseUtil(e);
            responseUtil.write(response);
        }
    }

    @RequestMapping(value = "/cardBind")
    public void cardBind(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException, IOException {
        ResponseUtil responseUtil;
        try {
            String bankCardNo = ServletRequestUtils.getStringParameter(request, "bankCardNo");
            String bankOwenerName = ServletRequestUtils.getStringParameter(request, "bankOwenerName");
            Assert.notNull(bankCardNo);
            Assert.notNull(bankOwenerName);

            BankCard bankCard = new BankCard();
            bankCard.setBankCardNo(bankCardNo);
            bankCard.setBankOwenerName(bankOwenerName);
            bankCard.setCreatedOn(new Date());
            bankCard.setBindDate(new Date());
            bankCard.setStatus(BankCardStatus.BINDED.getName());

            bankCardService.createBinkCard(bankCard);
            bankCardService.bindCard(bankCard, userInfoService.getLoginUser(request).getId());

            responseUtil = new ResponseUtil("OK", "绑定成功");
            responseUtil.write(response);

        } catch (NullPointerException e) {
            e.printStackTrace();
            responseUtil = new ResponseUtil("FAILED", "参数非法");
            responseUtil.write(response);
        }
    }

    @RequestMapping(value = "/recharge")
    public void recharge(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException, IOException {
        ResponseUtil responseUtil;
        try {
            BigDecimal rechargeAmount = new BigDecimal(request.getParameter("rechargeAmount"));
            accountService.rechargeAccount(userInfoService.getLoginUser(request).getId(), rechargeAmount);
            responseUtil = new ResponseUtil("OK", "充值成功");

            responseUtil.write(response);
        } catch (Exception e) {
            e.printStackTrace();
            responseUtil = ResponseUtil._ServerError;
            responseUtil.write(response);
        }
    }

    @RequestMapping(value = "/withdraw")
    public void withdraw(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException, IOException {
        ResponseUtil responseUtil;
        try {
            BigDecimal withdrawAmount = new BigDecimal(request.getParameter("withdrawAmount"));
            accountService.withdraw(userInfoService.getLoginUser(request).getId(), withdrawAmount);
            responseUtil = new ResponseUtil("OK", "提现成功");
            responseUtil.write(response);
        } catch (Exception e) {
            e.printStackTrace();
            responseUtil = ResponseUtil._ServerError;
            responseUtil.write(response);
        }
    }

    @RequestMapping(value = "/sys_report")
    public void getSystemReport(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException, IOException {
        ResponseUtil responseUtil;
        try {
            SystemReportDto systemReport = agreementService.getSystemReport();
            responseUtil = new ResponseUtil(systemReport);
            responseUtil.write(response);
        } catch (Exception e) {
            e.printStackTrace();
            responseUtil = ResponseUtil._ServerError;
            responseUtil.write(response);
        }
    }

    @RequestMapping(value = "/loan_detail_list")
    public void getLoanDetailList(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException, IOException {
        ResponseUtil responseUtil;
        try {
            String status = URLDecoder.decode(ServletRequestUtils.getStringParameter(request, "status"), "utf-8");
            if (status.equals("null")) {
                status = null;
            }
            ArrayList<LoanDto> loanList = agreementService.getLoanList(status);
            responseUtil = new ResponseUtil(loanList);
            responseUtil.write(response);
        } catch (Exception e) {
            e.printStackTrace();
            responseUtil = ResponseUtil._ServerError;
            responseUtil.write(response);
        }
    }

}
