package com.salesmanager.shop.service.email;

import com.salesmanager.shop.dao.Email;

public interface HtmlEmailSender {
	
	public void send(final Email email) throws Exception;

	public void setEmailConfig(EmailConfig emailConfig);

}
