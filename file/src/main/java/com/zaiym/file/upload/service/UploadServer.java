package com.zaiym.file.upload.service;


import com.zaiym.file.upload.BlockInfo;
import org.apache.commons.io.IOUtils;

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

public class UploadServer {

    private final String dir;

    public UploadServer(String dir){
        this.dir = dir;
    }

    /**
     * 检查文件片段状态
     * @param md5        完整文件MD5
     * @param blockIndex 分片文件索引
     * @return 文件已上传字节数
     */
    public Long check(String md5, int blockIndex){
        String filePath = dir + File.separator + md5;
        File fileDir = new File(filePath);
        //文件片段列表
        File[] blocks = fileDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().contains("block") && pathname.isFile();
            }
        });
        if (blocks != null) {
            for (File block : blocks) {
                String fileName = block.getName();
                if (fileName.startsWith(blockIndex + "_block")) {
                    return block.length();
                }
            }
        }
        return 0L;
    }


    /**
     * 批量检查文件片段状态
     * @param info 文件信息
     * @param isCheckMd5 是否检查文件MD5，大文件MD5计算比较耗时
     * @return 所有分片已上传字节数
     */
    public Map<Integer,Long> check(BlockInfo info, boolean isCheckMd5){
        Map<Integer,Long> uploads = new HashMap<Integer, Long>();
        File file = new File(dir + File.separator + info.getMd5() + File.separator + info.getMd5() +"." + info.getSuffix());
        //0：文件已经上传  -1：待上传文件和服务器文件不一致
        Long state = 1L;
        if (file.exists() && file.isFile()) {
            if (file.length() == info.getFileSize()) {
                state = 0L;
            } else if (file.length() != info.getFileSize()) {
                state = -1L;
            }
        }
        //检查服务器文件MD5
        if (isCheckMd5) {
            try {
                if (!info.getMd5().equalsIgnoreCase(MD5Util.getFileMD5String(file))) {
                    state = -1L;
                }
            } catch (IOException e) {}
        }
        if (state != 1L) {
            uploads.put(-1, state);
            if (state == -1L)
                file.delete();
            return uploads;
        }
        String filePath = dir + File.separator + info.getMd5();
        File fileDir = new File(filePath);
        if (!fileDir.exists() || !fileDir.isDirectory()) {
            return null;
        }
        //文件片段列表
        File[] blocks = fileDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().contains("block") && pathname.isFile();
            }
        });
        if (blocks != null) {
            for (File block : blocks) {
                String fileName = block.getName();
                uploads.put(Integer.parseInt(fileName.substring(0,fileName.indexOf("_"))), block.length());
            }
        }
        return uploads;
    }


    /**
     * 文件片段写入
     * @param info 文件信息
     * @param in 输入流
     * @return 返回已写入字节数
     */
    public long writeBytes(BlockInfo info, InputStream in) throws IOException {
        RandomAccessFile accessFile = null;
        OutputStream out = null;
        //本次写入的字节数
        long uploaded = 0L;
        File temp = null;
        try {
            temp = new File(dir + info.getMd5());
            if (!temp.exists()) {
                temp.mkdirs();
            }
            temp = new File(dir + info.getMd5() + File.separator + info.getBlockIndex() + "_block.temp");
            //文件不存在，新上传
            if (!temp.exists() || !temp.isFile()) {
                out = new FileOutputStream(temp);
                uploaded = IOUtils.copyLarge(in,out);
            } else {
                //文件已经存在，继续上传后续文件字节
                long tempLength = temp.length();
                if (tempLength < info.getBlockSize()) {
                    byte[] bytes = new byte[1024];
                    accessFile = new RandomAccessFile(temp, "rw");
                    accessFile.seek(tempLength);
                    int length;
                    while ((length = in.read(bytes, 0 , bytes.length)) > 0) {
                        accessFile.write(bytes, 0 , length);
                        uploaded += length;
                    }
                }
            }
        } finally {
            if (out != null) {
                out.flush();
                IOUtils.closeQuietly(out);
            }
            try {
                if (accessFile != null)
                    accessFile.close();
            } catch (IOException e) {}
            //文件重命名  0_block.temp >> 0_block
            if (temp != null && temp.length() == info.getBlockSize()) {
                try {
                    temp.renameTo(new File(dir + info.getMd5() + File.separator + info.getBlockIndex() + "_block"));
                } catch (Exception e) {}
            }
        }
        return uploaded;
    }


    /**
     * 合并文件片段
     * @param info 文件信息
     * @return 返回合并总总字节数
     */
    public void merge(BlockInfo info) throws IOException {
        new Thread(new MergeJob(info)).start();
    }

    class MergeJob implements Runnable{
        private BlockInfo info;

        public MergeJob(BlockInfo info){
            this.info = info;
        }

        @Override
        public void run() {
            SequenceInputStream sequenceInputStream = null;
            OutputStream out = null;
            try {
                String current_dir = dir + info.getMd5() + File.separator;
                List<InputStream> blocks = new ArrayList<InputStream>();
                for (int i = 0; i < info.getBlockCount(); i++) {
                    File blockTemp = new File(current_dir + i + "_block");
                    if (!blockTemp.exists() || !blockTemp.isFile()) {
                        throw new RuntimeException("文件块异常！");
                    }
                    blocks.add(new FileInputStream(blockTemp));
                }
                Enumeration<InputStream> enumeration = Collections.enumeration(blocks);
                sequenceInputStream = new SequenceInputStream(enumeration);
                File file = new File(current_dir + info.getMd5() + "." + info.getSuffix());
                out = new FileOutputStream(file);
                long mergedLength = IOUtils.copyLarge(sequenceInputStream, out);
                //文件合并完成，删除文件片段
                if (mergedLength == info.getFileSize()) {
                    deleteBlocks(info);
                }
            } catch (IOException e) {

            } finally {
                IOUtils.closeQuietly(sequenceInputStream);
                IOUtils.closeQuietly(out);
            }
        }
    }

    /**
     * 删除文件片段
     * @param info
     */
    public void deleteBlocks(BlockInfo info){
        String filePath = dir + File.separator + info.getMd5();
        File fileDir = new File(filePath);
        //文件片段列表
        File[] blocks = fileDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith("block") && pathname.isFile();
            }
        });
        if (blocks != null) {
            for (File block : blocks) {
                block.delete();
            }
        }
    }

    /**
     * 判断所有片段是否都已上传完成
     * @param info
     * @return
     */
    public boolean isEnd(BlockInfo info){
        String filePath = dir + File.separator + info.getMd5();
        File fileDir = new File(filePath);
        //文件片段列表
        File[] blocks = fileDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith("block") && pathname.isFile();
            }
        });
        return blocks != null && blocks.length == info.getBlockCount();
    }
}