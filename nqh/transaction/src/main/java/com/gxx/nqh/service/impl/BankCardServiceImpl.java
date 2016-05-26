package com.gxx.nqh.service.impl;

import com.gxx.nqh.entity.Account;
import com.gxx.nqh.entity.BankCard;
import com.gxx.nqh.enumtype.BankCardStatus;
import com.gxx.nqh.exceptions.NQHException;
import com.gxx.nqh.service.BankCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by GXX on 2016/3/28.
 */
@Service
public class BankCardServiceImpl implements BankCardService {
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Transactional
    public void bindCard(BankCard bankCard, long userId) {
        Assert.notNull(bankCard.getBankCardNo());
        Assert.notNull(bankCard.getBankOwenerName());

        hibernateTemplate.save(bankCard);

        Account account = (Account) hibernateTemplate.find("from Account a where a.userId = ?", userId).get(0);
        account.setBankCardId(bankCard.getId());
        hibernateTemplate.update(account);
    }

    public boolean isBindCard(long userId) {
        List<Account> list = (List<Account>) hibernateTemplate.find("from Account a where a.userId = ?", userId);
        if (CollectionUtils.isEmpty(list)) {
            throw new NQHException("用户不存在");
        } else {
            Long bankCardId = list.get(0).getBankCardId();
            if (bankCardId != null && bankCardId != 0) {
                return true;
            }
        }
        return false;
    }

    @Transactional
    public void createBinkCard(BankCard bankCard) {
        hibernateTemplate.save(bankCard);
    }

    @Transactional
    public void cancelBindCard(long bankCardId) {
        BankCard bankCard = hibernateTemplate.load(BankCard.class, bankCardId);
        Assert.notNull(bankCard);
        bankCard.setStatus(BankCardStatus.CANCELED.getName());
        hibernateTemplate.update(bankCard);
    }
}
