package com.zl.mapper;

import com.zl.pojo.BlogTag;
import com.zl.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TagMapper extends tk.mybatis.mapper.common.Mapper<com.zl.pojo.Tag> {
    @Select("SELECT * FROM t_tag WHERE id IN (SELECT tag_id FROM t_blog_tags WHERE blog_id = #{blogId})")
    public List<Tag> queryTagsByBlogId(Long blogId);
}
