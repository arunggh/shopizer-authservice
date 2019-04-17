/**
 * 
 */
package com.salesmanager.shop.service.populator;

import com.salesmanager.shop.exception.ConversionException;
import com.salesmanager.shop.model.merchant.MerchantStore;
import com.salesmanager.shop.model.reference.language.Language;
import java.util.Locale;

/**
 * @author Umesh A
 *
 */
public abstract class AbstractDataPopulator<Source,Target> implements DataPopulator<Source, Target>
{

 
   
    private Locale locale;

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	public Locale getLocale() {
		return locale;
	}
	


	@Override
	public Target populate(Source source, MerchantStore store, Language language) throws ConversionException{
	   return populate(source,createTarget(), store, language);
	}
	protected abstract Target createTarget();

   

}
