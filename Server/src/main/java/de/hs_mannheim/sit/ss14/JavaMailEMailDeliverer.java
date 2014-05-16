package de.hs_mannheim.sit.ss14;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * An implementation of {@link EMailDeliverer}, that uses Javax Mail.
 *
 * @author Jochen Schwander
 */
public class JavaMailEMailDeliverer implements EMailDeliverer {

	private Session session;

	/**
	 * Konstruktor.
	 */
	public JavaMailEMailDeliverer() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("sit.ss2014@gmail.com", "dasisteinschlechtespasswort");
			}
		});
	}

	@Override
	public void sendEMail(final User user) {
		try {
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress("sit.ss2014@gmail.com"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));

			message.setSubject("Authentification for Someapp");

			message.setText(makeEMailContent(user));
			// message.setContent(makeEMailContent(user), "text/html");

			Transport.send(message);
		} catch (MessagingException mex) {
			mex.printStackTrace();
			System.err.println("One time password email delivery to \"" + user.getEmail() + "\" failed");
		}
	}

	private String makeEMailContent(final User user) {
		StringBuilder message = new StringBuilder();

		message.append("Hello ").append(user.getUserName()).append(",\n");
		message.append("please go to https://localhost/somepath?user=").append(user.getUserName());
		message.append(" and enter ").append(user.getOneTimePassword()).append(" to log in.");

		return message.toString();
	}

}
