package com.wenhui.integration.cos;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.UploadResult;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;
import com.wenhui.core.base.utils.common.util.BusinessException;
import com.wenhui.core.base.utils.common.util.DateUtils;
import com.wenhui.core.core.biz.ErrorCode;
import com.wenhui.project.biz.util.IDGenerator;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 @author 天赋吉运-bms
 @DESCRIPTION 上传对象
 @create 2023/2/22
*/
public class CosUpload {
    public static PutObjectResult upload(String key, InputStream inputStream) {
        // 调用 COS 接口之前必须保证本进程存在一个 COSClient 实例，如果没有则创建


        // 详细代码参见本页：简单操作 -> 创建 COSClient
        TmpSecretKeyConstant tmpSecretKey = new TmpSecretKey().getTmpSecretKey();
        if (Objects.isNull(tmpSecretKey)) {
            new BusinessException(String.valueOf(ErrorCode.SERVER_ERROR), "服务器异常，请重新提交。");
        }
        PutObjectResult putObjectResult = null;
        COSClient cosClient = new COSClientCreate().getCosClient(tmpSecretKey);
        try {
// 存储桶的命名格式为 BucketName-APPID，此处填写的存储桶名称必须为此格式
            String bucketName = CosUploadConstant.BUCKET;
            // 对象键(Key)是对象在存储桶中的唯一标识。

            // 这里创建一个 ByteArrayInputStream 来作为示例，实际中这里应该是您要上传的 InputStream 类型的流
//        long inputStreamLength = 1024 * 1024;
//        byte data[] = new byte[(int) inputStreamLength];
//        InputStream inputStream = new ByteArrayInputStream(data);

            ObjectMetadata objectMetadata = new ObjectMetadata();
// 上传的流如果能够获取准确的流长度，则推荐一定填写 content-length
// 如果确实没办法获取到，则下面这行可以省略，但同时高级接口也没办法使用分块上传了
//objectMetadata.setContentLength(inputStreamLength);

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, objectMetadata);

// 设置存储类型（如有需要，不需要请忽略此行代码）, 默认是标准(Standard), 低频(standard_ia)
// 更多存储类型请参见 https://cloud.tencent.com/document/product/436/33417
//putObjectRequest.setStorageClass(StorageClass.Standard_IA);

            try {
                putObjectResult = cosClient.putObject(putObjectRequest);
                System.out.println(putObjectResult.getRequestId());
            } catch (CosServiceException e) {
                e.printStackTrace();
            } catch (CosClientException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 确认本进程不再使用 cosClient 实例之后，关闭之
            if (Objects.nonNull(cosClient)) {
                cosClient.shutdown();
            }
        }
        return putObjectResult;
    }

    /**
     * 上传文件到COS
     *
     * @param localFile
     * @param key
     * @return
     */
    private static String uploadFileToCOS(File localFile, String key) throws InterruptedException {
        PutObjectRequest putObjectRequest = new PutObjectRequest(CosUploadConstant.BUCKET, key, localFile);
        ExecutorService threadPool = Executors.newFixedThreadPool(8);
        TmpSecretKeyConstant tmpSecretKey = new TmpSecretKey().getTmpSecretKey();
        if (Objects.isNull(tmpSecretKey)) {
            new BusinessException(String.valueOf(ErrorCode.SERVER_ERROR), "服务器异常，请重新提交。");
        }
        PutObjectResult putObjectResult = null;
        COSClient cosClient = new COSClientCreate().getCosClient(tmpSecretKey);
        // 传入一个threadPool, 若不传入线程池, 默认TransferManager中会生成一个单线程的线程池
        // 返回一个异步结果Upload, 可同步的调用waitForUploadResult等待upload结束, 成功返回UploadResult, 失败抛出异常
        TransferManager transferManager = new TransferManager(cosClient, threadPool);
        Upload upload = transferManager.upload(putObjectRequest);
        UploadResult uploadResult = upload.waitForUploadResult();
        transferManager.shutdownNow();
        cosClient.shutdown();
        String filePath = CosUploadConstant.BASEURL + uploadResult.getKey();
        return filePath;
    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static String upload(MultipartFile file, String folderPrefix) throws Exception {
        String date = DateUtils.dateStr2(new Date());
        String originalFilename = file.getOriginalFilename();
        long nextId = IDGenerator.next();
        String name = nextId + originalFilename.substring(originalFilename.lastIndexOf("."));
        String folderName = folderPrefix + "/" + date + "/";
        String key = folderName + name;
        File localFile = null;
        try {
            localFile = transferToFile(file);
            String filePath = uploadFileToCOS(localFile, key);
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("文件上传失败");
        } finally {
            localFile.delete();
        }
    }

    /**
     * 用缓冲区来实现这个转换, 即创建临时文件
     * 使用 MultipartFile.transferTo()
     *
     * @param multipartFile
     * @return
     */
    private static File transferToFile(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String prefix = originalFilename.split("\\.")[0];
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        File file = File.createTempFile(prefix + "box", suffix);
        multipartFile.transferTo(file);
        return file;
    }
}
