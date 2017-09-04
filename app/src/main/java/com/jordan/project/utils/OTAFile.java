package com.jordan.project.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by icean on 2017/5/4.
 */

public class OTAFile {
    public static final int fileChunkSize = 20;

    private InputStream inputStream;
    private byte crc;
    private byte[] bytes;

    private byte[][][] blocks;

    private int fileBlockSize = 0;
    private int bytesAvailable;
    private int numberOfBlocks = -1;
    private int chunksPerBlockCount;
    private int totalChunkCount;

    private OTAFile(InputStream input_stream) throws IOException {
        inputStream = input_stream;
        bytesAvailable = inputStream.available();
    }

    public static OTAFile getByFileName(String file_dir, String filename) throws IOException {
        // Get the file and store it in fileStream
        InputStream is = new FileInputStream(file_dir + File.separator + filename);
        return new OTAFile(is);
    }

    public void setType(int type) throws IOException {
        this.bytes = new byte[this.bytesAvailable + 1];
        this.inputStream.read(this.bytes);
        this.crc = getCrc();
        this.bytes[this.bytesAvailable] = this.crc;
    }

    public int getFileBlockSize() {
        return this.fileBlockSize;
    }

    public int getNumberOfBytes() {
        return this.bytes.length;
    }

    public void setFileBlockSize(int fileBlockSize) {
        this.fileBlockSize = fileBlockSize;
        this.chunksPerBlockCount = (int) Math.ceil((double) fileBlockSize / (double) fileChunkSize);
        this.numberOfBlocks = (int) Math.ceil((double) bytes.length / (double) fileBlockSize);
        this.initBlocks();
    }

    private void initBlocksSuota() {
        int totalChunkCounter = 0;
        blocks = new byte[this.numberOfBlocks][][];
        int byteOffset = 0;
        for (int i = 0; i < this.numberOfBlocks; i++) {
            int blockSize = this.fileBlockSize;
            if (i + 1 == this.numberOfBlocks) {
                blockSize = this.bytes.length % this.fileBlockSize;
            }
            int numberOfChunksInBlock = (int) Math.ceil((double) blockSize / fileChunkSize);
            int chunkNumber = 0;
            blocks[i] = new byte[numberOfChunksInBlock][];
            for (int j = 0; j < blockSize; j += fileChunkSize) {
                int chunkSize = fileChunkSize;
                if (byteOffset + fileChunkSize > this.bytes.length) {
                    chunkSize = this.bytes.length - byteOffset;
                }
                else if (j + fileChunkSize > blockSize) {
                    chunkSize = this.fileBlockSize % fileChunkSize;
                }

                byte[] chunk = Arrays.copyOfRange(this.bytes, byteOffset, byteOffset + chunkSize);
                blocks[i][chunkNumber] = chunk;
                byteOffset += chunkSize;
                chunkNumber++;
                totalChunkCounter++;
            }
        }
        this.totalChunkCount = totalChunkCounter;
    }


    private void initBlocksSpota() {
        this.numberOfBlocks = 1;
        this.fileBlockSize = this.bytes.length;
        this.totalChunkCount = (int) Math.ceil((double) this.bytes.length / (double) fileChunkSize);
        this.blocks = new byte[numberOfBlocks][this.totalChunkCount][];
        int byteOffset = 0;
        int chunkSize = fileChunkSize;
        for (int i = 0; i < this.totalChunkCount; i++) {
            if (byteOffset + fileChunkSize > this.bytes.length) {
                chunkSize = this.bytes.length - byteOffset;
            }
            byte[] chunk = Arrays.copyOfRange(this.bytes, byteOffset, byteOffset + chunkSize);
            blocks[0][i] = chunk;
            byteOffset += fileChunkSize;
        }
    }

    private void initBlocks() {
        initBlocksSuota();
        //initBlocksSpota();
    }

    public byte[][] getBlock(int index) {
        return blocks[index];
    }

    public void close() {
        if (this.inputStream != null) {
            try {
                this.inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getNumberOfBlocks() {
        return this.numberOfBlocks;
    }

    public int getChunksPerBlockCount() {
        return chunksPerBlockCount;
    }

    public int getTotalChunkCount() {
        return this.totalChunkCount;
    }

    private byte getCrc() throws IOException {
        byte crc_code = 0;
        for (int i = 0; i < this.bytesAvailable; i++) {
            Byte byteValue = this.bytes[i];
            int intVal = byteValue.intValue();
            crc_code ^= intVal;
        }
        return crc_code;
    }
}
