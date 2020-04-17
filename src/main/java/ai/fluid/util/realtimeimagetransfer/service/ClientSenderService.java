package ai.fluid.util.realtimeimagetransfer.service;

import ai.fluid.util.realtimeimagetransfer.util.FilePathUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
@Service
public class ClientSenderService {

    @Value("${config.limit:1}")
    private int imageLimit;

    @Value("${config.interval:25}")
    private int captureImageInterval;

    public int getCaptureImageInterval() {
        return captureImageInterval;
    }

    public void saveRealTimeImageInDir(MultipartFile newImageFile) throws IOException {

        File newFile = new File(FilePathUtil.imageAbsolutePathWithName());
        log.debug("Image will be stored at {}", newFile.getAbsolutePath());

        newImageFile.transferTo(newFile.toPath());
        int totalOldDeletedFiles = deleteOldFilesGtN(newFile.getParentFile());
        log.debug("total old deleted files are {} ", totalOldDeletedFiles);
    }

    int deleteOldFilesGtN(File fileDirectory) {
        File[] allFiles = fileDirectory.listFiles();

        if (allFiles == null || allFiles.length < imageLimit) {
            return 0;
        }

        Arrays.sort(allFiles, (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()));

        for (int i = imageLimit; i < allFiles.length; i++) {
            File fileToDelete = allFiles[i];
            if (fileToDelete.delete()) {
                log.debug("Previous Old file deleted is {}", fileToDelete.getName());
            }
        }
        return allFiles.length - imageLimit;
    }

}
