package com.example.pcmarket.repository;

import com.example.pcmarket.entity.Attachment;
import com.example.pcmarket.entity.AttachmentContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContent,Integer> {
    Optional<AttachmentContent> findByAttachment(Attachment attachment);
}
