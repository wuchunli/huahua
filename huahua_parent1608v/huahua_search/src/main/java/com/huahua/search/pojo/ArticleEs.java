package com.huahua.search.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

/**
 * 文章实体类
 * 是否索引  能不能被搜索到
 * 是否分词  能不能被分词匹配到
 * 是否存储  能不能展示到页面
 */
@Data
@Document(indexName = "huahua_article",type = "articleEs")
public class ArticleEs implements Serializable {

    @Id
    private String id;//id是对应我们数据库中id
    @Field(index = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String title;//标题
    @Field(index = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String content;//文章正文
    private String state;//审核状态
}
