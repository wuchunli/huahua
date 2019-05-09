package com.huahua.article.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.huahua.article.pojo.Column;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ColumnDao extends JpaRepository<Column,String>,JpaSpecificationExecutor<Column>{
    /**
     * 根据专栏ID获取文章列表
     * @param columnId
     * @return
     */
    @Query(nativeQuery = true,value = "select * from tb_article where columnid = ?")
    Page<Column> findArticleListByColumnId(String columnId, PageRequest pageRequest);
}
