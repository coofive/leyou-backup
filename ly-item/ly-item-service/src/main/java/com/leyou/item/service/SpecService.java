package com.leyou.item.service;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;

import java.util.List;

/**
 * 商品规格处理接口
 *
 * @author: cooFive
 * @CreateDate: 2018/7/22 21:34
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface SpecService {
    /**
     * 通过商品分类id查询规格组信息
     *
     * @param cid
     * @return
     */
    List<SpecGroup> querySpecGroupByCid(Long cid);

    /**
     * 通过规格组id查询规格参数
     *
     * @param group_id
     * @param cid
     * @return
     */
    List<SpecParam> querySpecParamByGid(Long group_id, Long cid);
}
