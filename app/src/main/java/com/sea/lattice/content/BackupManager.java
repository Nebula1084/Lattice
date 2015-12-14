package com.sea.lattice.content;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sea on 10/27/2015.
 */
public class BackupManager {
    private static BackupManager instance;
    private File backupDir;
    private List<Backup> backupList;
    public static String BACK_DIR = "LatticeBackup";

    private BackupManager() {
        backupDir = new File(Environment.getExternalStorageDirectory(), BACK_DIR);
        backupList = new ArrayList<>();
        for (File file : backupDir.listFiles()) {
            backupList.add(new Backup(file));
        }
    }

    public static BackupManager getInstance() {
        if (instance == null)
            instance = new BackupManager();
        return instance;
    }

    public void backup() {

    }

    public void restore() {

    }

}
