package camt.se331.shoppingcart.service;

import camt.se331.shoppingcart.entity.Image;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.nio.file.Files;
import java.util.Calendar;

/**
 * Created by Dto on 4/23/2015.
 */
public class ImageUtil {
    static ImageUtil imageUtil = null;
    public static ImageUtil getInstance(){
        if (imageUtil == null){
            imageUtil = new ImageUtil();
        }
        return imageUtil;
    }
    public static Image getImage(String resourcePath){
        Image image = new Image();
        ClassLoader classLoader = ImageUtil.getInstance().getClass().getClassLoader();

        File file = new File(classLoader.getResource(resourcePath).getFile());

        try {

            image.setFileName(file.getName());
            image.setContentType(Files.probeContentType(file.toPath()));
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for (int readNum; (readNum = fis.read(buf)) != -1;){
                bos.write(buf,0,readNum);
            }
            image.setContent(bos.toByteArray());
            image.setCreated(Calendar.getInstance().getTime());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;

    }

    public static Image resizeImage(Image image,int width){
        // resize image

        InputStream imageStream = new ByteArrayInputStream(image.getContent());
        try {
            // change the image byte array to Buffer Image
            BufferedImage bufferedImage = ImageIO.read(imageStream);
            //Scale the image using the default api
            BufferedImage scaledImage = Scalr.resize(bufferedImage,100);

            //Convert BufferedImage to byte
            // convert BufferedImage to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(scaledImage, getFileExtension(image.getFileName()), baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            image.setContent(imageInByte);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private static String getFileExtension(String fileName){
        String extension = "";

        int i = fileName.lastIndexOf('.');
        int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

        if (i > p) {
            extension = fileName.substring(i+1);
        }
        return extension;
    }
}
