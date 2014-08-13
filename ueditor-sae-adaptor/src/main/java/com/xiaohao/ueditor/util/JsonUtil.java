package com.xiaohao.ueditor.util;

import com.xiaohao.ueditor.entity.FileState;
import com.xiaohao.ueditor.entity.ListImgState;
import org.json.JSONException;
import org.json.JSONObject;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by xiaohao on 2014/8/12.
 * 用来序列化编辑器文件操作后 返回客户端的对象为json字符串
 */
public class JsonUtil {

    private JsonUtil() {
    }

    public static JSONObject jsonConfig;

    /**
     * 将json结果写会给response
     *
     * @param response
     * @param result
     */
    public static void flushJsonResult(HttpServletResponse response, String result) {

        response.setCharacterEncoding("UTF-8");

        response.setContentType("application/json; charset=utf-8");

        PrintWriter out = null;

        try {
            out = response.getWriter();

            out.append(result);

        } catch (IOException e) {

            e.printStackTrace();

        } finally {
            if (out != null) {

                out.close();

            }
        }


    }


    /**
     * @param response
     */
    public static void flushEditorConfig(HttpServletResponse response) {
        if (jsonConfig == null) {
            try {
                String content = readFile("config.json");
                jsonConfig = new JSONObject(content);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        flushJsonResult(response, jsonConfig.toString());


    }

    /**
     * 取得上传文件的一个返回对象
     *
     * @return
     */
    public static FileState getFileStateInstance() {
        FileState state = new FileState();
        state.setState("SUCCESS");
        return state;
    }

    /**
     * 取得一个文件列表的返回对象
     *
     * @return
     */
    public static ListImgState getListFileImgStateInstance() {
        ListImgState state = new ListImgState();
        state.setState("SUCCESS");
        return state;
    }

    /**
     * @param path
     * @return
     * @throws java.io.IOException
     */
    private static String readFile(String path) throws IOException {

        StringBuilder builder = new StringBuilder();

        try {
            File file = new File(".");
            System.out.print(file.getAbsolutePath());

            InputStreamReader reader = new InputStreamReader(JsonUtil.class.getClassLoader().getResourceAsStream("config.json"), "UTF-8");
            BufferedReader bfReader = new BufferedReader(reader);

            String tmpContent = null;

            while ((tmpContent = bfReader.readLine()) != null) {
                builder.append(tmpContent);
            }

            bfReader.close();

        } catch (UnsupportedEncodingException e) {
            // 忽略
        }

        return filter(builder.toString());

    }

    // 过滤输入字符串, 剔除多行注释以及替换掉反斜杠
    private static String filter(String input) {

        return input.replaceAll("/\\*[\\s\\S]*?\\*/", "");

    }

}
