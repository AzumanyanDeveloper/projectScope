package projectscope.com.scope.utils;



import com.mysema.commons.lang.Pair;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import projectscope.com.scope.exception.FileIncorrectExtensionException;
import projectscope.com.scope.exception.FileNotFoundException;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public interface FileUtil {

    static Pair<String, String> insertFile(String path, MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new FileNotFoundException("No file present");
        }

        String contentType = new MimetypesFileTypeMap().getContentType(multipartFile.getOriginalFilename());
        if (!contentType.contains("image") && !contentType.contains("application/octet-stream")) {
            throw new FileIncorrectExtensionException(contentType);
        }

        File dir = new File(path);
        if (!dir.exists()) {
            final boolean mkdirs = dir.mkdirs();
            if (!mkdirs) {
                throw new FileNotFoundException(path, "");
            }
        }
        String file = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        try {
            multipartFile.transferTo(new File(dir, file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Pair<>(file, contentType);
    }

    static InputStream readFileStream(String imagePath, String userPic) {
        if (StringUtils.isNotEmpty(imagePath) && StringUtils.isNotEmpty(userPic)) {
            try {
                return new FileInputStream(imagePath + File.separator + userPic);
            } catch (IOException e) {
                throw new FileNotFoundException(e.getMessage());
            }
        }
        throw new FileNotFoundException(imagePath, userPic);
    }

    static boolean deleteFile(String filePath, String path) {
        try {
            File file = new File(filePath + File.separator + path);
            return Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            throw new FileNotFoundException(e.getMessage());
        }
    }
}
