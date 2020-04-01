package ru.itis.servlets.services;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.servlets.dto.FileDto;
import ru.itis.servlets.models.FileInfo;
import ru.itis.servlets.security.defails.UserDetailsImpl;
import ru.itis.servlets.repositories.FilesRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    private FilesRepository filesRepository;
    private String path;
    private String serverAddress;

    public FileServiceImpl(String filePath, String serverAddress, FilesRepository filesRepository) {
        this.serverAddress = serverAddress;
        this.path = filePath;
        this.filesRepository = filesRepository;
    }

    @Override
    public FileDto saveFile(MultipartFile multipartFile, UserDetailsImpl user) {
        String name = getNewFilename(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileInfo info = FileInfo.builder().originalFileName(multipartFile.getOriginalFilename())
                .ownerId(user.getUser().getId()).size(multipartFile.getSize())
                .type(multipartFile.getContentType()).storageFileName(name)
                .url(serverAddress + name).build();
        File file = new File(path + File.separator + name);
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        filesRepository.save(info);

        return FileDto.builder().userEmail(user.getUser().getMail()).userName(user.getUser().getNick()).originalName(info.getOriginalFileName()).size(info.getSize()).url(info.getUrl()).build();
    }

    private String getNewFilename(String originalFilename) {
        String[] strings = originalFilename.split("\\.");
        return UUID.randomUUID().toString() + "." + strings[strings.length - 1];
    }

    @Override
    public String[] getFile(String fileName, UserDetailsImpl user) {
        Optional<FileInfo> fileInfo = filesRepository.getFileByName(fileName);
        if (fileInfo.isPresent()) {
            FileInfo fileInfo1 = fileInfo.get();
            if (fileInfo1.getOwnerId().equals(user.getUser().getId())) {
                return new String[]{path + File.separator + fileName, fileInfo1.getType(), fileInfo1.getOriginalFileName()};
            } else {
                return new String[]{"Error", "Illegal Access"};
            }
        } else {
            return new String[]{"Error", "File not found"};
        }
    }

    @Override
    public void writeToResponse(String[] arguments, HttpServletResponse httpServletResponse) {
        try (InputStream inputStream = new FileInputStream(new File(arguments[0]));) {
            httpServletResponse.setContentType(arguments[1]);
            httpServletResponse.setHeader("Content-Disposition", "attachment; filename=\" " + arguments[2] + "\"");
            IOUtils.copy(inputStream, httpServletResponse.getOutputStream());
            httpServletResponse.getOutputStream().flush();
            httpServletResponse.getOutputStream().close();
        } catch (IOException e) {
            throw new IllegalStateException("Something went wrong while delivering file");
        }
    }

    @Override
    public List<FileInfo> getFiles(Long id) {
        return filesRepository.findAllById(id);
    }
}
