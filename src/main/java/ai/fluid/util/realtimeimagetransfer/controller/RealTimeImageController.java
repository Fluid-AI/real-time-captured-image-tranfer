package ai.fluid.util.realtimeimagetransfer.controller;

import ai.fluid.util.realtimeimagetransfer.util.FilePathUtil;
import org.apache.logging.log4j.core.util.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;

@RestController
public class RealTimeImageController {

    @RequestMapping("/image/{imageName}")
    public void realTimeImage(@PathVariable String imageName, HttpServletResponse httpServletResponse) throws IOException {
        File file = new File(FilePathUtil.imagesAbsolutePath() + File.separator + imageName);
//        if (!file.exists()) {
//            return new ResponseEntity<byte[]>(new byte[0], HttpStatus.OK);
//        }
        byte[] b = Files.readAllBytes(file.toPath());
        httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
        ServletOutputStream op = httpServletResponse.getOutputStream();
        op.write(b);
        op.flush();
        op.close();
//    }
//    @RequestMapping(value = "/image-response-entity", method = RequestMethod.GET)
//    public ResponseEntity<byte[]> getImageAsResponseEntity() {
//        HttpHeaders headers = new HttpHeaders();
//        InputStream in = servletContext.getResourceAsStream("/WEB-INF/images/image-example.jpg");
//        byte[] media = IOUtils.toByteArray(in);
//        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
//
//        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
//        return responseEntity;
    }
}
