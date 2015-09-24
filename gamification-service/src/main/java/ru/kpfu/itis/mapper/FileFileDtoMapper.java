package ru.kpfu.itis.mapper;

import com.google.common.io.Files;
import ru.kpfu.itis.dto.FileDto;

import java.io.File;
import java.util.function.Function;

/**
 * Created by Roman on 25.09.2015.
 */
public class FileFileDtoMapper implements Function<File, FileDto> {

    @Override
    public FileDto apply(File file) {
        FileDto fileDto = new FileDto();
        fileDto.setName(file.getName());
        fileDto.setExtension(Files.getFileExtension(file.getName()));
        fileDto.setURL(file.getName());
        return fileDto;
    }
}
