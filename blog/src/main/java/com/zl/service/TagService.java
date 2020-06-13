package com.zl.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zl.mapper.BlogMapper;
import com.zl.mapper.BlogTagMapper;
import com.zl.mapper.TagMapper;
import com.zl.pojo.Blog;
import com.zl.pojo.BlogTag;
import com.zl.pojo.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TagService {
    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private BlogTagMapper blogTagMapper;

    public void saveTag(Tag tag) {
        tagMapper.insertSelective(tag);
    }

    public Tag getTag(Long id) {
        return tagMapper.selectByPrimaryKey(id);
    }

    public Tag getTag(Tag tag) {
        return tagMapper.selectOne(tag);
    }

    public void updateTag(Tag tag) {
        tagMapper.updateByPrimaryKey(tag);
    }

    public void removeTag(Long id) {
        tagMapper.deleteByPrimaryKey(id);
    }

    public List<Tag> listTag() {
        return tagMapper.selectAll();
    }

    public List<Tag> listTagTop(Integer top) {
        List<BlogTag> blogTags = blogTagMapper.selectTagTop(top);
        List<Tag> tagList = new ArrayList<>();
        for (BlogTag blogTag : blogTags) {
            Tag tag = tagMapper.selectByPrimaryKey(blogTag.getTagId());
            List<BlogTag> blogTagList = blogTagMapper.selectByTagId(blogTag.getTagId());
            List<Blog> blogList = new ArrayList<>();
            for (BlogTag blogTag1 : blogTagList) {
                Blog blog = blogMapper.selectByPrimaryKey(blogTag1.getBlogId());
                blogList.add(blog);
            }
            tag.setBlogs(blogList);
            tagList.add(tag);
        }
        return tagList;
    }

    public PageInfo<Tag> queryTagsByPage(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<Tag> tags = tagMapper.selectAll();
        PageInfo<Tag> pageInfo = new PageInfo<>(tags);
        return pageInfo;
    }

    public List<Tag> queryTagsByBlogId(Long blogId){
        List<Tag> tags = tagMapper.queryTagsByBlogId(blogId);
        return tags;
    }
}
