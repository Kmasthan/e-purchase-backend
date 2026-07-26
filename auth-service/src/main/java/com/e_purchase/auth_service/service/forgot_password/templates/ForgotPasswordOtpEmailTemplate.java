package com.e_purchase.auth_service.service.forgot_password.templates;

import java.time.Year;

public class ForgotPasswordOtpEmailTemplate {

    private ForgotPasswordOtpEmailTemplate() {
    }

    public static String build(String otp, int validMinutes, String firstName) {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8"/>
                    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                    <title>Your OTP Code</title>
                </head>
                <body style="margin:0;padding:0;background-color:#f4f4f4;font-family:Arial,sans-serif;">
                    <table width="100%%" cellpadding="0" cellspacing="0">
                        <tr>
                            <td align="center" style="padding:40px 0;">
                                <table width="460" cellpadding="0" cellspacing="0" style="background-color:#ffffff;border-radius:8px;overflow:hidden;box-shadow:0 2px 8px rgba(0,0,0,0.1);">
                                    <!-- Header -->
                                    <tr>
                                        <td align="center" style="background-color:#4f46e5;padding:24px;">
                                            <h1 style="margin:0;color:#ffffff;font-size:22px;font-weight:bold;">Your OTP Code</h1>
                                        </td>
                                    </tr>
                                    <!-- Body -->
                                    <tr>
                                        <td style="padding:32px 36px;">
                                            <p style="margin:0 0 12px;color:#333333;font-size:15px;">Hello %s,</p>
                                            <p style="margin:0 0 24px;color:#333333;font-size:15px;">Your One-Time Password (OTP) for account verification is:</p>
                                            <!-- OTP Box -->
                                            <table width="100%%" cellpadding="0" cellspacing="0">
                                                <tr>
                                                    <td align="center" style="background-color:#f0f0f0;border-radius:6px;padding:20px;">
                                                        <span style="font-size:32px;font-weight:bold;color:#4f46e5;letter-spacing:6px;">%s</span>
                                                    </td>
                                                </tr>
                                            </table>
                                            <p style="margin:24px 0 12px;color:#333333;font-size:15px;">
                                                This OTP is valid for <strong>%d minutes</strong>. Please do not share this code with anyone.
                                            </p>
                                            <p style="margin:0 0 6px;color:#333333;font-size:15px;">If you didn't request this code, please ignore this email.</p>
                                            <p style="margin:0;color:#333333;font-size:15px;">Thank you for using our service!</p>
                                        </td>
                                    </tr>
                                    <!-- Footer -->
                                    <tr>
                                        <td align="center" style="background-color:#f9f9f9;padding:16px;border-top:1px solid #eeeeee;">
                                            <p style="margin:0;color:#aaaaaa;font-size:12px;">&copy; %d e-purchase. All rights reserved.</p>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </body>
                </html>
                """.formatted(firstName, otp, validMinutes, Year.now().getValue());
    }
}
