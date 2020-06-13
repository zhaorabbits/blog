package com.zl.controller;

import com.zl.pojo.Tag;
import com.zl.pojo.Type;
import com.zl.service.TagService;
import com.zl.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String queryTagsByPage(@RequestParam(value = "page",defaultValue = "1") Integer page,@RequestParam(value = "rows",defaultValue = "5") Integer rows,Model model){
        model.addAttribute("page",tagService.queryTagsByPage(page,rows));
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String saveTagPage(){
        return "admin/tags-input";
    }

    @GetMapping("/tags/edit")
    public String editTagPage(@RequestParam String name, Model model){
        Tag tag = new Tag();
        tag.setName(name);
        model.addAttribute("oldTag",tagService.getTag(tag));
        return "admin/tags-input";
    }

    @PostMapping("/tags/save")
    public String saveTag(Tag tag, RedirectAttributes attributes){
        if (tagService.getTag(tag) != null && !  StringUtils.isEmpty(tag.getName())){
            attributes.addFlashAttribute("message","添加失败");
            return "redirect:/admin/types/input";
        }else{
            tagService.saveTag(tag);
            attributes.addFlashAttribute("message","添加成功");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/edit")
    public String editTag(Tag tag, RedirectAttributes attributes){
        if (StringUtils.isEmpty(tag.getName())){
            attributes.addFlashAttribute("message","修改失败");
            return "redirect:/admin/tags/input";
        }else{
            tagService.updateTag(tag);
            attributes.addFlashAttribute("message","修改成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/delete")
    public String deleteTag(@RequestParam Long id,RedirectAttributes attributes){
        tagService.removeTag(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }



}
