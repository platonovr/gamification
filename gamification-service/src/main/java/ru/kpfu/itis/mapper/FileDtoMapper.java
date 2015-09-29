package ru.kpfu.itis.mapper;

import com.google.common.base.Joiner;
import com.google.common.io.Files;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.dto.FileDto;
import ru.kpfu.itis.model.Task;

import java.io.File;

/**
 * Created by Roman on 25.09.2015.
 */
@Component
public class FileDtoMapper implements Mapper<File, FileDto> {
    private String attachmentsPrefix = "attachments";

    @Override
    public File fromDto(FileDto dto) {
        return null;
    }

    @Override
    public FileDto toDto(File file) {
        FileDto fileDto = new FileDto();
        fileDto.setName(file.getName());
        fileDto.setExtension(Files.getFileExtension(file.getName()));
        fileDto.setURL(file.getName());
        fileDto.setName(fileDto.getName());
        return fileDto;
    }

    public FileDto toDto(File file, Task task) {
        FileDto fileDto = toDto(file);
        fileDto.setURL(Joiner.on("/").join(attachmentsPrefix, task.getId(), fileDto.getURL()));
        return fileDto;
    }
}
