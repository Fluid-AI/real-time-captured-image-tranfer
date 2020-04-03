package ai.fluid.util.realtimeimagetransfer.controller;

import ai.fluid.util.realtimeimagetransfer.util.FilePathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

@Slf4j
@RestController
public class ClientSenderController {

    private static final int MIN_FILE_COUNT = 3;

    @GetMapping("client-sender")
    public ModelAndView clientSender(ModelAndView modelAndView) {
        return new ModelAndView("client-sender");
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException, URISyntaxException {
        saveRealTimeImageInDir(file);
        return "success";
    }

    void saveRealTimeImageInDir(MultipartFile newImageFile) throws IOException, URISyntaxException {

        File newFile = new File(FilePathUtil.imageAbsolutePathWithName());
        log.debug("Image will be stored at {}",newFile.getAbsolutePath());

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
