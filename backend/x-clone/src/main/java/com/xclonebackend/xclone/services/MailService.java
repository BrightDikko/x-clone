package com.xclonebackend.xclone.services;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.xclonebackend.xclone.exceptions.EmailFailedToSendException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

//Platform to generate temporary burner email for testing purposes: https://temp-mail.org/en/

@Service
@RequiredArgsConstructor
public class MailService {

    private final Gmail gmailService;

    public void sendEmail(String toAddress, String subject, String content) throws Exception {
        Properties props = new Properties();

        Session session = Session.getInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        try {
            email.setFrom(new InternetAddress("devbydikko@gmail.com"));
            email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(toAddress));
            email.setSubject(subject);
            email.setText(content);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            email.writeTo(buffer);

            byte[] rawMessageBytes = buffer.toByteArray();

            String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);

            Message message = new Message();
            message.setRaw(encodedEmail);

            message = gmailService.users().messages().send("me", message).execute();
        } catch (Exception e) {
            throw new EmailFailedToSendException();
        }
    }

}
