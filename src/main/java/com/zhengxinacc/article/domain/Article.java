/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.article.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan>/a>
 * @version 1.0
 * @since 2018-10-28 12:53
 */
@Document(collection="article")
@Getter
@Setter
public class Article {
    @Id
    private String id;
    private String title;
    @Field(value = "content_url")
    private String contentUrl;
    private String digest;
    @Field(value = "datetime")
    private Long createDate;
    private String cover;
    private byte[] img;
    private String content;

}
