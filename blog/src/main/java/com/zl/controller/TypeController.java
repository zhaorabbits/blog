package com.zl.controller;

import com.zl.pojo.Type;
import com.zl.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.websocket.server.PathParam;

@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String queryTypesByPage(@RequestParam(value = "page",defaultValue = "1") Integer page,@RequestParam(value = "rows",defaultValue = "5") Integer rows,Model model){
        model.addAttribute("page",typeService.queryTypesByPage(page,rows));
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String saveTypePage(){
        return "admin/types-input";
    }

    @GetMapping("/types/edit")
    public String editTypePage(@RequestParam String name, Model model){
        Type type = new Type();
        type.setName(name);
        model.addAttribute("oldType",typeService.getType(type));
        return "admin/types-input";
    }

    @PostMapping("/types/save")
    public String saveType(Type type, RedirectAttributes attributes){
        if (typeService.getType(type) != null && !  StringUtils.isEmpty(type.getName())){
            attributes.addFlashAttribute("message","添加失败");
            return "redirect:/admin/types/input";
        }else{
            typeService.saveType(type);
            attributes.addFlashAttribute("message","添加成功");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/edit")
    public String editType(Type type, RedirectAttributes attributes){
        if (StringUtils.isEmpty(type.getName())){
            attributes.addFlashAttribute("message","修改失败");
            return "redirect:/admin/types/input";
        }else{
            typeService.updateType(type);
            attributes.addFlashAttribute("message","修改成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/delete")
    public String deleteType(@RequestParam Long id,RedirectAttributes attributes){
        typeService.removeType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }



}
