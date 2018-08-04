package com.leyou.page.service;

/**
 * 静态页面生成文件处理业务接口
 *
 * @author: cooFive
 * @CreateDate: 2018/8/2 13:25
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface FileService {
    /**
     * 生成静态化页面
     *
     * @param spuId
     * @throws Exception
     */
    void createHtml(Long spuId) throws Exception;

    /**
     * 判断静态页面是否已经生成
     *
     * @param spuId
     * @return
     */
    boolean existsHtml(Long spuId);

    /**
     * 异步生成静态页面
     *
     * @param spuId
     */
    void syncCreateHtml(Long spuId);
}
