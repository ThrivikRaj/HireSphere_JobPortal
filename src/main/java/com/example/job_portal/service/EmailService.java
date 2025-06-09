package com.example.job_portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email to " + to + ": " + e.getMessage(), e);
        }
    }

    public void sendWelcomeEmail(String email, String name) {
        String subject = "Welcome to Job Portal!";
        String content = "<h2>Welcome to Job Portal!</h2>"
                + "<p>Dear " + name + ",</p>"
                + "<p>Thank you for joining our platform. We're excited to have you on board!</p>"
                + "<p>You can now:</p>"
                + "<ul>"
                + "<li>Complete your profile</li>"
                + "<li>Search for jobs</li>"
                + "<li>Apply to positions</li>"
                + "<li>Track your applications</li>"
                + "</ul>"
                + "<p>If you have any questions, feel free to contact our support team.</p>"
                + "<br><p>Best Regards,<br>Job Portal Team</p>";
        sendEmail(email, subject, content);
    }

    public void sendJobApplicationEmail(String applicantEmail, String recruiterEmail, String jobTitle) {
        sendEmail(applicantEmail, "Job Application Submitted", 
                  "<h2>Job Application Submitted</h2>"
                  + "<p>You have successfully applied for the job: <strong>" + jobTitle + "</strong>.</p>");

        sendEmail(recruiterEmail, "New Job Application Received",
                  "<h2>New Job Application</h2>"
                  + "<p>A new application has been submitted for your job: <strong>" + jobTitle + "</strong>.</p>");
    }

    public void sendJobAcceptedEmail(String applicantEmail, String jobTitle) {
        sendEmail(applicantEmail, "Job Application Accepted",
                  "<h2>Congratulations!</h2>"
                  + "<p>Your application for <strong>" + jobTitle + "</strong> has been accepted.</p>");
    }

    public void sendNewUserEmail(String email, String name) {
        sendEmail(email, "Welcome to Job Portal", 
                  "<h2>Welcome, " + name + "!</h2>"
                  + "<p>Thank you for registering on our Job Portal. We are excited to have you!</p>");
    }

    public void sendNewCompanyEmail(String email, String companyName) {
        sendEmail(email, "Company Registration Successful", 
                  "<h2>Welcome, " + companyName + "!</h2>"
                  + "<p>Your company has been successfully registered on our platform.</p>");
    }

    public void sendNewJobEmail(String recruiterEmail, String jobTitle) {
        sendEmail(recruiterEmail, "Job Listing Created", 
                  "<h2>Job Posted Successfully</h2>"
                  + "<p>You have successfully created a new job listing: <strong>" + jobTitle + "</strong>.</p>");
    }
}
