package com.hereWeGo.manager.service.impl;

import com.hereWeGo.common.result.FileResult;
import com.hereWeGo.manager.service.UploadService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * 上传服务实现类
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Service
public class UploadServiceImpl implements UploadService {

    @Override
    public FileResult upload(InputStream inputStream, String fileName) {
        FileResult fileResult = new FileResult();
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huadong());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "FqfVHAYj2owVcGpkbZORjj71l9Ye-sxIBkf0wQzD";
        String secretKey = "a2JFyP0O4u4YZ6lD62-SgWHO1dj9lEiDUd6wO1_J";
        String bucket = "zhangheng-win";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = fileName;
        try {
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(inputStream,key,upToken,null, null);
                //解析上传成功的结果
                if (response.statusCode==200){
                    fileResult.setSuccess("success");
                    fileResult.setMessage("上传成功");
                    fileResult.setFileUrl("r2p51gk8p.hd-bkt.clouddn.com/"+fileName);
                    return fileResult;
                }else {
                    fileResult.setError("error");
                    fileResult.setMessage("上传失败");
                    return fileResult;
                }
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                fileResult.setError("error");
                fileResult.setMessage("上传失败");
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }
        return fileResult;
    }
}
