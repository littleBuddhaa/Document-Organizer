package com.bellatrix.aditi.documentorganizer.Utilities;

/**
 * Created by Aditi on 13-03-2019.
 */

public class Folder {
    public String folderName, color;
    Folder(String folderName, String color) {
        this.folderName = folderName;
        this.color = color;
    }

    public void setFolderName(Folder folder, String folderName) {
        folder.folderName = folderName;
    }

    public void setColor(Folder folder, String color) {
        folder.color = color;
    }
}
