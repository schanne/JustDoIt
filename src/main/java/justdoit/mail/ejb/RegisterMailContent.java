package justdoit.mail.ejb;

import justdoit.mail.ejb.MailContent;
import javax.ejb.Stateless;
import justdoit.common.jpa.User;

@Stateless
public class RegisterMailContent extends MailContent {

//<editor-fold defaultstate="collapsed" desc="Konstruktor">
    public RegisterMailContent(User user) {
        super(user, "Willkommen bei JustDoIt! | " + user.getUsername() + " | JustDoIt");
        this.setContent();
    }

    public RegisterMailContent() {

    }
//</editor-fold>
}
