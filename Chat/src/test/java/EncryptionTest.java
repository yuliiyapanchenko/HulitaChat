import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptionTest {

    public static void main(String[] args) {
        String password_1 = "123";
        String password_2 = "456";

        EncryptionTest encryptionTest = new EncryptionTest();

        System.out.println("MD5 hash:");
        System.out.println(password_1 + " -> " + encryptionTest.md5hash(password_1));
        System.out.println(password_2 + " -> " + encryptionTest.md5hash(password_2));

        System.out.println();

        System.out.println("BCrypt hash:");
        System.out.println(password_1 + " -> " + encryptionTest.bcrypt(password_1));
        System.out.println(password_2 + " -> " + encryptionTest.bcrypt(password_2));
    }

    public String md5hash(String password) {
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        return encoder.encodePassword(password, null);
    }

    public String bcrypt(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
