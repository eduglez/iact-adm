package rss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;


public class WorkersUtil {

    private static WorkersUtil instance=null;

    public synchronized static WorkersUtil getInstance() throws Exception{
        if(instance==null)
            instance=new WorkersUtil();

        return instance;
    }



    private WorkersUtil() throws Exception{
//        Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
        Class.forName("com.mysql.jdbc.Driver").newInstance();
    }

    public Connection getConnection() throws SQLException
    {
        String databaseName="";
        String user="";
        String password="";
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/"+databaseName+"?user="+user+"&password="+password+);

    }

    public synchronized void postMail(String recipients[], String subject, String message, String from, String username, String password) throws MessagingException {
        boolean debug = false;

        //Set the host smtp address
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.ugr.es");
        props.put("mail.smtp.auth", "true");

        Authenticator auth = new SMTPAuthenticator(username, password);
        Session session = Session.getDefaultInstance(props, auth);
        session.setDebug(debug);

        // create a message
        Message msg = new MimeMessage(session);

        // set the from and to address
        InternetAddress addressFrom = new InternetAddress(from);
        msg.setFrom(addressFrom);

        InternetAddress[] addressTo = new InternetAddress[recipients.length];
        for (int i = 0; i < recipients.length; i++) {
            addressTo[i] = new InternetAddress(recipients[i]);
        }
        msg.setRecipients(Message.RecipientType.TO, addressTo);


        // Optional : You can also set your custom headers in the Email if you Want
        msg.addHeader("MyHeaderName", "myHeaderValue");

        // Setting the Subject and Content Type
        msg.setSubject(subject);
        msg.setContent(message, "text/plain");
        Transport.send(msg);
    }
}

class SMTPAuthenticator extends javax.mail.Authenticator {

    private String username;
    private String password;

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }

    public SMTPAuthenticator(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }
}
