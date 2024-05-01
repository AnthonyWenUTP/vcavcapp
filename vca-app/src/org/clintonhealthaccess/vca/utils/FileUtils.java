package org.clintonhealthaccess.vca.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Environment;
import android.util.Log;

public final class FileUtils {
    private final static String tag = "FileUtils";
    
    // Storage paths
    public static final String VCA_ROOT = Environment.getExternalStorageDirectory() + "/vca/";
    public static final String DATABASE_PATH = VCA_ROOT + "databases/";
    public static final String BACKUP_PATH = Environment.getExternalStorageDirectory() + "/vca01/";
    public static final String BACKUP_FILE = BACKUP_PATH + "vcacryp.sqlite3";
    
    
    public static final boolean deleteFolder(String path) {
        // not recursive
        if (path != null && storageReady()) {
            File dir = new File(path);
            if (dir.exists() && dir.isDirectory()) {
                File[] files = dir.listFiles();
                for (File file : files) {
                    if (!file.delete()) {
                        Log.i(tag, "Failed to delete " + file);
                    }
                }
            }
            return dir.delete();
        } else {
            return false;
        }
    }


    public static final boolean createFolder(String path) {
        if (storageReady()) {
            boolean made = true;
            File dir = new File(path);
            if (!dir.exists()) {
                made = dir.mkdirs();
            }
            return made;
        } else {
            return false;
        }
    }

    public static final boolean existFile(String path) {
    	 boolean exist = false;
        if (storageReady()) {
            File dir = new File(path);
            if (dir.exists()) {
                exist = true;
            }
        }
        return exist;
    }
    

    public static final boolean deleteFile(String path) {
        if (storageReady()) {
            File f = new File(path);
            return f.delete();
        } else {
            return false;
        }
    }
    
    public static boolean storageReady() {
        String cardstatus = Environment.getExternalStorageState();
        if (cardstatus.equals(Environment.MEDIA_REMOVED)
                || cardstatus.equals(Environment.MEDIA_UNMOUNTABLE)
                || cardstatus.equals(Environment.MEDIA_UNMOUNTED)
                || cardstatus.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            return false;
        } else {
            return true;
        }
    }
    
    public static void copy(File src, File dst) throws IOException {
    	if (storageReady()) {
    		if (dst.exists()) {
    			dst.delete();
            }
	        InputStream in = new FileInputStream(src);
	        try {
	            OutputStream out = new FileOutputStream(dst);
	            try {
	                // Transfer bytes from in to out
	                byte[] buf = new byte[1024];
	                int len;
	                while ((len = in.read(buf)) > 0) {
	                    out.write(buf, 0, len);
	                }
	            } finally {
	                out.close();
	            }
	        } finally {
	            in.close();
	        }
    	}
    }
}
