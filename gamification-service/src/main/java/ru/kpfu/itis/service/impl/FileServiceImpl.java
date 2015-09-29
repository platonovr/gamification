package ru.kpfu.itis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.service.FileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static java.io.File.separator;

/**
 * Created by timur on 21.06.15.
 */
@Service("fileService")
@PropertySource("classpath:config.properties")
public class FileServiceImpl implements FileService {

    @Autowired
    private Environment env;

    private String attachmentsPrefix = "attachments";

    @Override
    public String uploadTaskAttachment(MultipartFile file, Long taskId) throws IOException {
        if (file == null | taskId == null) throw new NullPointerException("attachment and taskId should be specified");
        String filesDirectory = getFilesDirectory();
        String attachmentsDirectory = attachmentsPrefix + separator + taskId + separator;
        Path attachmentPath = Paths.get(filesDirectory + attachmentsDirectory);
        if (Files.notExists(attachmentPath))
            Files.createDirectories(attachmentPath);
        file.transferTo(new File(attachmentPath.toString() + separator + file.getOriginalFilename()));
        return attachmentsDirectory + file.getOriginalFilename();
    }

    private String getFilesDirectory() {
        String filesDirectory = env.getProperty("image.upload.path");
        if (!filesDirectory.endsWith(separator))
            filesDirectory += separator;
        return filesDirectory;
    }

    @Override
    public List<String> getTaskAttachmentsNames(Long taskId) {
        File[] attachments = new File(getFilesDirectory() + attachmentsPrefix + separator + taskId).listFiles();
        List<String> attachmentsNames = new LinkedList<>();
        if (attachments != null && attachments.length > 0)
            for (File attachment : attachments)
                attachmentsNames.add(attachment.getName());
        return Collections.unmodifiableList(attachmentsNames);
    }

    @Override
    public File[] getTaskFiles(Long taskId) {
        File[] attachments = null;
        attachments = new File(getFilesDirectory() + attachmentsPrefix + separator + taskId).listFiles();
        return attachments;
    }

}
