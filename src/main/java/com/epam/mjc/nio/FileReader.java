package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class FileReader {

    public Profile getDataFromFile(File file) {
        StringBuilder txt = new StringBuilder();

        try (RandomAccessFile raf = new RandomAccessFile(file.getPath(), "r"); FileChannel inChannel = raf.getChannel()) {
            ByteBuffer buff = ByteBuffer.allocate(1024);

            while (inChannel.read(buff) > 0) {
                buff.flip();

                while (buff.hasRemaining()) {
                    txt.append((char) buff.get());
                }

                buff.clear();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String[] lines = txt.toString().split("\n");

        String name = null;
        int age = 0;
        String email = null;
        long phone = 0;

        for (String line : lines) {
            if (line.startsWith("Name:")) {
                name = line.substring(5).trim();
            } else if (line.startsWith("Age:")) {
                age = Integer.parseInt(line.substring(4).trim());
            } else if (line.startsWith("Email:")) {
                email = line.substring(6).trim();
            } else if (line.startsWith("Phone:")) {
                phone = Long.parseLong(line.substring(6).trim());
            }
        }

        return new Profile(name, age, email, phone);
    }
}
