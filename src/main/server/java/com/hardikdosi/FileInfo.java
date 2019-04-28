package com.hardikdosi;

/**
 * Created by Hardik Dosi on 11/16/2016.
 */
public class FileInfo {
    private String username;
    private String fileName;
    private String data;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new StringBuffer(" username : ").append(this.username)
                .append(" fileName : ").append(this.fileName)
                .append(" data : ").append(this.data).toString();
    }

}
