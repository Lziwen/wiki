package com.ziwen.wiki.controller;

import com.ziwen.wiki.req.DocQueryReq;
import com.ziwen.wiki.req.DocSaveReq;
import com.ziwen.wiki.resp.CommonResp;
import com.ziwen.wiki.resp.DocQueryResp;
import com.ziwen.wiki.resp.PageResp;
import com.ziwen.wiki.service.DocService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/doc")
public class DocController {
    @Resource
    private DocService docService;

    @GetMapping("/all")
    public CommonResp all() {
        CommonResp<List<DocQueryResp>> resp = new CommonResp<>();
        List<DocQueryResp> list = docService.all();
        resp.setContent(list);
        return resp;
    }

    @GetMapping("/list")
    public CommonResp list(@Valid DocQueryReq docQueryReq) {
        CommonResp<PageResp<DocQueryResp>> resp = new CommonResp<>();
        PageResp<DocQueryResp> list = docService.list(docQueryReq);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp list(@Valid @RequestBody DocSaveReq docQueryReq) {
        CommonResp resp = new CommonResp<>();
        docService.save(docQueryReq);
        return resp;
    }

    @DeleteMapping("/delete/{idsStr}")
    public CommonResp delete(@PathVariable String idsStr) {
        CommonResp resp = new CommonResp<>();
        List<String> list = Arrays.asList(idsStr.split(","));
        docService.delete(list);
        return resp;
    }

}