package com.zl.controller;

import com.github.pagehelper.PageInfo;
import com.zl.pojo.Blog;
import com.zl.pojo.Tag;
import com.zl.service.BlogService;
import com.zl.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;
    @GetMapping("/tags/{id}")
    public String types(@RequestParam(value = "page", defaultValue = "1") Integer page,
                        @RequestParam(value = "rows", defaultValue = "5") Integer rows,
                        @PathVariable(value = "id") Long id, Model model) {
        List<Tag> tags = tagService.listTagTop(10000);
        if (id == -1){
            id = tags.get(0).getId();
        }
        PageInfo<Blog> pageInfo = blogService.queryBlogByPage(page, rows, id);
        model.addAttribute("tags",tags);
        model.addAttribute("page",pageInfo);
        model.addAttribute("activeTagId",id);
        return "tags";
    }
}
