package com.zl.mapper;

import com.zl.pojo.BlogTag;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BlogTagMapper extends tk.mybatis.mapper.common.Mapper<BlogTag> {
    @Select("SELECT * FROM t_blog_tags GROUP BY tag_id ORDER BY COUNT(tag_id) DESC LIMIT #{top}")
    @Results(id = "blogTagMap",value = {
            @Result(column = "blog_id",property = "blogId"),
            @Result(column = "tag_id",property = "tagId"),
    })
    List<BlogTag> selectTagTop(Integer top);

    @Select("select* from t_blog_tags where tag_id = #{id}")
    @ResultMap("blogTagMap")
    List<BlogTag> selectByTagId(Long id);
}
