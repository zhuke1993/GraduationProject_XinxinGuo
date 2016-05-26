package com.gxx.nqh.service.impl;

import com.gxx.nqh.entity.Communication;
import com.gxx.nqh.service.CommunicationService;
import com.gxx.nqh.service.MailService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by GXX on 2016/3/29.
 */
@Service
public class CommunicationServiceImpl implements CommunicationService, ApplicationContextAware {
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    private ApplicationContext applicationContext;

    @Transactional
    public void sendConnunication(Communication communication) {

        MailService mailService = applicationContext.getBean(MailService.class);
        mailService.setMailInfo(communication.getUserName(), communication.getContent());

        Future<?> submit = threadPoolExecutor.submit(mailService);
        communication.setStatus("send success");

        hibernateTemplate.save(communication);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
