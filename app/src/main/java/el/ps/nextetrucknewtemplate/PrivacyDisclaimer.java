package el.ps.nextetrucknewtemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class PrivacyDisclaimer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_disclaimer);

        TextView privacyDisclaimerTextView = findViewById(R.id.privacyDisclaimerTextView);
        String privacyDisclaimer = "Privacy Disclaimer for NextETRUCK Web and Mobile Applications\n\n" +
                "Last Updated: June 07, 2024\n\n" +
                "1. Introduction: At NextETRUCK, we are dedicated to ensuring the privacy and data protection rights of our users, in compliance with the General Data Protection Regulation (GDPR) and other national applicable data protection laws. This GDPR Compliant Privacy Disclaimer outlines how we collect, use, and protect personal data when you use our web and mobile applications as an operator or professional driver. Please carefully review this disclaimer to understand your rights and our data processing practices.\n\n" +
                "2. Data Controller: NextETRUCK, as the data controller, is responsible for processing your personal data as described in this Privacy Disclaimer. If you have any questions or concerns regarding your data, you can contact us at: dmarg@certh.gr.\n\n" +
                "3. Types of Data We Collect: We collect various types of data from users of our web and mobile applications, including but not limited to:\n\n" +
                "Personal Information: This includes your name, contact details, and other identification data necessary for registration and account creation.\n" +
                "Usage Data: We collect data related to your interactions with our applications, such as the use of specific features, actions taken, and device information.\n" +
                "Driver/Rider Information: If you are an operator, we collect information related to the drivers and riders under your care.\n" +
                "Location Data: We may collect location data primarily for operational purposes, with your explicit consent.\n\n" +
                "4. Legal Basis for Processing: We process your personal data based on one or more legal grounds, including your consent.\n\n" +
                "5. How We Use Your Data: We use your data for the following purposes:\n\n" +
                "Operator Monitoring: If you are an operator, we use your data to monitor the activities of drivers under your responsibility and the performance of the vehicles under your ownership.\n" +
                "Driver activities: If you are a driver, we use your data during operations.\n" +
                "Communication: We may use your contact information to send alerts, notifications, and important updates related to your role.\n" +
                "Performance Improvement: We analyze usage data to enhance the functionality and performance of our applications.\n\n" +
                "6. Data Sharing: We will NOT share your data with third parties, but only in accordance with this Privacy Disclaimer and applicable laws. Your data may be shared with:\n\n" +
                "Authorized Personnel: Operator information may be accessible to authorized personnel for monitoring and data analysis purposes.\n\n" +
                "7. International Data Transfers: Your data will NOT be transferred and processed in countries outside the European Economic Area (EEA) and or outside the NextETRUCK Consortium. You may find more information about the project and the partners in the NextETRUCK website: https://nextetruck.eu/.\n\n" +
                "8. Data Security: We implement robust security measures to protect your data from unauthorized access, disclosure, alteration, or destruction. However, no system can be entirely secure, and you use our applications at your own risk.\n\n" +
                "9. Your Rights: Under GDPR, you have specific rights related to your personal data, including the right to access, rectify, erase, restrict processing, and data portability. You also have the right to object to processing based on legitimate interests or for direct marketing purposes.\n\n" +
                "10. Updates to This Privacy Disclaimer: We may update this Privacy Disclaimer to reflect changes in our practices and legal requirements. Any updates will be published on our website.\n\n" +
                "11. Contact Us: If you have any questions or concerns about your privacy, data protection, or this Privacy Disclaimer, please contact us at: dmarg@certh.gr.\n\n" +
                "By using our web and mobile applications, you consent to the terms outlined in this GDPR Compliant Privacy Disclaimer. Your continued use of our services constitutes acceptance of any updates or modifications to this disclaimer.";

        privacyDisclaimerTextView.setText(privacyDisclaimer);
    }
}