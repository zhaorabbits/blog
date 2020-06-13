package com.zl.controller;

import com.github.pagehelper.PageInfo;
import com.zl.pojo.Blog;
import com.zl.pojo.Type;
import com.zl.service.BlogService;
import com.zl.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;
    @GetMapping("/types/{id}")
    public String types(@RequestParam(value = "page", defaultValue = "1") Integer page,
                        @RequestParam(value = "rows", defaultValue = "5") Integer rows,
                        @PathVariable Long id, Model model) {
        List<Type> types = typeService.listTypeTop(10000);
        if (id == -1){
            id = types.get(0).getId();
        }
        PageInfo<Blog> pageInfo = blogService.queryBlogByPage(page, rows, null, id, true, null);
        model.addAttribute("types",types);
        model.addAttribute("page",pageInfo);
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
