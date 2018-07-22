package com.leyou.upload.controller;

import com.leyou.upload.service.UploadService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传
 *
 * @author: cooFive
 * @CreateDate: 2018/7/22 14:05
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    /**
     * 上传图片功能
     *
     * @param file
     * @return
     */
    @PostMapping("image")
    public ResponseEntity<String> imageUpload(@RequestParam("file") MultipartFile file) {
        String url = this.uploadService.imageUpload(file);

        if (StringUtils.isBlank(url)) {
            // url为空则上传失败
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // 返回200,并携带url路径
        return ResponseEntity.ok(url);
    }
}
