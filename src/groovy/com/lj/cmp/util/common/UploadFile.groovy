package com.lj.cmp.util.common

import com.lj.cmp.util.customenum.FileExts
import com.lj.cmp.util.customenum.ResourceType
import com.lj.cmp.util.media.ConverToFlv

import javax.imageio.ImageIO
import java.awt.*
import java.awt.image.BufferedImage
import java.text.SimpleDateFormat

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-9-11
 * Time: 上午1:13
 * To change this template use File | Settings | File Templates.
 */
class UploadFile {
    public static String bootPath="";
    //上传文件到文件系统
    public static String uploadToFileSystem(InputStream is,String dirName,String fileName) {
        String fileFullName = null;
        // 获取系统默认文件路径分隔符
        def separator = System.getProperty("file.separator");
        // 获取上传文件扩展名
        //def extension = fileName.substring(fileName.indexOf("."));
        //文件名加上时间
        fileName = new Date().getTime() + fileName;
        //文件保存路径
        //def filePath = "web-app" + separator + "uploadFile" + separator;
        def filePath = bootPath+"uploadFile" + separator+dirName;
        File dirFile = new File(filePath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        FileOutputStream fos = null;
        //InputStream is=null;
        try {
            fos = new FileOutputStream(filePath+ separator + fileName);
            //is=orginalFile.getInputStream();
            byte[] temp = new byte[1024];
            while (is.read(temp) > 0) {
                fos.write(temp);
                fos.flush();
            }
            //is.close();
            fos.close();
            //is = null;
            fos = null;
            //fileFullName = filePath + fileName;
            fileFullName = dirName+separator+fileName;
        } catch (IOException exception) {
            exception.printStackTrace()
        } finally {
            if (is != null) {
                //is.close()
            }
            if (fos != null) {
                fos.close()
            }
        }
//        //获取图片宽高
//        int width=0;
//        int height=0;
//        BufferedImage src = null;
//        try {
//            src = javax.imageio.ImageIO.read(is);
//            width = src.getWidth(null); // 得到源图宽
//            height=src.getHeight();
//            is.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        if(FileExts.dirTypeMap.get(dirName)==ResourceType.MEDIA){//视频文件需要转码
              ConverToFlv ctf=new ConverToFlv(bootPath,filePath,fileName);
            if(ctf.conver()){
                fileFullName=dirName+separator+ctf.getNewFileName();
            }
        }
        return fileFullName;
    }
    //从文件系统删除文件
    public static boolean deleteFromFileSystem(String fileFullName) {
        try {
            // 获取系统默认文件路径分隔符
            def separator = System.getProperty("file.separator");
            def filePath = bootPath+"uploadFile" + separator;
            fileFullName=filePath+fileFullName;
            File file = new File(fileFullName);
            return file.delete();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
    //从文件系统下载文件
    public static int downloadFromFileSystem(String fileFullName, OutputStream os) {
        FileInputStream fis = null;
        int recode = 0;
        try {
            // 获取系统默认文件路径分隔符
            def separator = System.getProperty("file.separator");
            def filePath = bootPath+"uploadFile" + separator;
            fileFullName=filePath+fileFullName;
            fis = new FileInputStream(fileFullName);
            byte[] buff = new byte[4 * 1024];
            int count = -1;
            while ((count = fis.read(buff)) != -1) {
                //output.write(buff);
                os.write(buff, 0, count);
                os.flush();
            }
        }
        catch (Exception ex) {
            recode = -1;
            ex.printStackTrace();
        }
        finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return recode;
    }
    //从文件系统下载缩略图片
    public static int downloadThumbnailFromFileSystem(String fileFullName, OutputStream os, int width, int height) {
        FileInputStream fis = null;
        int recode = 0;
        BufferedImage originalImage = null;
        try {
            // 获取系统默认文件路径分隔符
            def separator = System.getProperty("file.separator");
            def filePath = bootPath+"uploadFile" + separator;
            fileFullName=filePath+fileFullName;
            println("fileFullName-->"+fileFullName);
            fis = new FileInputStream(fileFullName);
            originalImage = ImageIO.read(fis);
            int oldWidth = originalImage.width;
            int oldHeight = originalImage.height;

            //缩放
            if (oldWidth - width > oldHeight - height) {//按宽缩放
                height = oldHeight * width / oldWidth;
            } else {//按高缩放
                width = oldWidth * height / oldHeight;
            }
            //println("originalImage.getType()-->"+originalImage.getType());
            BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics g = newImage.getGraphics();
            g.drawImage(originalImage, 0, 0, width, height, null);
            g.dispose();
            //写入到输出流
            ImageIO.write(newImage, "PNG", os);
//            byte []buff=new byte[4*1024];
//            int count=-1;
//            while((count=fis.read(buff))!=-1)
//            {
//                //output.write(buff);
//                os.write(buff, 0, count);
//                os.flush();
//            }
        }
        catch (Exception ex) {
            recode = -1;
            ex.printStackTrace();
        }
        finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return recode;
    }
    public static long getFileSize(String fileFullName){
        // 获取系统默认文件路径分隔符
        def separator = System.getProperty("file.separator");
        def filePath = bootPath+"uploadFile" + separator;
        fileFullName=filePath+fileFullName;
        println("fileFullName-->"+fileFullName);
        File file=new File(fileFullName);
        if(file.exists()){
            return file.size();
        }else{
            return 0;
        }
    }
//    //上传图片
//    public static String upload(InputStream is,String fileName){
//
//    }
//    //删除图片
//    public static boolean delete(String fileFullName){
//
//    }
//    //获取图片URL
//    public static String getUrl(String fileFullName){
//
//    }
}
