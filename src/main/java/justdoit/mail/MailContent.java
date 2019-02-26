package justdoit.mail;

import justdoit.user.User;

abstract class MailContent {

    TemplateParser templateParser;

    protected String username;
    protected String recipientAdress;
    protected String subject;
    protected String content;

//<editor-fold defaultstate="collapsed" desc="Konstruktor">
    protected MailContent(User user, String subject) {
        this.username = user.getUsername();
        this.recipientAdress = user.getEmail();
        this.subject = subject;

        templateParser = new TemplateParser();
        this.content = templateParser.parseTemplate(this);
    }

    protected MailContent() {

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Getter&Setter">
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRecipientAdress() {
        return recipientAdress;
    }

    public void setRecipientAdress(String recipientAdress) {
        this.recipientAdress = recipientAdress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
//</editor-fold>
}
