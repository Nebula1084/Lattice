package com.sea.lattice.content;

import java.io.File;
import java.util.Date;

/**
 * Created by Sea on 10/27/2015.
 */
public class Backup {
    private File file;
    private String name;
    private Date date;

    public Backup(File file){
        this.file = file;
        this.date = new Date(file.lastModified());
        this.name = file.getName();

    }

    public File getFile(){
        return file;
    }

    public String getName(){
        return name;
    }

    public Date getDate(){
        return date;
    }
}
