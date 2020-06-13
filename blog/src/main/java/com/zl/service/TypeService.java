package com.zl.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zl.mapper.BlogMapper;
import com.zl.mapper.TypeMapper;
import com.zl.pojo.Blog;
import com.zl.pojo.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TypeService {
    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private BlogMapper blogMapper;

    public void saveType(Type type){
        typeMapper.insertSelective(type);
    }

    public Type getType(Long id){
        return typeMapper.selectByPrimaryKey(id);
    }

    public Type getType(Type type){
        return typeMapper.selectOne(type);
    }

    public void updateType(Type type){
        typeMapper.updateByPrimaryKey(type);
    }

    public void removeType(Long id){
        typeMapper.deleteByPrimaryKey(id);
    }

    public List<Type> listType(){
        return typeMapper.selectAll();
    }

    public List<Type> listTypeTop(Integer top){
        List<Blog> blogs = blogMapper.selectTypeTop(top);
        List<Type> types = new ArrayList<>();
        for (Blog blog : blogs) {
            Type type = typeMapper.selectByPrimaryKey(blog.getTypeId());
            Example example = new Example(Blog.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("typeId",blog.getTypeId());
            List<Blog> blogList = blogMapper.selectByExample(example);
            type.setBlogs(blogList);
            types.add(type);
        }
        return types;
    }

    public PageInfo<Type> queryTypesByPage(Integer page,Integer rows){
        PageHelper.startPage(page,rows);
        List<Type> types = typeMapper.selectAll();
        PageInfo<Type> pageInfo = new PageInfo<>(types);
        return pageInfo;
    }
}
