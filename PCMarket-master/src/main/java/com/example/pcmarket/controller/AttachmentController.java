package com.example.pcmarket.controller;

import com.example.pcmarket.dto.ApiResponse;
import com.example.pcmarket.entity.Attachment;
import com.example.pcmarket.entity.AttachmentContent;
import com.example.pcmarket.repository.AttachmentContentRepository;
import com.example.pcmarket.repository.AttachmentRepository;
import com.example.pcmarket.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/attachment")
@RestController
@RequiredArgsConstructor
public class AttachmentController {

    @Autowired
    AttachmentService attachmentService;
    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    @GetMapping("/info")
    public List<Attachment> getAll() {
        List<Attachment> all = attachmentRepository.findAll();
        return all;
    }

    @PostMapping("/upload")
    @SneakyThrows
    public ApiResponse upload(MultipartHttpServletRequest request)  {
        //  /file/upload

        Iterator<String> fileNames = request.getFileNames();

        if (fileNames.hasNext()) {
            String fileName = fileNames.next();

            MultipartFile file = request.getFile(fileName);

            Attachment attachment = new Attachment();

            attachment.setName(file.getOriginalFilename());
            attachment.setSize(file.getSize());
            attachment.setType(file.getContentType());

            Attachment attachment1 = attachmentRepository.save(attachment);
            // file info saved

            AttachmentContent attachmentContent = new AttachmentContent();

            attachmentContent.setAttachment(attachment1);
            attachmentContent.setBytes(file.getBytes());

            AttachmentContent save = attachmentContentRepository.save(attachmentContent);

            return new ApiResponse("saved",true,attachment1);
        }
        return new ApiResponse("something went wrong",false);
    }

    @PostMapping("/files")
    public ApiResponse uploadFile(MultipartHttpServletRequest request) {
        return attachmentService.uploadFile(request);
    }

    @GetMapping("/download/{id}")
    public void getFile(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        Optional<Attachment> byId = attachmentRepository.findById(id);
        if (byId.isPresent()) {
            Attachment attachment = byId.get();
            Optional<AttachmentContent> byAttachment = attachmentContentRepository.findByAttachment(attachment);
            if(byAttachment.isPresent()){
                AttachmentContent attachmentContent = byAttachment.get();
                response.setHeader("Content-Disposition","attachment; filename:"+ attachment.getName());
                response.setContentType(attachment.getType());
                FileCopyUtils.copy(attachmentContent.getBytes(), response.getOutputStream());
            }
        }
    }
    private static final String uploadDirectory = "filelar";
    @GetMapping("dowloadSystem/{id}")
    public void getFileTofileSystem(@PathVariable Integer id,HttpServletResponse response) throws IOException {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isPresent()){
            Attachment attachment = optionalAttachment.get();
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + attachment.getName());
            response.setContentType(attachment.getType());

            FileInputStream fileInputStream=new FileInputStream(uploadDirectory+"/"+attachment.getName());

            FileCopyUtils.copy(fileInputStream, response.getOutputStream());
        }
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id){
        Optional<Attachment> byId = attachmentRepository.findById(id);
        return ResponseEntity.ok().body(byId.get());
    }

}
