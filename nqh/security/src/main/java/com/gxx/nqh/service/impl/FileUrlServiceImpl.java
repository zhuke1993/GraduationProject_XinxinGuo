package com.gxx.nqh.service.impl;

import com.gxx.nqh.entity.FileURL;
import com.gxx.nqh.service.FileUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ZHUKE on 2016/4/8.
 */
@Service
public class FileUrlServiceImpl implements FileUrlService {
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Transactional
    public void saveFileUrl(FileURL fileURL) {
        hibernateTemplate.save(fileURL);
    }
}
