package com.zl.controller;

import com.zl.pojo.Blog;
import com.zl.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model){
        Map<String, List<Blog>> map = blogService.archiveBlog();
        model.addAttribute("archiveMap",map);
        model.addAttribute("blogCount",blogService.selectTotal());
        return "archives";
    }
}
