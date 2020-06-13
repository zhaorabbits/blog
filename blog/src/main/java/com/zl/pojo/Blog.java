package com.zl.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "t_blog")
@Data
public class Blog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //标题
    private String title;

    //内容
    private String content;

    //首图
    private String firstPicture;

    //标记
    private String flag;

    //浏览次数
    private Integer views;

    //描述
    private String description;

    //赞赏是否开启
    private Boolean isAppreciation;

    //转载时否开启
    private Boolean isShare;

    //评论是否开启
    private Boolean isComment;

    //是否发布
    private Boolean isPublish;

    //是否推荐
    private Boolean isRecommend;

    private Date createTime;

    private Date updateTime;

    @Transient
    private String create;
    @Transient
    private String update;

    private Long typeId;

    private Long userId;
    @Transient
    private String tagIds;
    @Transient
    private Type type;
    @Transient
    private List<Tag> tags = new ArrayList<>();
    @Transient
    private User user;
    @Transient
    private List<Comment> comments = new ArrayList<>();
    public Blog() {
    }

    public void setCreateTime(Date createTime) {
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(createTime);
        this.createTime = createTime;
        this.create = date;
    }

    public void setUpdateTime(Date updateTime) {
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(updateTime);
        this.updateTime = updateTime;
        this.update = date;
    }
}
