package com.leyou.item.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.controller.GoodsController;
import com.leyou.item.mapper.*;
import com.leyou.item.pojo.*;
import com.leyou.item.service.GoodsService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品处理业务实现
 *
 * @author: cooFive
 * @CreateDate: 2018/7/23 16:04
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    private final Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 商品分页查询
     *
     * @param page
     * @param rows
     * @param saleable
     * @param key
     * @return
     */
    @Override
    public PageResult<Spu> querySpuByPage(Integer page, Integer rows, Boolean saleable, String key) {
        // 设置分页条件
        PageHelper.startPage(page, rows);

        // 创建查询模板
        Example example = new Example(Spu.class);
        // 是否筛选
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(key)) {
            criteria.andLike("title", "%" + key + "%");
        }

        // 是否上架
        if (saleable != null) {
            criteria.andEqualTo("saleable", saleable);
        }

        logger.debug("打印sql语句", example);
        // 查询
        // 1. 先查询spu
        List<Spu> spuList = spuMapper.selectByExample(example);
        // 2. 处理商品分类和品牌查询
        handleCategoryNameAndBrandName(spuList);

        // 封装PageResult
        PageInfo<Spu> spuPage = new PageInfo<>(spuList);

        return new PageResult<>(spuPage.getTotal(), spuPage.getList());
    }

    /**
     * 保存商品数据
     *
     * @param spu
     * @return
     */
    @Override
    @Transactional
    public Boolean saveGoods(Spu spu) {
        // 补全spu属性内容
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(spu.getCreateTime());
        spu.setSaleable(false);
        spu.setValid(true);

        // 保存spu数据，并校验
        int count = spuMapper.insertSelective(spu);
        if (count == 0) {
            logger.error("保存spu数据失败", spu);
            throw new RuntimeException("保存spu数据失败");
        }
        // 保存spuDetail数据
        // 注意：需要和spu的id保持一致
        SpuDetail spuDetail = spu.getSpuDetail();
        spuDetail.setSpuId(spu.getId());
        count = spuDetailMapper.insertSelective(spuDetail);
        if (count == 0) {
            logger.error("保存spuDetail数据失败", spuDetail);
            throw new RuntimeException("保存spuDetail数据失败");
        }
        // 保存skus数据
        // 需要设置skus里面的spuId
        spu.getSkus().forEach(sku -> {
            sku.setCreateTime(spu.getCreateTime());
            sku.setLastUpdateTime(spu.getLastUpdateTime());
            sku.setSpuId(spu.getId());

            // 依次保存skus数据
            int tmp = skuMapper.insertSelective(sku);
            if (tmp == 0) {
                logger.error("保存Sku数据失败", sku);
                throw new RuntimeException("保存Sku数据失败");
            }

            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            // 再去保存库存
            tmp = stockMapper.insertSelective(stock);
            if (tmp == 0) {
                logger.error("保存Sku数据失败", stock);
                throw new RuntimeException("保存Sku数据失败");
            }

        });
        return true;
    }

    /**
     * 处理商品分类和品牌查询
     *
     * @param spuList
     */
    private void handleCategoryNameAndBrandName(List<Spu> spuList) {
        spuList.forEach((spu) -> {
            // 查询Category,将3个cname用/顺序拼接
            List<Long> cidList = Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3());
            List<Category> categories = categoryMapper.selectByIdList(cidList);
            String cname = categories.stream().map(Category::getName).collect(Collectors.joining("/"));
            spu.setCname(cname);

            // 查询brand，将只需要name
            Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
            spu.setBname(brand.getName());
        });
    }
}
