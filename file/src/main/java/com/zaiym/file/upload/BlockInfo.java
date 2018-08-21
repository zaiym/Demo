package com.zaiym.file.upload;

public class BlockInfo {

    /**
     * 完整文件MD5
     */
    private String md5;

    /**
     * 完整文件大小
     */
    private Long fileSize;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 分块文件大小
     */
    private Long blockSize;

    /**
     * 分块文件索引
     */
    private int blockIndex;

    /**
     * 分块文件数量
     */
    private int blockCount;

    public BlockInfo(){
    }

    /**
     * 完整文件MD5
     * @return 完整文件MD5
     */
    public String getMd5() {
        return md5;
    }

    /**
     * 完整文件MD5
     * @param md5 完整文件MD5
     */
    public void setMd5(String md5) {
        this.md5 = md5;
    }

    /**
     * 完整文件大小
     * @return 完整文件大小
     */
    public Long getFileSize() {
        return fileSize;
    }

    /**
     * 完整文件大小
     * @param fileSize 完整文件大小
     */
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * 文件名称
     * @return 文件名称
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 文件名称
     * @param fileName 文件名称
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 分块文件大小
     * @return 分块文件大小
     */
    public Long getBlockSize() {
        return blockSize;
    }

    /**
     * 分块文件大小
     * @param blockSize 分块文件大小
     */
    public void setBlockSize(Long blockSize) {
        this.blockSize = blockSize;
    }

    /**
     * 分块文件索引
     * @return 分块文件索引
     */
    public int getBlockIndex() {
        return blockIndex;
    }

    /**
     * 分块文件索引
     * @param blockIndex 分块文件索引
     */
    public void setBlockIndex(int blockIndex) {
        this.blockIndex = blockIndex;
    }

    /**
     * 获取文件后缀名
     * @return 文件后缀名
     */
    public String getSuffix(){
        return fileName == null ? "" : fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 分块文件数量
     * @return 分块文件数量
     */
    public int getBlockCount() {
        return blockCount;
    }

    /**
     * 分块文件数量
     * @param blockCount 分块文件数量
     */
    public void setBlockCount(int blockCount) {
        this.blockCount = blockCount;
    }
}