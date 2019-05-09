package com.huahua.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.huahua.article.pojo.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.print.attribute.standard.MediaSize;
import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{

    @Query(nativeQuery = true,value = "select * from tb_article where id = ?")
    Article findOneById(String articleid);

    @Modifying
    @Query(nativeQuery = true,value = "update tb_article set thumbup = thumbup+1 where id = ?")
    void updateArticleThumbup(String id);

    @Query(nativeQuery = true,value = "select * from tb_article where istop = 1")
    List<Article> findArticleTopByIstop(String istop);
}
