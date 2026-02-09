package com.project.demo.material.domain;

import java.util.Arrays;

public enum MaterialType {
    LINK("text/plain","text"),IMAGE("image/jpeg","img"),PDF("application/pdf","pdf");

    private final String mimeType;
    private final String folderName;

    MaterialType(String mimeType,String folderName){
        this.mimeType=mimeType;
        this.folderName=folderName;
    }
    public String getMimeType(){
        return mimeType;
    }

    public String getFolderName() {
        return folderName;
    }

    public static MaterialType findByMimeType(String mimeType) {
        return Arrays.stream(MaterialType.values())
                .filter(type -> type.mimeType.equals(mimeType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 파일 형식입니다: " + mimeType));
    }
}
