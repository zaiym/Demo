package com.zaiym.file.upload;

import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Uploaddemo extends HttpServlet{

    String dir = "E:\\receive\\";

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }


    private Map<Integer,Long> check(String md5){
        Map<Integer,Long> blocks = new HashMap<Integer,Long>();
        File file = new File(dir + md5);
        if (!file.exists() || !file.isDirectory()) {
            blocks.put(-1, -1L);
            return blocks;
        }
        File[] temps = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".temp") && pathname.isFile();
            }
        });
        if (temps == null || temps.length == 0) {
            blocks.put(-1, 0L);
            return null;
        }
        for (File temp : temps) {
            String fileName = temp.getName();
            blocks.put(Integer.parseInt(fileName.substring(0,fileName.indexOf("-"))), temp.length());
        }
        return blocks;
    }

    private void write(Long blockSize, int blockIndex, String md5, InputStream in) throws IOException {
        RandomAccessFile accessFile = null;
        OutputStream out = null;
        try {
            File temp = new File(dir + md5 + File.separator + blockIndex + "_block.temp");
            //文件不存在，新上传
            if (!temp.exists() || !temp.isFile()) {
                out = new FileOutputStream(temp);
                IOUtils.copy(in,out);
            }
            //文件已经存在，继续上传后续文件字节
            else {
                long tempLength = temp.length();
                if (tempLength < blockSize) {
                    byte[] bytes = new byte[1024];
                    accessFile = new RandomAccessFile(temp, "rw");
                    accessFile.seek(tempLength);
                    int length;
                    while ((length = in.read(bytes, 0 , bytes.length)) > 0) {
                        accessFile.write(bytes, 0 , length);
                    }
                }
            }
        } finally {
            if (out != null)
                IOUtils.closeQuietly(out);
            try {
                if (accessFile != null)
                    accessFile.close();
            } catch (IOException e) {}
        }
    }

    private long merge(int blockCount, String md5,String suffix) throws IOException {
        SequenceInputStream sequenceInputStream = null;
        OutputStream out = null;
        try {
            String current_dir = dir + md5 + File.separator;
            File _dir = new File(current_dir);
            File[] temps = _dir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.getName().endsWith(".temp") && pathname.isFile();
                }
            });
            List<InputStream> blocks = new ArrayList<InputStream>();
            for (int i = 0; i < blockCount; i++) {
                File blockTemp = new File(current_dir + i + "_block.temp");
                if (!blockTemp.exists() || !blockTemp.isFile()) {
                    throw new RuntimeException("文件块异常！");
                }
                blocks.add(new FileInputStream(blockTemp));
            }
            Enumeration<InputStream> enumeration = Collections.enumeration(blocks);
            sequenceInputStream = new SequenceInputStream(enumeration);
            File file = new File(current_dir + md5 + File.separator + "." + suffix);
            out = new FileOutputStream(file);
            return IOUtils.copyLarge(sequenceInputStream, out);
        } finally {
            IOUtils.closeQuietly(sequenceInputStream);
            IOUtils.closeQuietly(out);
        }
    }
}