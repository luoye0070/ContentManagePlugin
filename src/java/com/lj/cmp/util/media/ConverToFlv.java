package com.lj.cmp.util.media;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;
import java.util.Properties;

import com.lj.cmp.util.system.OSinfo;

public class ConverToFlv {
    private String dirName;
    private String fileName;
    private String filePath;
    private String fileRealName; // 文件名 不包括扩展名
    private String newFileName;//新文件名
    private String newFilePath;
    private String imageName;
    private String imagePath;
    private boolean deleteOld=true;
    private Properties videoPp;
    private Properties audioPp;

    private String ffmpegpath = "ffmpeg"; // ffmpeg的目录
    private String ffprobepath="ffprobe";//ffprobe目录

    public String getNewFileName() {
        return newFileName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setDeleteOld(boolean deleteOld) {
        this.deleteOld = deleteOld;
    }

    public ConverToFlv(String bootPath,String dirName,String fileName){
        this.dirName=dirName;
        this.fileName=fileName;
        this.filePath=dirName+File.separator+fileName;
        if(fileName.lastIndexOf(".")>-1){
            this.fileRealName = fileName.substring(0, fileName.lastIndexOf("."));
        }else{
            this.fileRealName =fileName;
        }
        this.newFileName=fileRealName+".flv";
        this.newFilePath=dirName+File.separator+newFileName;

        this.imageName=fileRealName+".jpg";
        this.imagePath=dirName+File.separator+imageName;

        //根据操作系统来选择用什么ffmpeg
        if(OSinfo.isWindows()){
            ffmpegpath=bootPath+File.separator+ "ffmpeg" +File.separator+"ffmpeg_win64.exe";//默认
            ffprobepath=bootPath+File.separator+"ffmpeg" +File.separator+"ffprobe_win64.exe";
            if(OSinfo.is32B()){//32位系统
                ffmpegpath=bootPath+File.separator+ "ffmpeg" +File.separator+"ffmpeg_win32.exe";
                ffprobepath=bootPath+File.separator+"ffmpeg" +File.separator+"ffprobe_win32.exe";
            }else if(OSinfo.is64B()){//64位系统
                ffmpegpath=bootPath+File.separator+ "ffmpeg" +File.separator+"ffmpeg_win64.exe";
                ffprobepath=bootPath+File.separator+"ffmpeg" +File.separator+"ffprobe_win64.exe";
            }

        }else if(OSinfo.isMacOS()||OSinfo.isMacOSX()){
            ffmpegpath=bootPath+File.separator+ "ffmpeg" +File.separator+"ffmpeg_mac_intel64";
            ffprobepath=bootPath+File.separator+"ffmpeg" +File.separator+"ffprobe_mac_intel64";
            if(OSinfo.is32B()){//32位系统
                ffmpegpath=bootPath+File.separator+ "ffmpeg" +File.separator+"ffmpeg_mac32";
                ffprobepath=bootPath+File.separator+"ffmpeg" +File.separator+"ffprobe_mac32";
            }else if(OSinfo.is64B()){//64位系统
                ffmpegpath=bootPath+File.separator+ "ffmpeg" +File.separator+"ffmpeg_mac64";
                ffprobepath=bootPath+File.separator+"ffmpeg" +File.separator+"ffprobe_mac64";
            }
        }else{//其他认为都是linux
            ffmpegpath=bootPath+File.separator+ "ffmpeg" +File.separator+"ffmpeg_linux64";//默认
            ffprobepath=bootPath+File.separator+"ffmpeg" +File.separator+"ffprobe_linux64";
            if(OSinfo.is32B()){//32位系统
                ffmpegpath=bootPath+File.separator+ "ffmpeg" +File.separator+"ffmpeg_linux32";
                ffprobepath=bootPath+File.separator+"ffmpeg" +File.separator+"ffprobe_linux32";
            }else if(OSinfo.is64B()){//64位系统
                ffmpegpath=bootPath+File.separator+ "ffmpeg" +File.separator+"ffmpeg_linux64";
                ffprobepath=bootPath+File.separator+"ffmpeg" +File.separator+"ffprobe_linux64";
            }
        }
    }

    public boolean conver(){
        if(checkFile()&&isSupportContentType()){
            getVideoInfo();
            if(videoPp==null){
                return false;
            }
            List<String> commend = new java.util.ArrayList<String>();
            commend.add(ffmpegpath);
            commend.add("-i");
            commend.add(filePath);
            commend.add("-ab");
            commend.add("64");
            commend.add("-acodec");
            commend.add("mp3");
            commend.add("-ac");
            commend.add("2");
            commend.add("-ar");
            commend.add("22050");
            commend.add("-b");
            commend.add("230");
            commend.add("-r");
            commend.add(""+videoPp.getProperty("r_frame_rate"));
            commend.add("-qscale");
            commend.add("4");
            commend.add("-aspect");
            commend.add(""+videoPp.getProperty("display_aspect_ratio"));
            String display_aspect_ratio=videoPp.getProperty("display_aspect_ratio");
            double w_i=0.0d;
            double h_i=0.0d;
            try{
                int idx=display_aspect_ratio.indexOf(":");
                if(idx>=0){
                    String w=display_aspect_ratio.substring(0,idx);
                    String h=display_aspect_ratio.substring(idx+1);
                    w_i=Double.parseDouble(w);
                    h_i=Double.parseDouble(h);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
            int width_i=0;
            int height_i=0;
            if(w_i>0){
               try{
                   width_i=Integer.parseInt(videoPp.getProperty("width"));
                   height_i=(int)(width_i/w_i*h_i);
               }catch (Exception ex){
                   ex.printStackTrace();
               }
            }
            commend.add("-s");
            if(height_i>0){
                commend.add(width_i+"x"+height_i);
            }else {
                commend.add(videoPp.getProperty("width")+"x"+videoPp.getProperty("height"));
            }
            commend.add("-y");
            commend.add(newFilePath);
            try {
                ProcessBuilder builder = new ProcessBuilder();
                String cmd = commend.toString();
                System.out.println("cmd::"+cmd);
                builder.command(commend);
                Process p = builder.start();
                int exitValue=doWaitFor(p);
                System.out.println("exitValue---->"+exitValue);
                p.destroy();
                if(exitValue==0){
                    if(deleteOld){
                        deleteFile(filePath);
                    }
                    return true;
                }
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
    public boolean processImg() {
        String type = newFilePath.substring(newFilePath.lastIndexOf(".") + 1, newFilePath.length()).toLowerCase();
        if(checkFile()&&"flv".equals(type)){
            if(videoPp==null){
                getVideoInfo();
                if(videoPp==null){
                    return false;
                }
            }
            List<String> commend = new java.util.ArrayList<String>();
            commend.add(ffmpegpath);
            commend.add("-i");
            commend.add(newFilePath);
            commend.add("-y");
            commend.add("-f");
            commend.add("image2");
            commend.add("-ss");
            commend.add("38");
            commend.add("-t");
            commend.add("0.001");
            commend.add("-s");
            commend.add(videoPp.getProperty("width")+"x"+videoPp.getProperty("height"));
            commend.add(imagePath);
            try {
                ProcessBuilder builder = new ProcessBuilder();
                String cmd = commend.toString();
                System.out.println("cmd::"+cmd);
                builder.command(commend);
                Process p = builder.start();
                int exitValue=doWaitFor(p);
                p.destroy();
                if(exitValue==0){
                    return true;
                }
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
    public Properties getVideoInfo(){
        videoPp=getInfo("v");
        return videoPp;
    }
    public Properties getAudioInfo(){
        audioPp=getInfo("a");
        return audioPp;
    }
    public Properties getInfo(String selectStream){
        Properties pp=null;
        if(checkFile()&&isSupportContentType()){
            List<String> commend = new java.util.ArrayList<String>();
            commend.add(ffprobepath);
            commend.add(filePath);
            commend.add("-v");
            commend.add("quiet");
            commend.add("-print_format");
            commend.add("ini");
//        commend.add("-show_format");
            commend.add("-show_streams");
            commend.add("-select_streams");
            commend.add(selectStream);
            try {
                ProcessBuilder builder = new ProcessBuilder();
                String cmd = commend.toString();
                System.out.println("cmd::"+cmd);
                builder.command(commend);
                Process p = builder.start();
                String  remessage=doWaitForRemessage(p);
                p.destroy();
                System.out.println("reMessage-->"+remessage);
                if(remessage!=null){
                    pp=new Properties();
                    pp.load(new StringReader(remessage));
                    pp.list(System.out);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return pp;
    }
    private String doWaitForRemessage(Process p) {
        InputStream in = null;
        InputStream err = null;
        int exitValue = -1; // returned to caller when p is finished
        StringBuilder sb=new StringBuilder("");
        try {
            in = p.getInputStream();
            err = p.getErrorStream();
            boolean finished = false; // Set to true when p is finished
            while (!finished) {
                try {
                    while (in.available() > 0) {
                        Character c = new Character((char) in.read());
                        //System.out.print(c);
                        sb.append(c);
                    }
                    while (err.available() > 0) {
                        Character c = new Character((char) err.read());
                        System.out.print(c);
                    }
                    exitValue = p.exitValue();
                    finished = true;
                } catch (IllegalThreadStateException e) {
                    Thread.currentThread().sleep(500);
                }
            }
        } catch (Exception e) {
            System.err.println("doWaitFor();: unexpected exception - "
                    + e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            if (err != null) {
                try {
                    err.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        if(exitValue==0){
            return sb.toString();
        }
        return null;
    }
    public boolean checkFile() {
        System.out.println("filePath::"+filePath);
        File file = new File(filePath);
        if (!file.isFile()) {
            System.out.println("is not file");
            return false;
        } else {
            System.out.println("is file");
            return true;
        }
    }
    public boolean isSupportContentType() {
        String type = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
        // ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
        if (type.equals("avi")) {
            return true;
        } else if (type.equals("mpg")) {
            return true;
        } else if (type.equals("wmv")) {
            return true;
        } else if (type.equals("3gp")) {
            return true;
        } else if (type.equals("mov")) {
            return true;
        } else if (type.equals("mp4")) {
            return true;
        } else if (type.equals("asf")) {
            return true;
        } else if (type.equals("asx")) {
            return true;
        }
        // else if (type.equals("flv")) {//已经是flv了，不需要转换了
        //     return true;
        // }
        System.out.println("is not support file type");
        return false;
    }
    public int doWaitFor(Process p) {
        InputStream in = null;
        InputStream err = null;
        int exitValue = -1; // returned to caller when p is finished
        StringBuilder inStrBuffer=new StringBuilder("");
        StringBuilder errStrBuffer=new StringBuilder("");
        try {
            System.out.println("comeing");
            in = p.getInputStream();
            err = p.getErrorStream();
            boolean finished = false; // Set to true when p is finished

            while (!finished) {
                try {
                    while (in.available() > 0) {
                        char c = (char) in.read();
                        if(c=='\n') {
                            System.out.print(inStrBuffer.toString());
                            inStrBuffer=new StringBuilder("");
                        }else{
                            inStrBuffer.append(c);
                        }
                    }
                    while (err.available() > 0) {
                        Character c = new Character((char) err.read());
                        if(c=='\n') {
                            System.out.print(errStrBuffer.toString());
                            errStrBuffer=new StringBuilder("");
                        }else{
                            errStrBuffer.append(c);
                        }
                    }
                    exitValue = p.exitValue();
                    finished = true;
                } catch (IllegalThreadStateException e) {
                    Thread.currentThread().sleep(500);
                }
            }
        } catch (Exception e) {
            System.err.println("doWaitFor();: unexpected exception - "
                    + e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            if (err != null) {
                try {
                    err.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return exitValue;
    }

    public void deleteFile(String filepath) {
        File file = new File(filepath);
        if (file.delete()) {
            System.out.println("文件" + filepath + "已删除");
        }
    }
}
