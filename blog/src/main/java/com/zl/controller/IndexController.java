package com.zl.controller;

import com.zl.pojo.Blog;
import com.zl.service.BlogService;
import com.zl.service.TagService;
import com.zl.service.TypeService;
import com.zl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;
    @GetMapping("/")
    public String indexPage(@RequestParam(value = "page",defaultValue = "1") Integer page,
                            @RequestParam(value = "rows",defaultValue = "4") Integer rows, Model model){
        model.addAttribute("page",blogService.queryBlogByPage(page,rows,null,null,true,null));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(8));
        return "index";
    }

    @PostMapping("/search")
    public String search(@RequestParam(value = "page",defaultValue = "1") Integer page,
                         @RequestParam(value = "rows",defaultValue = "5") Integer rows,@RequestParam String query, Model model){
        model.addAttribute("page",blogService.queryBlogByPage(page,rows,null,null,true,query));
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/blog")
    public String showblog(@RequestParam Long id,Model model){
        Blog blog = blogService.getAndConvent(id);
        blog.setUser(userService.getUser(blog.getUserId()));
        blog.setTags(tagService.queryTagsByBlogId(blog.getId()));
        blog.setType(typeService.getType(blog.getTypeId()));
        model.addAttribute("blog",blog);
        return "blog";
    }

    @GetMapping("/footer/newBlog")
    public String newBlogs(Model model){
        model.addAttribute("newBlogs",blogService.listRecommendBlogTop(3));
        return "_fragments :: newBlogList";

    }
}
