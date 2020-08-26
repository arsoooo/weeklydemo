package com.bupt317.study.weeklydemo.util;
import com.bupt317.study.weeklydemo.pojo.User;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.*;

public class WordUtil {

    /**
     * 根据user doc模板写用户数据表
     */
    public static void writeDoc(User user) {

        // 准备user的数据，处理null的问题
        String name = user.getName()!=null?user.getName():"";
        String email = user.getEmail()!=null?user.getEmail():"暂无";
        String phone = user.getPhone()!=null?user.getPhone():"暂无";
        String other = user.getOther()!=null?user.getOther():"暂无";

        // 加载doc模板并写入
        String templatePath = "src\\main\\webapp\\doc\\model\\userM.doc";
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(templatePath);
            HWPFDocument doc = new HWPFDocument(is);
            Range range = doc.getRange();

            //把range范围内的${reportDate}替换为当前的日期
            range.replaceText("${titleUserName}", user.getName());
            range.replaceText("${name}", name);
            range.replaceText("${email}", email);
            range.replaceText("${phone}", phone);
            range.replaceText("${other}", other);
            os = new FileOutputStream(
                    new File("src\\main\\webapp\\doc\\user\\"+user.getId()+".doc"));
            //把doc输出到输出流中
            doc.write(os);

            os.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读doc的内容，区分标题和正文
     * 标题只支持2个，并且会把所有非标题的内容变成对应的正文
     * 注意！ 根据标题的名字来区分标题，正文请不要出现标题
     */
    public static void readDoc() throws IOException {
        String templatePath = "src\\main\\webapp\\doc\\model\\template.doc";
        InputStream is = new FileInputStream(templatePath); //读取文件
//        OutputStream os = null;
        HWPFDocument doc = new HWPFDocument(is);
        Range range = doc.getRange();
        System.out.println(range.numParagraphs());

        // 设置标题
        String title1 = "一级标题：周报内容1";
        String title2 = "一级标题：周报内容2";
        StringBuilder tmpContent = new StringBuilder(); // 正文内容
        int paraIdx = 0; // 当前段落

        for(int i = 0; i< range.numParagraphs(); i++){
            String paraContent = range.getParagraph(i).text().trim(); // 获取的内容（可能是标题可能是正文）
            // 如果找到标题1，把标题1之后、标题2之前的内容，作为1的正文
            if(title1.equals(paraContent)){
                paraIdx = i + 1;
                System.out.println("标题："+paraContent);
                while(!title2.equals(range.getParagraph(paraIdx).text().trim())){
                    tmpContent.append(range.getParagraph(paraIdx).text().trim());
                    paraIdx ++;
                }
                System.out.println("正文:" + "\n" + tmpContent);
                break;
            }
        }
        // 接着把标题2后面到文末的所有内容作为正文
        tmpContent = new StringBuilder();
        System.out.println("标题2："+range.getParagraph(paraIdx).text().trim());
        for(int i = paraIdx+1; i < range.numParagraphs(); i++){
            tmpContent.append(range.getParagraph(i).text().trim());
        }
        System.out.println("正文2:" + "\n" + tmpContent);


        is.close();
    }


    public static void main(String[] args) throws Exception {
        // 准备user
//        User user = new User();
//        user.setId(1);
//        user.setName("aaa");
//        user.setEmail("s@ss.com");
//        user.setPassword("1");
//        user.setOther("sdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdjsdlmslkdjsdj");
//
//        writeDoc(user);

        readDoc();
    }
}
