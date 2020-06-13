package com.zl.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zl.mapper.BlogTagMapper;
import com.zl.mapper.TagMapper;
import com.zl.pojo.Blog;
import com.zl.pojo.BlogTag;
import com.zl.pojo.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional
public class BlogTagService {
    @Autowired
    private BlogTagMapper blogTagMapper;

    public void saveBlogTag(BlogTag blogTag){
        blogTagMapper.insertSelective(blogTag);
    }

    public List<BlogTag> getBlogTag(BlogTag blogTag){
        Example example = new Example(BlogTag.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("blogId",blogTag.getBlogId());
        return blogTagMapper.selectByExample(example);
    }

    public void removeBlogTag(Long id){
        Example example = new Example(BlogTag.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("blogId",id);
        blogTagMapper.deleteByExample(example);
    }



}
