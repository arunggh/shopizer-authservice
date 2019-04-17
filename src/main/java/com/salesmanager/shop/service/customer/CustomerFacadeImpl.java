/**
 *
 */
package com.salesmanager.shop.service.customer;

import com.salesmanager.shop.constants.Constants;
import com.salesmanager.shop.constants.EmailConstants;
import com.salesmanager.shop.dao.Email;
import com.salesmanager.shop.dao.customer.PersistableCustomer;
import com.salesmanager.shop.exception.ServiceException;
import com.salesmanager.shop.exception.ServiceRuntimeException;
import com.salesmanager.shop.exception.UserAlreadyExistException;
import com.salesmanager.shop.model.customer.Customer;
import com.salesmanager.shop.model.merchant.MerchantStore;
import com.salesmanager.shop.model.reference.language.Language;
import com.salesmanager.shop.model.user.Group;
import com.salesmanager.shop.model.user.GroupType;
import com.salesmanager.shop.service.country.CountryService;
import com.salesmanager.shop.service.email.EmailService;
import com.salesmanager.shop.service.group.GroupService;
import com.salesmanager.shop.service.image.ImageFilePath;
import com.salesmanager.shop.service.language.LanguageService;
import com.salesmanager.shop.service.zone.ZoneService;
import com.salesmanager.shop.utils.EmailUtils;
import com.salesmanager.shop.utils.LabelUtils;
import com.salesmanager.shop.utils.UserReset;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import javax.inject.Inject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Customer Facade work as an abstraction layer between Controller and Service layer. It work as an
 * entry point to service layer.
 * 
 * @author Umesh Awasthi
 * @version 2.2.1
 *
 */

@Service("customerFacade")
public class CustomerFacadeImpl implements CustomerFacade {

  private static final Logger LOG = LoggerFactory.getLogger(CustomerFacadeImpl.class);
  private final static int USERNAME_LENGTH = 6;

  private final static String RESET_PASSWORD_TPL = "email_template_password_reset_customer.ftl";

  public final static String ROLE_PREFIX = "ROLE_";// Spring Security 4


  @Inject
  private CustomerService customerService;

  @Inject
  private LanguageService languageService;

  @Inject
  private CustomerOptionValueService customerOptionValueService;

  @Inject
  private CustomerOptionService customerOptionService;

  @Inject
  private LabelUtils messages;


  @Inject
  private CountryService countryService;

  @Inject
  private GroupService groupService;

  @Inject
  private ZoneService zoneService;

  @SuppressWarnings("deprecation")
  @Inject
  private PasswordEncoder passwordEncoder;

  @Inject
  private EmailService emailService;


  @Inject
  private EmailUtils emailUtils;

  @Inject
  @Qualifier("img")
  private ImageFilePath imageUtils;

  @Override
  public Customer getCustomerByUserName(String userName, MerchantStore store) throws Exception {
    return customerService.getByNick(userName, store.getId());
  }
  
  @Override
  public PersistableCustomer registerCustomer(final PersistableCustomer customer,
      final MerchantStore merchantStore, Language language) throws Exception {
    LOG.info("Starting customer registration process..");

    if (this.userExist(customer.getUserName())) {
      throw new UserAlreadyExistException("User already exist");
    }

    Customer customerModel = getCustomerModel(customer, merchantStore, language);
    if (customerModel == null) {
      LOG.equals("Unable to create customer in system");
      // throw new CustomerRegistrationException( "Unable to register customer" );
      throw new Exception("Unable to register customer");
    }

    LOG.info("About to persist customer to database.");
    customerService.saveOrUpdate(customerModel);

    LOG.info("Returning customer data to controller..");
    // return customerEntityPoulator(customerModel,merchantStore);
    customer.setId(customerModel.getId());
    return customer;
  }

