package com.chenlong.common.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class OssTemplate {

    // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
    private String endpoint = "oss-cn-beijing.aliyuncs.com";
    // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
    private String accessKeyId = "LTAI5tCXM35RCbkhrQf75vSB";
    private String accessKeySecret = "yjbiwrPIUnEvdr4W3PIiCGsE5ZYYDO";
    // aliyun bucket存储对象(数据库)的名称
    private String bucketName = "0416-chenlong";
    // bucket访问路径 https://0416-chenlong.oss-cn-beijing.aliyuncs.com/img/4.jpg
    private String bucketUrl = "https://0416-chenlong.oss-cn-beijing.aliyuncs.com/";

    public String upload(InputStream inputStream, String fileName) throws FileNotFoundException {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        // inputStream = new FileInputStream("C:\\Users\\chenlong\\Pictures\\Saved Pictures\\4.jpg");
        // 填写Bucket名称和Object完整路径。Object完整路径中不能包含Bucket名称。
        // 要求存储的路径以年月日进行区分
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String folderName = dateFormat.format(new Date());
        // 避免文件名重复
        String newFileName = UUID.randomUUID().toString().replace("-", "")
                + fileName;
        ossClient.putObject(bucketName, "img/"+folderName+"/"+newFileName, inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();
        //把文件访问路径返回
        return bucketUrl+"img/"+folderName+"/"+newFileName;

    }

}
