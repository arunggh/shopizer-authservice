/**
 * 
 */
package com.salesmanager.shop.service.populator;

import com.salesmanager.shop.exception.ConversionException;
import com.salesmanager.shop.model.merchant.MerchantStore;
import com.salesmanager.shop.model.reference.language.Language;

/**
 * @author Umesh A
 *
 */
public interface DataPopulator<Source,Target>
{


    public Target populate(Source source, Target target, MerchantStore store, Language language) throws ConversionException;
    public Target populate(Source source, MerchantStore store, Language language) throws ConversionException;

   
}
