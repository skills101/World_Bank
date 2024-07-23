package com.henry.World.Banking.Application.service;

import com.henry.World.Banking.Application.payload.request.EmailDetails;

public interface EmailService {

    void sendEmailAlert(EmailDetails emailDetails);
}
