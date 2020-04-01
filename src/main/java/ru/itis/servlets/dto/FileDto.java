package ru.itis.servlets.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDto {
    private String originalName;
    private String userName;
    private String userEmail;
    private String url;
    private Long size;
}
