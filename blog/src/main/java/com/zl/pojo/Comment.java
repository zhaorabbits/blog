package com.zl.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@Entity
@Table(name = "t_comment")
@Data
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickName;

    private String email;

    private String content;
    //头像
    private String avatar;

    private Date createTime;

    private Long blogId;

    private Long parentCommentId;

    private Boolean adminComment;

    @Transient
    private Blog blog;

    @Transient
    private List<Comment> replyComments = new ArrayList<>();

    @Transient
    private Comment parentComment;
    public Comment() {
    }


}
