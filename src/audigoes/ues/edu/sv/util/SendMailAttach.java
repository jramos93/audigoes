package audigoes.ues.edu.sv.util;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.StringUtils;

public class SendMailAttach {

	private String from;
	private String cc;
	private String to;
	private String subject;
	private String text;
	private String attach;
	private String logo;
	private Address[] toList;
	private Address[] toCc;

	private static final int STATUS_SEND = 1;
	private static final int STATUS_ADDRESS_ERROR = 2;
	private static final int STATUS_SEND_ERROR = 3;

	public SendMailAttach(String from, String cc, String to, String subject, String text, String attach, String logo) {
		super();
		this.from = from;
		this.cc = cc;
		this.to = to;
		this.subject = subject;
		this.text = text;
		this.attach = attach;
		this.logo = logo;
	}

	public SendMailAttach(String from, Address[] toList, String cc, String subject, String text, String attach,
			String logo) {
		super();
		this.from = from;
		this.cc = cc;
		this.subject = subject;
		this.text = text;
		this.attach = attach;
		this.logo = logo;
		this.toList = toList;
	}

	public SendMailAttach(String from, String to, Address[] toCc, String subject, String text, String attach,
			String logo) {
		super();
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.text = text;
		this.attach = attach;
		this.logo = logo;
		this.toCc = toCc;
	}

	public SendMailAttach(String from, Address[] toList, Address[] toCc, String subject, String text, String attach,
			String logo) {
		super();
		this.from = from;
		this.toList = toList;
		this.toCc = toCc;
		this.subject = subject;
		this.text = text;
		this.attach = attach;
		this.logo = logo;
	}

	public int send() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		final String username = "audigoes.sv@gmail.com";
		final String password = "audigoes20";

		Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		MimeMessage simpleMessage = new MimeMessage(mailSession);

		InternetAddress fromAddress = null;
		InternetAddress toAddress = null;
		InternetAddress toCc = null;

		try {
			fromAddress = new InternetAddress(from);
			toAddress = new InternetAddress(to);
			toCc = new InternetAddress(cc);
		} catch (AddressException e) {
			e.printStackTrace();
			return STATUS_ADDRESS_ERROR;
		}

