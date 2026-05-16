package com.lamdayne.minihrm.service;

import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;

public interface MailService {
    void sendEmail(String recipients, String subject, String content, MultipartFile[] files)
            throws MessagingException, UnsupportedEncodingException;

    void sendTempPassword(String email, String password) throws MessagingException, UnsupportedEncodingException;
}