  @Override
  public Customer getCustomerModel(final PersistableCustomer customer,
      final MerchantStore merchantStore, Language language) throws Exception {

    LOG.info("Starting to populate customer model from customer data");
    Customer customerModel = null;
    CustomerPopulator populator = new CustomerPopulator();
    populator.setCountryService(countryService);
    populator.setCustomerOptionService(customerOptionService);
    populator.setCustomerOptionValueService(customerOptionValueService);
    populator.setLanguageService(languageService);
    populator.setLanguageService(languageService);
    populator.setZoneService(zoneService);


    customerModel = populator.populate(customer, merchantStore, language);
    // we are creating or resetting a customer
    if (StringUtils.isBlank(customerModel.getPassword())
        && !StringUtils.isBlank(customer.getClearPassword())) {
      customerModel.setPassword(customer.getClearPassword());
    }
    // set groups
    if (!StringUtils.isBlank(customerModel.getPassword())
        && !StringUtils.isBlank(customerModel.getNick())) {
      customerModel.setPassword(passwordEncoder.encode(customer.getClearPassword()));
      setCustomerModelDefaultProperties(customerModel, merchantStore);
    }


    return customerModel;

  }

  @Override
  public void setCustomerModelDefaultProperties(Customer customer, MerchantStore store)
      throws Exception {
    Validate.notNull(customer, "Customer object cannot be null");
    if (customer.getId() == null || customer.getId() == 0) {
      if (StringUtils.isBlank(customer.getNick())) {
        String userName = UserReset.generateRandomString(USERNAME_LENGTH);
        customer.setNick(userName);
      }
      if (StringUtils.isBlank(customer.getPassword())) {
        String password = UserReset.generateRandomString();
        String encodedPassword = passwordEncoder.encode(password);
        customer.setPassword(encodedPassword);
      }
    }

    if (CollectionUtils.isEmpty(customer.getGroups())) {
      List<Group> groups = getListOfGroups(GroupType.CUSTOMER);
      for (Group group : groups) {
        if (group.getGroupName().equals(Constants.GROUP_CUSTOMER)) {
          customer.getGroups().add(group);
        }
      }

    }

  }

  private boolean userExist(String userName) {
    return Optional.ofNullable(customerService.getByNick(userName))
        .isPresent();
  }

  private List<Group> getListOfGroups(GroupType groupType) {
    try{
      return groupService.listGroup(groupType);
    } catch (ServiceException e) {
      throw new ServiceRuntimeException(e);
    }

  }

  @Override
  public void resetPassword(Customer customer, MerchantStore store, Language language)
      throws Exception {


    String password = UserReset.generateRandomString();
    String encodedPassword = passwordEncoder.encode(password);

    customer.setPassword(encodedPassword);
    customerService.saveOrUpdate(customer);

    Locale locale = languageService.toLocale(language, store);

    // send email

    try {

      // creation of a user, send an email
      String[] storeEmail = {store.getStoreEmailAddress()};


      Map<String, String> templateTokens =
          emailUtils.createEmailObjectsMap(imageUtils.getContextPath(), store, messages, locale);
      templateTokens.put(EmailConstants.LABEL_HI, messages.getMessage("label.generic.hi", locale));
      templateTokens.put(EmailConstants.EMAIL_CUSTOMER_FIRSTNAME,
          customer.getBilling().getFirstName());
      templateTokens.put(EmailConstants.EMAIL_CUSTOMER_LASTNAME,
          customer.getBilling().getLastName());
      templateTokens.put(EmailConstants.EMAIL_RESET_PASSWORD_TXT,
          messages.getMessage("email.customer.resetpassword.text", locale));
      templateTokens.put(EmailConstants.EMAIL_CONTACT_OWNER,
          messages.getMessage("email.contactowner", storeEmail, locale));
      templateTokens.put(EmailConstants.EMAIL_PASSWORD_LABEL,
          messages.getMessage("label.generic.password", locale));
      templateTokens.put(EmailConstants.EMAIL_CUSTOMER_PASSWORD, password);


      Email email = new Email();
      email.setFrom(store.getStorename());
      email.setFromEmail(store.getStoreEmailAddress());
      email.setSubject(messages.getMessage("label.generic.changepassword", locale));
      email.setTo(customer.getEmailAddress());
      email.setTemplateName(RESET_PASSWORD_TPL);
      email.setTemplateTokens(templateTokens);



      emailService.sendHtmlEmail(store, email);

    } catch (Exception e) {
      LOG.error("Cannot send email to customer", e);
    }


  }

}
