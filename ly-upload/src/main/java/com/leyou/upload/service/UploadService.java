package com.leyou.upload.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传处理业务接口
 *
 * @author: cooFive
 * @CreateDate: 2018/7/22 14:42
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface UploadService {
    /**
     * 图片上传
     *
     * @param file
     * @return
     */
    String imageUpload(MultipartFile file);
}
