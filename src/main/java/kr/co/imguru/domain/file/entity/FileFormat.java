package kr.co.imguru.domain.file.entity;

import lombok.Getter;

@Getter
public class FileFormat {

    private String fileName;

    private String fileUrl;

    private String fileExtension;

    private String fileCategory;

    private Long fileKey;

    public FileFormat(File file) {
        this.fileName = file.getFileName();
        this.fileUrl = file.getFileUrl();
        this.fileExtension = file.getFileExtension();
        this.fileCategory = file.getFileCategory();
        this.fileKey = file.getFileKey();
    }

}
