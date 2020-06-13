package com.zl.controller;

import com.github.pagehelper.PageInfo;
import com.zl.pojo.*;
import com.zl.service.BlogService;
import com.zl.service.BlogTagService;
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
import org.springframework.web.servlet.config.annotation.RedirectViewControllerRegistration;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogTagService blogTagService;

    @GetMapping("/blogs")
    public String queryBlogByPage(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                  @RequestParam(value = "rows",defaultValue = "5") Integer rows, Model model){
        PageInfo<Blog> pageInfo = blogService.queryBlogByPage(page, rows, null, null, true,null);
        List<Type> types = typeService.listType();
        model.addAttribute("page",pageInfo);
        model.addAttribute("types",types);
        return "admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String queryBlogByPage(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                  @RequestParam(value = "rows",defaultValue = "5") Integer rows,
                                  @RequestParam(value = "title") String title,
                                  @RequestParam(value = "typeId") Long typeId,
                                  @RequestParam(value = "isRecommend") Boolean isRecommend, Model model){
        PageInfo<Blog> pageInfo = blogService.queryBlogByPage(page, rows, title, typeId, isRecommend,null);
        List<Type> types = typeService.listType();
        model.addAttribute("page",pageInfo);
        model.addAttribute("types",types);
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String saveBlogPage(Model model){
        setTypeAndTag(model);
        Blog blog = new Blog();
        blog.setFlag("原创");
        model.addAttribute("oldBlog",blog);
        return "admin/blogs-input";
    }

    @PostMapping("/blogs/save")
    public String saveBlog(Blog blog, HttpSession session, RedirectAttributes attributes){
        User user = (User) session.getAttribute("user");
        if (blog.getId()!=null){
            blogTagService.removeBlogTag(blog.getId());
        }
        blog.setUser(user);
        blog.setUserId(user.getId());
        String tagIds = blog.getTagIds();
        if (!StringUtils.isEmpty(tagIds)){
            String[] tagIdsArr = tagIds.split(",");
            List<Tag> tags = new ArrayList<>();
            for (String tagId : tagIdsArr) {
                tags.add(tagService.getTag(Long.valueOf(tagId)));
                BlogTag blogTag = new BlogTag();
            }
            blog.setTags(tags);
        }
        if (blog.getId()==null){
            blogService.saveBlog(blog);
        }else{
            blogService.updateBlog(blog);
        }
        if (!StringUtils.isEmpty(tagIds)){
            String[] ids = tagIds.split(",");
            for (String id : ids) {
                BlogTag blogTag = new BlogTag();
                blogTag.setTagId(Long.valueOf(id));
                blogTag.setBlogId(blog.getId());
                blogTagService.saveBlogTag(blogTag);
            }
        }
        attributes.addFlashAttribute("message","操作成功");
        return "redirect:/admin/blogs";
    }



    @GetMapping("/blogs/edit")
    public String editBlogPage(@RequestParam Long id,Model model){
        setTypeAndTag(model);
        model.addAttribute("oldBlog",blogService.getBlog(id));
        BlogTag blogTag = new BlogTag();
        blogTag.setBlogId(id);
        List<BlogTag> blogTags = blogTagService.getBlogTag(blogTag);
        if (!blogTags.isEmpty()){
            StringBuffer tags = new StringBuffer();
            for (BlogTag tag : blogTags) {
                tags.append(tag.getTagId()+",");
            }
            tags.deleteCharAt(tags.length()-1);
            model.addAttribute("oldBlogTags",tags);
        }
        return "admin/blogs-input";
    }

    @GetMapping("/blogs/delete")
    public String removeBlog(@RequestParam Long id,RedirectAttributes attributes){
        blogTagService.removeBlogTag(id);
        blogService.removeBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/blogs";
    }


    public void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
    }
}
