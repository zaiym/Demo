package com.zaiym;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileClient {

    public static void main(String[] args) throws IOException {
        int thread = 4;
        CountDownLatch countDownLatch = new CountDownLatch(thread);
        ExecutorService service = Executors.newFixedThreadPool(thread);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < thread; i++) {
            Map<String,String> params = new HashMap<String,String>();
            params.put("md5","fa68ac0327485b31533c34685ea0e49a");
            params.put("blockCount","4");
            params.put("fileSize","2148037042");
            params.put("suffix","mp4");
            params.put("blockIndex", i+"");
            File file = new File("E:\\receive\\block_" + i);
            params.put("blockSize", file.length()+"");
            service.execute(new Updater(file, params, countDownLatch));
        }
        service.shutdown();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {}
        System.out.println("用时：" + (System.currentTimeMillis() - startTime));
    }

    static class Updater implements Runnable{
        File file;
        Map<String,String> params;

        CountDownLatch countDownLatch;

        public Updater(File file,Map<String,String> params, CountDownLatch countDownLatch){
            this.file = file;
            this.params = params;
            this.countDownLatch = countDownLatch;
        }
        @Override
        public void run() {
            try {
                String str = HttpClientUtil.upload(file,"http://127.0.0.1:8080/file/upload", params);
                System.out.println(Thread.currentThread().getName() + " : " + str);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        }
    }

    public static void splitFile(File file, int num){
        if (file == null || !file.exists() || !file.isFile()) {
            return;
        }
        long size = file.length();
        long blockSize = size/num;
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            OutputStream os = null;
            for (int i = 0; i < num; i++) {
                try {
                    File block = new File("E:\\receive\\block_" + i);
                    os = new FileOutputStream(block);
                    byte[] b = new byte[2048];
                    int len;
                    while ((len = in.read(b, 0, b.length)) > 0) {
                        os.write(b,0, len);
                        if (block.length() >= blockSize) {
                            break;
                        }
                    }
                    os.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    IOUtils.closeQuietly(os);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
        }
    }


    public static long merge() throws IOException {
        SequenceInputStream sequenceInputStream = null;
        OutputStream out = null;
        try {
            List<InputStream> blocks = new ArrayList<InputStream>();
            for (int i = 0; i < 4; i++) {
                File blockTemp = new File("E:\\receive\\block_" + i);
                if (!blockTemp.exists() || !blockTemp.isFile()) {
                    throw new RuntimeException("文件块异常！");
                }
                blocks.add(new FileInputStream(blockTemp));
            }
            Enumeration<InputStream> enumeration = Collections.enumeration(blocks);
            sequenceInputStream = new SequenceInputStream(enumeration);
            File file = new File("E:\\receive\\123.mp4");
            out = new FileOutputStream(file);
            return IOUtils.copyLarge(sequenceInputStream, out);
        } finally {
            IOUtils.closeQuietly(sequenceInputStream);
            IOUtils.closeQuietly(out);
        }
    }
}