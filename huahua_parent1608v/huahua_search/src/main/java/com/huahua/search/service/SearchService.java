package com.huahua.search.service;

import com.huahua.search.dao.SearchDao;
import com.huahua.search.pojo.ArticleEs;
import huahua.common.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SearchService {
    @Autowired
    private SearchDao searchDao;

    /**
     * 通过title concent 查询es引擎中的数据
     * @param keywords
     * @param page
     * @param size
     * @return
     */
    public Page<ArticleEs> searchArticle(String keywords, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page-1, size);

        return  searchDao.findAllByTitleoOrContent(keywords,keywords,pageRequest);
    }

    public void add(ArticleEs articleEs) {
        searchDao.save(articleEs);
    }
}
