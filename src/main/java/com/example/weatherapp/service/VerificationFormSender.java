package com.example.weatherapp.service;

import com.example.weatherapp.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class VerificationFormSender {
    private final TemplateEngine templateEngine;
    private final String subjectMessage;
    private final String verificationUrl;
    private final SenderService senderService;

    public VerificationFormSender(TemplateEngine templateEngine,
                                  @Value("${api.subject.message}") String subjectMessage,
                                  @Value("${api.verification.url}") String verificationUrl,
                                  SenderService senderService) {
        this.templateEngine = templateEngine;
        this.subjectMessage = subjectMessage;
        this.verificationUrl = verificationUrl;
        this.senderService = senderService;
    }

    public void sendMessage(User user) {
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("verificationUrl", String.format(verificationUrl, user.getVerificationCode()));
        String emailContent = templateEngine.process("email-template", context);
        senderService.sendMessage(user.getEmail(), subjectMessage, emailContent);
    }
}