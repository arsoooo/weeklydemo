package com.bupt317.study.weeklydemo.util;

import com.bupt317.study.weeklydemo.config.StaticParams;
import com.bupt317.study.weeklydemo.pojo.User;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class ImgUtil {


    /**
     * 将前端传来的图片，根据uid存到本地
     * 注：转换成jpg
     * @param user
     * @param image
     */
    public static void saveUserImg(User user, MultipartFile image, HttpServletRequest request) {
        try {
            File imageFolder = new File(PathUtil.getROOTPath(request, StaticParams.USER_IMG_ROOT));
            File file = new File(imageFolder, user.getId() + ".jpg");
            if (!file.getParentFile().exists()) { file.getParentFile().mkdirs(); }
            System.out.println("ImgUtil:" + file.getAbsolutePath());
            // springMVC的上传操作(下载到file)
            image.transferTo(file);
            BufferedImage img = change2jpg(file);
            ImageIO.write(img, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 转换jpg的方法
     * from how2J
     */
    public static BufferedImage change2jpg(File f) {
        try {
            Image i = Toolkit.getDefaultToolkit().createImage(f.getAbsolutePath());
            PixelGrabber pg = new PixelGrabber(i, 0, 0, -1, -1, true);
            pg.grabPixels();
            int width = pg.getWidth(), height = pg.getHeight();
            final int[] RGB_MASKS = {0xFF0000, 0xFF00, 0xFF};
            final ColorModel RGB_OPAQUE = new DirectColorModel(32, RGB_MASKS[0], RGB_MASKS[1], RGB_MASKS[2]);
            DataBuffer buffer = new DataBufferInt((int[]) pg.getPixels(), pg.getWidth() * pg.getHeight());
            WritableRaster raster = Raster.createPackedRaster(buffer, width, height, width, RGB_MASKS, null);
            BufferedImage img = new BufferedImage(RGB_OPAQUE, raster, false, null);
            return img;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

}
