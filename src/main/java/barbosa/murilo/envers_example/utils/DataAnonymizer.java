package barbosa.murilo.envers_example.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class DataAnonymizer {

    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) {
            return email;
        }
        String[] parts = email.split("@");
        String local = parts[0];
        String domain = parts[1];
        String maskedLocal = local.length() <= 2 ? "**" : local.substring(0, 2) + "***";
        String maskedDomain = domain.contains(".") ? domain.substring(0, 1) + "***" + domain.substring(domain.lastIndexOf(".")) : "***";
        return maskedLocal + "@" + maskedDomain;
    }

    public static String maskName(String name) {
        if (name == null || name.isBlank()) {
            return name;
        }
        String[] parts = name.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            if (part.length() > 1) {
                sb.append(part.charAt(0)).append("*** ");
            } else {
                sb.append("*** ");
            }
        }
        return sb.toString().trim();
    }

    public static String hashEmail(String email) {
        if (email == null) {
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(email.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
