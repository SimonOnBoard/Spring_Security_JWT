package ru.itis.servlets.services;

import org.springframework.web.multipart.MultipartFile;
import ru.itis.servlets.dto.FileDto;
import ru.itis.servlets.models.FileInfo;
import ru.itis.servlets.security.defails.UserDetailsImpl;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface FileService {
    FileDto saveFile(MultipartFile multipartFile, UserDetailsImpl user);
    String[] getFile(String fileName, UserDetailsImpl user);
    void writeToResponse(String[] arguments, HttpServletResponse httpServletResponse);

    List<FileInfo> getFiles(Long user);
}
