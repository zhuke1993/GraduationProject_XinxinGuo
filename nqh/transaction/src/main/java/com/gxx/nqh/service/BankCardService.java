package com.gxx.nqh.service;

import com.gxx.nqh.entity.BankCard;

/**
 * Created by ZHUKE on 2016/3/28.
 */
public interface BankCardService {
    /**
     * 绑卡
     *
     * @param bankCard
     * @param userId
     */
    void bindCard(BankCard bankCard, long userId);

    /**
     * 解绑
     *
     * @param bankCardId
     */
    void cancelBindCard(long bankCardId);

    public boolean isBindCard(long userId);

    void createBinkCard(BankCard bankCard);
}
