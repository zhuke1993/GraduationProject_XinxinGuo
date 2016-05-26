package com.gxx.nqh.jobschedule;

import com.gxx.nqh.entity.Agreement;
import com.gxx.nqh.entity.UserInfo;
import com.gxx.nqh.enumtype.AgreementStatus;
import com.gxx.nqh.service.AgreementService;
import com.gxx.nqh.service.MailService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.orm.hibernate5.HibernateTemplate;

import java.util.Date;
import java.util.List;

/**
 * 定期扫描超过还款期限的合同
 * 执行如下操作：
 * 1.如果余额充足，自动还款分发，发送通知
 * 2.余额不足，发送通知
 * Created by GXX on 2016/4/9.
 */
public class OvertimeAgreementSchedule {

    private HibernateTemplate hibernateTemplate;

    private AgreementService agreementService;

    private Logger logger = LogManager.getLogger(OvertimeAgreementSchedule.class);

    public void execute() {
        logger.info("定时任务开始执行");
        List<Agreement> agreementList = (List<Agreement>) hibernateTemplate.find("from Agreement a");
        for (int i = 0; i < agreementList.size(); i++) {
            Agreement agreement = agreementList.get(i);
            if (agreement.getStatus().equals(AgreementStatus.IN_REPAYMENT.getValue()) ||
                    (isOverTime(agreement.getCreatedOn(), agreement.getRepaymentLimit())
                            && agreement.getStatus().equals(AgreementStatus.IN_RAISING.getValue()))) {
                try {
                    agreementService.repayment(agreement);
                    UserInfo userInfo = hibernateTemplate.get(UserInfo.class, agreement.getUserId());
                    userInfo.setCreditScore(userInfo.getCreditScore() + 10);
                    hibernateTemplate.update(userInfo);

                } catch (Exception e) {
                    UserInfo userInfo = hibernateTemplate.get(UserInfo.class, agreement.getUserId());
                    userInfo.setCreditScore(userInfo.getCreditScore() - 20);
                    hibernateTemplate.update(userInfo);
                    logger.info("还款失败，信用-20" + userInfo.getRealname());
                    e.printStackTrace();
                }
            }
        }
        logger.info("定时任务执行成功");
    }

    boolean isOverTime(Date date, int period) {
        DateTime dateTime = new DateTime(date.getTime());
        DateTime ontimeDate = dateTime.plusDays(period);

        return ontimeDate.isBefore(new Date().getTime());
    }

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    public void setAgreementService(AgreementService agreementService) {
        this.agreementService = agreementService;
    }
}
