package com.zl.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "t_blog_tags")
@Entity
@Data
public class BlogTag implements Serializable {

    @Id
    @Column(name = "blog_id")
    private Long blogId;
    @Id
    @Column(name="tag_id")
    private Long tagId;
}
