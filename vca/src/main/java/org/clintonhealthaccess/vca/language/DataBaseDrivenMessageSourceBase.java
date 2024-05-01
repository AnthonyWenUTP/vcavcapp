package org.clintonhealthaccess.vca.language;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.context.support.AbstractMessageSource;

/**
 * Elemento base para el manejo de mensajes en Spring desde la base de datos
 * 
 * @author William Aviles
 **/
public abstract class DataBaseDrivenMessageSourceBase extends AbstractMessageSource{

    private final Map<String, Map<String, String>> properties = new HashMap<String, Map<String, String>>();

    public DataBaseDrivenMessageSourceBase() {
        
    }
    
    @PostConstruct
    public void init() {
    	reload();
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        String msg = getText(code, locale);
        MessageFormat result = createMessageFormat(msg, locale);
        return result;
    }

    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        return getText(code, locale);
    }

    private String getText(String code, Locale locale) {
    	Map<String, String> localized = properties.get(code);
        String textForCurrentLanguage = null;
        if (localized != null) {
            textForCurrentLanguage = localized.get(locale.getLanguage());
            if (textForCurrentLanguage == null) {
                textForCurrentLanguage = localized.get(Locale.ENGLISH.getLanguage());
            }
        }
        if (textForCurrentLanguage==null) {
            //Check parent message
            try {
                textForCurrentLanguage = getParentMessageSource().getMessage(code, null, locale);
            } catch (Exception e) {
                
            }
        }
        return textForCurrentLanguage != null ? textForCurrentLanguage : code;
    }

    public void reload() {
        properties.clear();
        properties.putAll(loadTexts());
    }
    
    abstract Map<String, Map<String, String>> loadTexts();


}
