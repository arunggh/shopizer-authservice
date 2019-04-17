package com.salesmanager.shop.service.email;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesmanager.shop.constants.CoreConstants;
import com.salesmanager.shop.dao.Email;
import com.salesmanager.shop.exception.ServiceException;
import com.salesmanager.shop.model.merchant.MerchantConfiguration;
import com.salesmanager.shop.model.merchant.MerchantStore;
import com.salesmanager.shop.service.merchant.MerchantConfigurationService;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

	@Inject
	private MerchantConfigurationService merchantConfigurationService;
	
	@Inject
	private HtmlEmailSender sender;
	
	@Override
	public void sendHtmlEmail(MerchantStore store, Email email) throws ServiceException, Exception {

		EmailConfig emailConfig = getEmailConfiguration(store);
		
		sender.setEmailConfig(emailConfig);
		sender.send(email);
	}

	@Override
	public EmailConfig getEmailConfiguration(MerchantStore store) throws ServiceException {
		
		MerchantConfiguration configuration = merchantConfigurationService.getMerchantConfiguration(CoreConstants.EMAIL_CONFIG, store);
		EmailConfig emailConfig = null;
		if(configuration!=null) {
			String value = configuration.getValue();
			
			ObjectMapper mapper = new ObjectMapper();
			try {
				emailConfig = mapper.readValue(value, EmailConfig.class);
			} catch(Exception e) {
				throw new ServiceException("Cannot parse json string " + value);
			}
		}
		return emailConfig;
	}
	
	
}
