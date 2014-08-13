package com.xiaohao.ueditor.util;

import com.sina.sae.storage.FileAttribute;
import com.sina.sae.storage.SaeStorage;
import com.sina.sae.util.SaeUserInfo;
import com.xiaohao.ueditor.constant.Constant;
import com.xiaohao.ueditor.entity.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Created by xiaohao on 2014/8/13.
 * 处理ueditor的一些动作对应的服务器端操作
 */
public class UploadUtil {

    /**
     * 上传文件到不同的domain中 返回一个上传正常的json串给客户端
     * @param servletContext
     * @param req
     * @param resp
     * @param fileDomain
     * @throws java.io.UnsupportedEncodingException
     */
    public static void uploadFile(ServletContext servletContext,HttpServletRequest req,HttpServletResponse resp,String fileDomain)throws UnsupportedEncodingException {
        //取得上传的类型
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存区块大小4KB
        factory.setSizeThreshold(4 * 1024);
        // 设置暂存容器，当上传文件大于设置的内存块大小时，用暂存容器做中转
        factory.setRepository(new File(servletContext.getRealPath(
                "/")));
        ServletFileUpload fileUpload = new ServletFileUpload(factory);
        fileUpload.setSizeMax(1024 * 1024 * 100);
        fileUpload.setFileSizeMax(1024 * 1024 * 10);
        List<FileItem> fileItemList = null;

        try {
            fileItemList = fileUpload.parseRequest(req);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        Iterator<FileItem> fileItemIterator = fileItemList.iterator();
        FileItem fileItem = null;
        while (fileItemIterator.hasNext()) {
            fileItem = fileItemIterator.next();
            // 普通文件框上传
            if (fileItem.isFormField()) {
                String filedName = fileItem.getFieldName();
                String filedValue = fileItem.getString("utf-8");// 编码格式
                System.out.println(filedName);// 文件框名称
                System.out.println(filedValue);// 文件的值
            } else {
                String filedName = fileItem.getFieldName();// 文件上传框的名称
                // 获取文件上传的文件名
                String OriginalFileName = FileUtil.takeOutFileName(fileItem.getName());
                String fileType = FileUtil.getFileType(OriginalFileName);
                System.out.println("原始文件名："+OriginalFileName);
                if (!"".equals(OriginalFileName)) {
                    // 根据上传的文件名重新命名
                    String newFileName = FileUtil.getNewFileName(OriginalFileName);
                    try {
                        //fileItem.write(writeToFile);
                        SaeStorage saeStorage = new SaeStorage(SaeUserInfo.getAccessKey(),SaeUserInfo.getSecretKey(),SaeUserInfo.getAppName());
                        InputStream io =fileItem.getInputStream();
                        int size =io.available();
                        byte[] bb =new byte[size];
                        io.read(bb);
                        io.close();
                        saeStorage.write(fileDomain, newFileName, bb);
                        String url =saeStorage.getUrl(fileDomain,newFileName);
                        FileState state =JsonUtil.getFileStateInstance();
                        state.setOriginal(OriginalFileName);
                        state.setUrl(url);
                        state.setSize(fileItem.getSize());
                        state.setTitle(OriginalFileName);
                        state.setType(fileType);
                        JsonUtil.flushJsonResult(resp,state.toJsonString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * listFile的服务器端动作 返回符合条件的json串
     * @param req
     * @param resp
     * @param domain
     */
    public static void listFileOpt(HttpServletRequest req,HttpServletResponse resp,String domain){

        SaeStorage saeStorage = new SaeStorage(SaeUserInfo.getAccessKey(), SaeUserInfo.getSecretKey(), SaeUserInfo.getAppName());
        int start = new Integer(req.getParameter(Constant.request_parameter_start));
        int size = new Integer(req.getParameter(Constant.request_parameter_size));
        List<String> fileList = saeStorage.getList(domain, "*", size, start);
        int total = saeStorage.getFilesNum(domain, "");
        List<ListFileItemState> listFileStates = new ArrayList<ListFileItemState>();

        for (int i = 0; i < fileList.size(); i++) {
            ListFileItemState fileState = new ListFileItemState();
            String url = saeStorage.getUrl(domain, fileList.get(i));
            FileAttribute fileAttribute = saeStorage.getAttr(domain, fileList.get(i));
            fileState.setMtime(fileAttribute.getDatetime());
            fileState.setUrl(url);
            listFileStates.add(fileState);
        }

        ListFileState imgState = new ListFileState();
        imgState.setState(Constant.result_ok);
        imgState.setList(listFileStates);
        imgState.setStart(start);
        imgState.setTotal(total);
        JsonUtil.flushJsonResult(resp, imgState.toJsonString());
    }

    /**
     * listimg的服务器端操作 返回符合条件的json串
     * @param req
     * @param resp
     * @param domain
     */
    public static void listImgOpt(HttpServletRequest req,HttpServletResponse resp,String domain){

        SaeStorage saeStorage = new SaeStorage(SaeUserInfo.getAccessKey(), SaeUserInfo.getSecretKey(), SaeUserInfo.getAppName());
        int start = new Integer(req.getParameter(Constant.request_parameter_start));
        int size = new Integer(req.getParameter(Constant.request_parameter_size));
        List<String> fileList = saeStorage.getList(domain, "*", size, start);
        int total1 = saeStorage.getFilesNum(domain, "");
        List<ListImgItemState> listImgStates = new ArrayList<ListImgItemState>();

        for (int i = 0; i < fileList.size(); i++) {
            ListImgItemState imgState = new ListImgItemState();
            imgState.setState(Constant.result_ok);
            String url = saeStorage.getUrl(domain, fileList.get(i));
            imgState.setUrl(url);
            listImgStates.add(imgState);
        }

        ListImgState imgState = new ListImgState();
        imgState.setState(Constant.result_ok);
        imgState.setList(listImgStates);
        imgState.setStart(start);
        imgState.setTotal(total1);
        JsonUtil.flushJsonResult(resp, imgState.toJsonString());
    }

    /**
     * 涂鸦上传 后台操作
     * @param req
     * @param resp
     * @param domain
     */
    public static void uploadScrawlOpt(HttpServletRequest req,HttpServletResponse resp,String domain){
        //如果是截图
        String content = req.getParameter(Constant.request_parameter_upfile);
        byte[] data = FileUtil.decode(content);

        SaeStorage saeStorage = new SaeStorage(SaeUserInfo.getAccessKey(), SaeUserInfo.getSecretKey(), SaeUserInfo.getAppName());
        String filename = UUID.randomUUID().toString() + Constant.image_extention;
        saeStorage.write(domain, filename, data);
        FileState state = JsonUtil.getFileStateInstance();

        state.setType(Constant.image_extention);
        state.setUrl(saeStorage.getUrl(domain, filename));
        state.setSize(data.length);
        state.setTitle(Constant.image_title);
        state.setOriginal(Constant.image_name);

        JsonUtil.flushJsonResult(resp, state.toJsonString());
    }
}
