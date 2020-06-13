package com.zl.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zl.handler.NotFoundException;
import com.zl.mapper.BlogMapper;
import com.zl.mapper.TagMapper;
import com.zl.mapper.TypeMapper;
import com.zl.mapper.UserMapper;
import com.zl.pojo.Blog;
import com.zl.pojo.Tag;
import com.zl.pojo.Type;
import com.zl.pojo.User;
import com.zl.util.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.util.*;

@Service
@Transactional
public class BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private TagMapper tagMapper;

    public Blog getBlog(Long id) {
        return blogMapper.selectByPrimaryKey(id);
    }

    public PageInfo<Blog> queryBlogByPage(Integer page, Integer rows, String title, Long typeId, Boolean isRecommend,String query) {
        Example example = new Example(Blog.class);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(title)) {
            criteria.andLike("title", "%" + title + "%");
        }
        if (typeId != null) {
            criteria.andEqualTo("typeId", typeId);
        }
        if (!isRecommend) {
            criteria.andEqualTo("isRecommend", isRecommend);
        }
        if (!StringUtils.isEmpty(query)){
            criteria.andLike("title","%"+query+"%").orLike("content","%"+query+"%");
        }
        PageHelper.startPage(page, rows);
        List<Blog> blogs = blogMapper.selectByExample(example);
        blogs.forEach(blog -> {
            Type type = typeMapper.selectByPrimaryKey(blog.getTypeId());
            blog.setType(type);
            User user = userMapper.selectByPrimaryKey(blog.getUserId());
            blog.setUser(user);
        });
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        return pageInfo;
    }


    public void saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        blogMapper.insertSelective(blog);
    }

    public void updateBlog(Blog blog) {
        blog.setUpdateTime(new Date());
        blogMapper.updateByPrimaryKeySelective(blog);
    }

    public void removeBlog(Long id) {
        blogMapper.deleteByPrimaryKey(id);
    }

    public List<Blog> listRecommendBlogTop(Integer top){
        Example example = new Example(Blog.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isRecommend",true);
        example.setOrderByClause("update_time"+" "+"desc"+" "+"limit "+top);

        List<Blog> blogList = blogMapper.selectByExample(example);
        return blogList;
    }

    public Blog getAndConvent(Long id){
        Blog blog = blogMapper.selectByPrimaryKey(id);
        if (blog==null){
            throw new NotFoundException("该博客不存在");
        }
        String content = blog.getContent();
        String s = MarkdownUtils.markdownToHtmlExtensions(content);
        blog.setContent(s);
        blogMapper.updateViews(id);
        return blog;
    }

    public PageInfo<Blog> queryBlogByPage(Integer page, Integer rows, Long tagId) {
        PageHelper.startPage(page,rows);
        List<Blog> blogs = blogMapper.selectBlogByTagId(tagId);
        blogs.forEach(blog -> {
            Type type = typeMapper.selectByPrimaryKey(blog.getTypeId());
            blog.setType(type);
            User user = userMapper.selectByPrimaryKey(blog.getUserId());
            blog.setUser(user);
            List<Tag> tags = tagMapper.queryTagsByBlogId(blog.getId());
            blog.setTags(tags);
        });
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        return pageInfo;
    }

    public Map<String,List<Blog>> archiveBlog(){
        LinkedHashMap<String,List<Blog>> map = new LinkedHashMap<>();
        List<String> groupYear = blogMapper.findGroupYear();
        for (String year : groupYear) {
            List<Blog> blogList = blogMapper.selectBlogByYear(year);
            map.put(year,blogList);
        }
        return map;
    }

    public Integer selectTotal(){
        return blogMapper.selectTotal();
    }
}
