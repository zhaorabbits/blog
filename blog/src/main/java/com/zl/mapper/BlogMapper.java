package com.zl.mapper;

import com.zl.pojo.Blog;
import com.zl.pojo.Type;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface BlogMapper extends tk.mybatis.mapper.common.Mapper<Blog> {
    @Select("SELECT * FROM t_blog GROUP BY type_id ORDER BY COUNT(type_id) DESC LIMIT #{top}")
    @Results(id = "blogMap", value = {
            @Result(column = "id", property = "id", id = true),
            @Result(column = "content", property = "content"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "first_picture", property = "firstPicture"),
            @Result(column = "flag", property = "flag"),
            @Result(column = "is_appreciation", property = "isAppreciation"),
            @Result(column = "is_comment", property = "isComment"),
            @Result(column = "is_publish", property = "isPublish"),
            @Result(column = "is_recommend", property = "isRecommend"),
            @Result(column = "is_share", property = "isShare"),
            @Result(column = "title", property = "title"),
            @Result(column = "update_time", property = "updateTime"),
            @Result(column = "views", property = "views"),
            @Result(column = "type_id", property = "typeId"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "description", property = "description"),
    })
    List<Blog> selectTypeTop(Integer top);

    @Update("UPDATE t_blog b SET b.views = b.views + 1 WHERE id = #{id}")
    void updateViews(@Param("id") Long id);

    @Select("SELECT * FROM t_blog WHERE id IN (SELECT blog_id FROM t_blog_tags WHERE tag_id = #{tagId})")
    @ResultMap(value = "blogMap")
    List<Blog> selectBlogByTagId(Long tagId);

    @Select("SELECT DATE_FORMAT(b.update_time,'%Y') AS YEAR FROM t_blog b GROUP BY YEAR ORDER BY YEAR ASC")
    List<String> findGroupYear();

    @Select("SELECT * FROM t_blog b WHERE DATE_FORMAT(b.update_time,'%Y') = #{year}")
    @ResultMap(value = "blogMap")
    List<Blog> selectBlogByYear(String year);

    @Select("SELECT COUNT(*) FROM t_blog")
    Integer selectTotal();
}
