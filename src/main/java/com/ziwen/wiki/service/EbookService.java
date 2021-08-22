package com.ziwen.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ziwen.wiki.domain.Ebook;
import com.ziwen.wiki.domain.EbookExample;
import com.ziwen.wiki.mapper.EbookMapper;
import com.ziwen.wiki.req.EbookQueryReq;
import com.ziwen.wiki.req.EbookSaveReq;
import com.ziwen.wiki.resp.EbookQueryResp;
import com.ziwen.wiki.resp.PageResp;
import com.ziwen.wiki.util.CopyUtil;
import com.ziwen.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {
    private static final Logger LOG = LoggerFactory.getLogger(EbookService.class);

    @Resource
    private EbookMapper ebookMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<EbookQueryResp> list(EbookQueryReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        PageInfo<Ebook> pageInfo = new PageInfo<>(ebookList);
        LOG.info("Total：{}", pageInfo.getTotal());
        LOG.info("Pages：{}", pageInfo.getPages());

//        List<EbookResp> respList = new ArrayList<>();
//        for (Ebook ebook : ebookList) {
//            EbookResp ebookResp = new EbookResp();
//            BeanUtils.copyProperties(ebook, ebookResp);
//            respList.add(ebookResp);
//        }

        // 列表复制
        List<EbookQueryResp> list = CopyUtil.copyList(ebookList, EbookQueryResp.class);

        PageResp<EbookQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    /**
     * Save
     */
    public void save(EbookSaveReq req) {
        Ebook ebook = CopyUtil.copy(req, Ebook.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            // add
            ebook.setId(snowFlake.nextId());
            ebookMapper.insert(ebook);
        } else {
            // update
            ebookMapper.updateByPrimaryKey(ebook);
        }
    }

    /**
     * Delete
     */
    public void delete(Long id){
        ebookMapper.deleteByPrimaryKey(id);
    }
}
