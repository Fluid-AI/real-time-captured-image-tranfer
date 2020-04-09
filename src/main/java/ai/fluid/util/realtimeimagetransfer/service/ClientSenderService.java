package ai.fluid.util.realtimeimagetransfer.service;

import ai.fluid.util.realtimeimagetransfer.util.FilePathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

@Slf4j
@Service
public class ClientSenderService {

    private static final int MIN_FILE_COUNT = 3;

    public void saveRealTimeImageInDir(MultipartFile newImageFile) throws IOException {

        File newFile = new File(FilePathUtil.imageAbsolutePathWithName());
        log.debug("Image will be stored at {}", newFile.getAbsolutePath());

        newImageFile.transferTo(newFile.toPath());
        int totalOldDeletedFiles = deleteOldFilesGtN(newFile.getParentFile());
        log.debug("total old deleted files are {} ", totalOldDeletedFiles);
    }

    int deleteOldFilesGtN(File fileDirectory) {
        File[] allFiles = fileDirectory.listFiles();

        if (allFiles == null || allFiles.length < MIN_FILE_COUNT) {
            return 0;
        }

        Arrays.sort(allFiles, (f1, f2) -> {
            return Long.compare(f2.lastModified(), f1.lastModified());
        });

        for (int i = MIN_FILE_COUNT; i < allFiles.length; i++) {
            File fileToDelete = allFiles[i];
            if (fileToDelete.delete()) {
                log.debug("Previous Old file deleted is {}", fileToDelete.getName());
            }
        }
        return allFiles.length - MIN_FILE_COUNT;
    }

}