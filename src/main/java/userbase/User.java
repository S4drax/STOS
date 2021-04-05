package userbase;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class User {
    private String login;
    private String password;
    private int accessLevel;
    private String qrCodePswd; //TODO: zrobić mechanizm czytania QR kodu przez kamerkę
}
