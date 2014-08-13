package com.xiaohao.ueditor.servlet;

import com.xiaohao.ueditor.constant.Constant;
import com.xiaohao.ueditor.util.JsonUtil;
import com.xiaohao.ueditor.util.UploadUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by xiaohao on 2014/8/12.
 * 用来处理ueditor的后台servlet
 */
public class UeditorServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String actionType = req.getParameter(Constant.request_parameter_action);

        if (Constant.action_type_config.equals(actionType)) {

            JsonUtil.flushEditorConfig(resp);

        } else if (Constant.action_type_uploadimage.equals(actionType)) {

            UploadUtil.uploadFile(this.getServletContext(), req, resp, Constant.domain_name_image);

        } else if (Constant.action_type_uploadvideo.endsWith(actionType)) {

            UploadUtil.uploadFile(this.getServletContext(), req, resp, Constant.domain_name_video);

        } else if (Constant.action_type_uploadfile.equals(actionType)) {

            UploadUtil.uploadFile(this.getServletContext(), req, resp, Constant.domain_name_file);

        } else if (Constant.action_type_uploadscrawl.equals(actionType)) {

            UploadUtil.uploadScrawlOpt(req,resp, Constant.domain_name_draw);

        } else if (Constant.action_type_listimage.equals(actionType)) {

           UploadUtil.listImgOpt(req,resp,Constant.domain_name_image);

        } else if ("listfile".equals(actionType)) {

            UploadUtil.listFileOpt(req, resp, Constant.domain_name_file);

        }

    }


}
