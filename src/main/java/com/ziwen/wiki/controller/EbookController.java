package com.ziwen.wiki.controller;

import com.ziwen.wiki.req.EbookQueryReq;
import com.ziwen.wiki.req.EbookSaveReq;
import com.ziwen.wiki.resp.CommonResp;
import com.ziwen.wiki.resp.EbookQueryResp;
import com.ziwen.wiki.resp.PageResp;
import com.ziwen.wiki.service.EbookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/ebook")
public class EbookController {
    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp list(@Valid EbookQueryReq ebookQueryReq) {
        CommonResp<PageResp<EbookQueryResp>> resp = new CommonResp<>();
        PageResp<EbookQueryResp> list = ebookService.list(ebookQueryReq);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp list(@Valid @RequestBody EbookSaveReq ebookQueryReq) {
        CommonResp resp = new CommonResp<>();
        ebookService.save(ebookQueryReq);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp list(@PathVariable Long id) {
        CommonResp resp = new CommonResp<>();
        ebookService.delete(id);
        return resp;
    }

}
