package ai.fluid.util.realtimeimagetransfer.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class FilePathUtil {

    public static String imagesAbsolutePath(){
        String rootPath = System.getProperty("user.dir");
        File dir = new File(rootPath + File.separator + "realtime-image-capture");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.getAbsolutePath();
    }
    public static String imageAbsolutePathWithName(){
        return FilePathUtil.imagesAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpeg";
    }
}
