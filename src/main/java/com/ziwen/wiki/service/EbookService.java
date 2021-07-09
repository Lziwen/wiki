package com.ziwen.wiki.service;

import com.ziwen.wiki.domain.Ebook;
import com.ziwen.wiki.domain.EbookExample;
import com.ziwen.wiki.mapper.EbookMapper;
import com.ziwen.wiki.req.EbookReq;
import com.ziwen.wiki.resp.EbookResp;
import com.ziwen.wiki.util.CopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {

    @Resource
    private EbookMapper ebookMapper;

    public List<EbookResp> list(EbookReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        criteria.andNameLike("%" + req.getName() + "%");
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);
//        List<EbookResp> respList = new ArrayList<>();
//        for (Ebook ebook : ebookList) {
//            EbookResp ebookResp = new EbookResp();
//            BeanUtils.copyProperties(ebook, ebookResp);
//            respList.add(ebookResp);
//        }
        // 列表复制
        List<EbookResp> list = CopyUtil.copyList(ebookList, EbookResp.class);


        return list;
    }
}
