package com.huahua.article.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.huahua.article.pojo.Channel;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ChannelDao extends JpaRepository<Channel,String>,JpaSpecificationExecutor<Channel>{

    /**
     * 根据频道ID获取文章列表
     * @param channelId
     * @return
     */
    @Query(nativeQuery = true,value = "select * from tb_article where channelid = ?")
    Page<Channel> findArticleListByChannelId(String channelId, PageRequest pageRequest);
}