		try {
			MimeMultipart multiPart = new MimeMultipart("related");
			MimeBodyPart messageBodyPart = null;

			// Adding html tag for logo
			if ((StringUtils.isNotEmpty(logo)) && (StringUtils.isNotEmpty(logo))) {
				// Adding body content and logo file
				try {
					File f = new File(logo);
					if (f.exists()) {
						messageBodyPart = new MimeBodyPart();
						text += "<table><tr><td align=\"center\"><strong>Sistema Informatico para Apoyar el Proceso de\r\n"
								+ "Auditorìa Interna en las Instituciones Gubernamentales de El Salvador</strong></td></tr>"
								+ "<tr><td><img src=\"cid:image@logo-azul\" /></td></tr></table>";
						messageBodyPart.setContent(text, "text/html");
						multiPart.addBodyPart(messageBodyPart);

						messageBodyPart = new MimeBodyPart();
						DataSource source = new FileDataSource(logo);
						messageBodyPart.setDataHandler(new DataHandler(source));
						messageBodyPart.setFileName(f.getName());
						messageBodyPart.setHeader("Content-ID", "<image@logo-azul>");
						multiPart.addBodyPart(messageBodyPart);
					}
				} catch (Exception e) {
					// logo cannot added
					e.printStackTrace();
				}
			} else {
				// Adding just the body content
				messageBodyPart = new MimeBodyPart();
				multiPart.addBodyPart(messageBodyPart);
			}

			simpleMessage.setFrom(fromAddress);
			simpleMessage.setRecipient(RecipientType.TO, toAddress);
			simpleMessage.setRecipient(RecipientType.CC, toCc);
			simpleMessage.setSubject(subject);

			if (attach != null) {
				// Adding Attachment
				messageBodyPart = new MimeBodyPart();
				File f = new File(attach);
				if (f.exists()) {
					DataSource source = new FileDataSource(attach);
					messageBodyPart.setDataHandler(new DataHandler(source));
					messageBodyPart.setFileName(f.getName());
					multiPart.addBodyPart(messageBodyPart);
				}
			}

			simpleMessage.setContent(multiPart);
			Transport.send(simpleMessage);
			return STATUS_SEND;
		} catch (MessagingException e) {
			e.printStackTrace();
			return STATUS_SEND_ERROR;
		}
	}

	public int sendManyTo() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		final String username = "audigoes.sv@gmail.com";
		final String password = "audigoes20";

		Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		MimeMessage simpleMessage = new MimeMessage(mailSession);

		InternetAddress fromAddress = null;
		InternetAddress toCc = null;
		Address[] toAddress = null;

		try {
			fromAddress = new InternetAddress(from);
			toAddress = toList;
			toCc = new InternetAddress(cc);
		} catch (AddressException e) {
			e.printStackTrace();
			return STATUS_ADDRESS_ERROR;
		}

		try {
			MimeMultipart multiPart = new MimeMultipart("related");
			MimeBodyPart messageBodyPart = null;

			// Adding html tag for logo
			if ((StringUtils.isNotEmpty(logo)) && (StringUtils.isNotEmpty(logo))) {
				// Adding body content and logo file
				try {
					File f = new File(logo);
					if (f.exists()) {
						messageBodyPart = new MimeBodyPart();
						text += "<table><tr><td align=\"center\"><strong>Sistema Informatico para Apoyar el Proceso de\r\n"
								+ "Auditorìa Interna en las Instituciones Gubernamentales de El Salvador</strong></td></tr>"
								+ "<tr><td><img src=\"cid:image@logo-azul\" /></td></tr></table>";
						messageBodyPart.setContent(text, "text/html");
						multiPart.addBodyPart(messageBodyPart);

						messageBodyPart = new MimeBodyPart();
						DataSource source = new FileDataSource(logo);
						messageBodyPart.setDataHandler(new DataHandler(source));
						messageBodyPart.setFileName(f.getName());
						messageBodyPart.setHeader("Content-ID", "<image@logo-azul>");
						multiPart.addBodyPart(messageBodyPart);
					}
				} catch (Exception e) {
					// logo cannot added
					e.printStackTrace();
				}
			} else {
				// Adding just the body content
				messageBodyPart = new MimeBodyPart();
				multiPart.addBodyPart(messageBodyPart);
			}

			simpleMessage.setFrom(fromAddress);
			simpleMessage.setRecipients(RecipientType.TO, toAddress);
			simpleMessage.setRecipient(RecipientType.CC, toCc);
			simpleMessage.setSubject(subject);
			//System.out.println("SendMailAttach.sendManyTo() a");
			if (attach != null) {
				// Adding Attachment
				messageBodyPart = new MimeBodyPart();
				File f = new File(attach);
				System.out.println("attach "+attach);
				System.out.println("SendMailAttach.sendManyTo()");
				if (f.exists()) {
					//System.out.println("SendMailAttach.sendManyTo() exists");
					DataSource source = new FileDataSource(attach);
					messageBodyPart.setDataHandler(new DataHandler(source));
					messageBodyPart.setFileName(f.getName());
					multiPart.addBodyPart(messageBodyPart);
				}
			}
			//System.out.println("SendMailAttach.sendManyTo() b");

			simpleMessage.setContent(multiPart);
			Transport.send(simpleMessage);
			return STATUS_SEND;
		} catch (MessagingException e) {
			e.printStackTrace();
			return STATUS_SEND_ERROR;
		}
	}

	public int sendManyCc() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		final String username = "audigoes.sv@gmail.com";
		final String password = "audigoes20";

		Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		MimeMessage simpleMessage = new MimeMessage(mailSession);

		InternetAddress fromAddress = null;
		InternetAddress toAddress = null;
		Address[] toListCc = null;

		try {
			fromAddress = new InternetAddress(from);
			toAddress = new InternetAddress(to);
			toListCc = toCc;
		} catch (AddressException e) {
			e.printStackTrace();
			return STATUS_ADDRESS_ERROR;
		}

		try {
			MimeMultipart multiPart = new MimeMultipart("related");
			MimeBodyPart messageBodyPart = null;

			// Adding html tag for logo
			if ((StringUtils.isNotEmpty(logo)) && (StringUtils.isNotEmpty(logo))) {
				// Adding body content and logo file
				try {
					File f = new File(logo);
					if (f.exists()) {
						messageBodyPart = new MimeBodyPart();
						text += "<table><tr><td align=\"center\"><strong>Sistema Informatico para Apoyar el Proceso de\r\n"
								+ "Auditorìa Interna en las Instituciones Gubernamentales de El Salvador</strong></td></tr>"
								+ "<tr><td><img src=\"cid:image@logo-azul\" /></td></tr></table>";
						messageBodyPart.setContent(text, "text/html");
						multiPart.addBodyPart(messageBodyPart);

						messageBodyPart = new MimeBodyPart();
						DataSource source = new FileDataSource(logo);
						messageBodyPart.setDataHandler(new DataHandler(source));
						messageBodyPart.setFileName(f.getName());
						messageBodyPart.setHeader("Content-ID", "<image@logo-azul>");
						multiPart.addBodyPart(messageBodyPart);
					}
				} catch (Exception e) {
					// logo cannot added
					e.printStackTrace();
				}
			} else {
				// Adding just the body content
				messageBodyPart = new MimeBodyPart();
				multiPart.addBodyPart(messageBodyPart);
			}

			simpleMessage.setFrom(fromAddress);
			simpleMessage.setRecipient(RecipientType.TO, toAddress);
			simpleMessage.setRecipients(RecipientType.CC, toListCc);
			simpleMessage.setSubject(subject);

			if (attach != null) {
				// Adding Attachment
				messageBodyPart = new MimeBodyPart();
				File f = new File(attach);
				if (f.exists()) {
					DataSource source = new FileDataSource(attach);
					messageBodyPart.setDataHandler(new DataHandler(source));
					messageBodyPart.setFileName(f.getName());
					multiPart.addBodyPart(messageBodyPart);
				}
			}

			simpleMessage.setContent(multiPart);
			Transport.send(simpleMessage);
			return STATUS_SEND;
		} catch (MessagingException e) {
			e.printStackTrace();
			return STATUS_SEND_ERROR;
		}
	}
	
}
