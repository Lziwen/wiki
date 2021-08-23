package com.ziwen.wiki.controller;

import com.ziwen.wiki.req.CategoryQueryReq;
import com.ziwen.wiki.req.CategorySaveReq;
import com.ziwen.wiki.resp.CommonResp;
import com.ziwen.wiki.resp.CategoryQueryResp;
import com.ziwen.wiki.resp.PageResp;
import com.ziwen.wiki.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @GetMapping("/all")
    public CommonResp all() {
        CommonResp<List<CategoryQueryResp>> resp = new CommonResp<>();
        List<CategoryQueryResp> list = categoryService.all();
        resp.setContent(list);
        return resp;
    }

    @GetMapping("/list")
    public CommonResp list(@Valid CategoryQueryReq categoryQueryReq) {
        CommonResp<PageResp<CategoryQueryResp>> resp = new CommonResp<>();
        PageResp<CategoryQueryResp> list = categoryService.list(categoryQueryReq);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp list(@Valid @RequestBody CategorySaveReq categoryQueryReq) {
        CommonResp resp = new CommonResp<>();
        categoryService.save(categoryQueryReq);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp list(@PathVariable Long id) {
        CommonResp resp = new CommonResp<>();
        categoryService.delete(id);
        return resp;
    }

}
