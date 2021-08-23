package com.ziwen.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ziwen.wiki.domain.Category;
import com.ziwen.wiki.domain.CategoryExample;
import com.ziwen.wiki.mapper.CategoryMapper;
import com.ziwen.wiki.req.CategoryQueryReq;
import com.ziwen.wiki.req.CategorySaveReq;
import com.ziwen.wiki.resp.CategoryQueryResp;
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
public class CategoryService {
    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<CategoryQueryResp> list(CategoryQueryReq req) {
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
//        if (!ObjectUtils.isEmpty(req.getName())) {
//            criteria.andNameLike("%" + req.getName() + "%");
//        }
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        LOG.info("Total：{}", pageInfo.getTotal());
        LOG.info("Pages：{}", pageInfo.getPages());

//        List<CategoryResp> respList = new ArrayList<>();
//        for (Category category : categoryList) {
//            CategoryResp categoryResp = new CategoryResp();
//            BeanUtils.copyProperties(category, categoryResp);
//            respList.add(categoryResp);
//        }

        // 列表复制
        List<CategoryQueryResp> list = CopyUtil.copyList(categoryList, CategoryQueryResp.class);

        PageResp<CategoryQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    /**
     * Save
     */
    public void save(CategorySaveReq req) {
        Category category = CopyUtil.copy(req, Category.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            // add
            category.setId(snowFlake.nextId());
            categoryMapper.insert(category);
        } else {
            // update
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    /**
     * Delete
     */
    public void delete(Long id){
        categoryMapper.deleteByPrimaryKey(id);
    }
}
