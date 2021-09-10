package com.example.socialnetworkingapp.export;

/* https://newbedev.com/how-to-convert-byte-array-to-multipartfile */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@AllArgsConstructor
@Getter
@Setter
public class ByteArrMultipartFile implements MultipartFile {

    private final byte[] fileContent;
    private String fileName;
    private String contentType;
    private File file;
    private String destPath;
    private FileOutputStream fileOutputStream;

    public ByteArrMultipartFile(byte[] fileData, String name, String destPath) {
        this.destPath = destPath;
        this.fileContent = fileData;
        this.fileName = name;
        this.file = new File(destPath + fileName);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        fileOutputStream = new FileOutputStream(dest);
        fileOutputStream.write(fileContent);
    }

    public void clearOutStreams() throws IOException {
        if (null != fileOutputStream) {
            fileOutputStream.flush();
            fileOutputStream.close();
            file.deleteOnExit();
        }
    }

    @Override
    public String getName() {
        return this.destPath + this.fileName;
    }

    @Override
    public String getOriginalFilename() {
        return this.fileName;
    }

    @Override
    public boolean isEmpty() {
        return this.fileContent.length > 0;
    }

    @Override
    public long getSize() {
        return this.fileContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return fileContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(fileContent);
    }

    @Override
    public String getContentType() {
        if(this.fileName.contains("xml")) {
            return "text/xml";
        }
        if(this.fileName.contains("json")) {
            return "text/json";
        }
        return null;
    }
}