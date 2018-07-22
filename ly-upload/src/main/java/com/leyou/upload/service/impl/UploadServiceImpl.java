package com.leyou.upload.service.impl;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.leyou.upload.service.UploadService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 文件上传处理业务实现
 *
 * @author: cooFive
 * @CreateDate: 2018/7/22 14:51
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Service
public class UploadServiceImpl implements UploadService {
    // 支持的文件类型
    private static final List<String> suffixes = Arrays.asList("image/png", "image/jpeg");

    @Autowired
    private FastFileStorageClient storageClient;

    @Override
    public String imageUpload(MultipartFile file) {
        try {
            // 1. 图片信息校验
            // 1.1 校验文件类型
            String type = file.getContentType();
            if (!suffixes.contains(type)) {
                System.out.println("上传失败，文件类型不匹配：{}" + type);
                return null;
            }
            // 1.2 校验图片内容
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage == null) {
                System.out.println("上传失败，文件内容不符合要求");
                return null;
            }
            // 2. 保存图片
            // 2.1 生成保存目录
//            File dir = new File("C:\\Users\\Administrator\\Desktop\\Portable\\nginx-1.15.1\\html");
//
//
//            if (!dir.exists()) {
//                dir.mkdirs();
//            }
            // 2.1 获取文件后缀名
            String extension = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            StorePath storePath = this.storageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);

//            file.transferTo(new File(dir, file.getOriginalFilename()));

//            String url = "http://localhost/" + file.getOriginalFilename();
            // 2.3 拼接图片地址
            return "http://image.leyou.com/" + storePath.getFullPath();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
