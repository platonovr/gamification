package ru.kpfu.itis.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by timur on 21.06.15.
 */
public interface FileService {

    /**
     * @return path to the uploaded file
     * @throws IOException
     */
    String uploadTaskAttachment(MultipartFile file, Long taskId) throws IOException;

    Collection<String> getTaskAttachmentsNames(Long taskId);
}
