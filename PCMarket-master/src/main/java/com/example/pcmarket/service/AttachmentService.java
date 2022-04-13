package com.example.pcmarket.service;

import com.example.pcmarket.dto.ApiResponse;
import com.example.pcmarket.entity.Attachment;
import com.example.pcmarket.repository.AttachmentContentRepository;
import com.example.pcmarket.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    final AttachmentRepository attachmentRepository;
    final AttachmentContentRepository attachmentContentRepository;
    @SneakyThrows
    public ApiResponse uploadFile(MultipartHttpServletRequest request) {
        Iterator<String> fileNames = request.getFileNames();
        while (fileNames.hasNext()) {
            List<MultipartFile> files = request.getFiles(fileNames.next());
            if (files.size() > 0) {
                for (MultipartFile file : files) {
                    Attachment attachment = new Attachment(file.getOriginalFilename(), file.getSize(), file.getContentType());
                    Attachment save = attachmentRepository.save(attachment);
                    File upload = new File("src/main/resource/files/" + save.getName());
                    FileOutputStream outputStream = new FileOutputStream(upload);
                    outputStream.write(file.getBytes());
                    outputStream.close();
                }
            }
        }
        return new ApiResponse("Zo'r", true);
    }

}
