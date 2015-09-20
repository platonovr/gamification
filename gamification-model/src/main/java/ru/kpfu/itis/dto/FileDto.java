package ru.kpfu.itis.dto;

/**
 * Created by Roman on 21.09.2015.
 */
public class FileDto {

    private String name;

    private String extension;

    private byte[] data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
