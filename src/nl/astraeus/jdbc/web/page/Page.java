package nl.astraeus.jdbc.web.page;

import nl.astraeus.jdbc.web.PageMapping;
import nl.astraeus.template.ReflectHelper;
import nl.astraeus.template.cache.CachedFormatters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * User: rnentjes
 * Date: 3/28/12
 * Time: 3:20 PM
 */
public abstract class Page implements Serializable {
    private final static Logger logger = LoggerFactory.getLogger(Page.class);

    private static final long serialVersionUID = 6480721322469803964L;

    private String uri;
    private String redirect = null;
    private String offSiteRedirect = null;
    private int responseCode = 200;

    protected Map<String, Object> model;

    protected Page() {
        this.model = new HashMap<String, Object>();
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getRedirect() {
        return redirect;
    }

    public String getOffSiteRedirect() {
        return offSiteRedirect;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void get() {
        sendResponse(501);
    }

    public void post() {
        sendResponse(501);
    }
    public void put() {
        sendResponse(501);
    }

    public void delete() {
        sendResponse(501);
    }

    public void ajax() {
        sendResponse(501);
    }

    public boolean writeHeaderAndFooter() {
        return true;
    }

    protected void authorize(boolean check) {
        if (!check) {
            throw new AuthorizationException();
        }
    }

    /*
    protected void authorizeAdmin() {
        authorize(getUser() != null && getUser().getSuperuser());
    }

    protected User getUser() {
        User user = null;

        Long uid = getSession("user-id", Long.class);

        if (uid != null) {
            user = UserDao.get().find(uid);
        }

        return user;
    }*/

    protected void sendResponse(int nr) {
        responseCode = nr;
    }

    protected void redirect(PageMapping uri, String ... parameters) {
        redirect = "/"+uri.getUri();

        for (String parameter : parameters) {
            redirect += "/"+parameter;
        }
    }

    protected void redirectOffSite(String url) {
        offSiteRedirect = url;
    }

    protected void set(String name, Object value) {
        model.put(name, value);
    }

    protected <T> boolean checkSession(String name, Class<T> type) {
        return getSession().getAttribute(name) != null && type.isAssignableFrom(getSession().getAttribute(name).getClass());
    }

    protected HttpSession getSession() {
        return Request.get().getSession(true);
    }

    protected <T> T getSession(String name, Class<T> type) {
        return (T)getSession().getAttribute(name);
    }

    protected void setSession(String name, Object value) {
        getSession().setAttribute(name, value);
    }

    protected void clearSession(String name) {
        getSession().removeAttribute(name);
    }

    protected String getParameter(String parameterName) {
        return Request.get().getParameter(parameterName);
    }

    public void render(OutputStream out) throws IOException {
        addMessage(Warnings.Message.Type.ERROR, "ERROR!", "'String render();' method not implemented in class: "+this.getClass().getName());
    }

    protected boolean empty(String str) {
        return str == null || str.length() == 0;
    }

    private String getLabel(Map<String, String> labels, String key) {
        String result = labels.get(key);

        if (result == null) {
            result = "-"+key+"-";
        }

        return result;
    }

    protected void checkParametersExist(Map<String, String> labels, String ... parameters) {
        List<String> missing = new LinkedList<String>();

        for (String parameter : parameters) {
            if (empty(getParameter(parameter))) {
                missing.add(parameter);
            }
        }

        if (!missing.isEmpty()) {
            if (missing.size() == 1) {
                addMessage(Warnings.Message.Type.ERROR, "Missende gegevens!", "Veld "+getLabel(labels, missing.get(0))+" is niet ingevuld.");
            } else if (missing.size() == 2) {
                addMessage(Warnings.Message.Type.ERROR, "Missende gegevens!", "Velden "+getLabel(labels, missing.get(0))+" en "+getLabel(labels, missing.get(1))+" zijn niet ingevuld.");
            } else {
                String part1 = getLabel(labels, missing.get(0));

                for (int index = 1; index < missing.size() - 1; index++) {
                    part1 += ", " + getLabel(labels, missing.get(index));
                }

                addMessage(Warnings.Message.Type.ERROR, "Missende gegevens!", "Velden "+part1+" en "+getLabel(labels, missing.get(missing.size()-1))+" zijn niet ingevuld.");
            }
        }
    }

    protected void addMessage(Warnings.Message.Type type, String header, String message) {
        Warnings.getWarnings(Request.get()).addMessage(type, header, message);
    }

    protected boolean hasWarnings() {
        return Warnings.getWarnings(Request.get()).hasWarnings();
    }

    protected void storeString(Map<String, String> labels, String parameterName, Object targetObject, String targetField) {
        Field field  = ReflectHelper.get().getField(targetObject, targetField);

        Class<?> type = field.getType();

        assert type.equals(String.class);

        try {
            field.set(targetObject, getParameter(parameterName));
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    protected void storeNumber(Map<String, String> labels, String parameterName, Object targetObject, String targetField) {
        Field field  = ReflectHelper.get().getField(targetObject, targetField);

        Class<?> type = field.getType();
        Object value = null;
        String stringValue = getParameter(parameterName);

        try {
            if (type.equals(Integer.class) || type.equals(int.class)) {
                if (stringValue == null) {
                    value = 0;
                } else {
                    value = Integer.parseInt(stringValue);
                }
            } else if (type.equals(Long.class) || type.equals(long.class)) {
                if (stringValue == null) {
                    value = 0L;
                } else {
                    value = Long.parseLong(stringValue);
                }
            } else if (type.equals(Short.class) || type.equals(short.class)) {
                if (stringValue == null) {
                    value = 0;
                } else {
                    value = Short.parseShort(stringValue);
                }
            } else if (type.equals(Byte.class) || type.equals(byte.class)) {
                if (stringValue == null) {
                    value = 0;
                } else {
                    value = Byte.parseByte(stringValue);
                }
            } else {
                addMessage(Warnings.Message.Type.ERROR, "Ongeldig getal!", "Veld "+getLabel(labels, parameterName)+" is geen geldig getal.");
            }

            field.set(targetObject, value);
        } catch (IllegalAccessException e) {
            logger.warn(e.getMessage(),e);
            addMessage(Warnings.Message.Type.ERROR, e.getMessage(), e.getMessage());
        } catch (NumberFormatException e) {
            addMessage(Warnings.Message.Type.ERROR, "Ongeldig getal!", "Veld "+getLabel(labels, parameterName)+" is geen geldig getal.");
        }

    }

    protected void storeAmount(Map<String, String> labels, String parameterName, Object targetObject, String targetField) {
        Field field  = ReflectHelper.get().getField(targetObject, targetField);

        Class<?> type = field.getType();
        Object value = null;
        String stringValue = getParameter(parameterName);

        try {
            Number numberValue = 0;

            if (stringValue != null && !stringValue.isEmpty()) {
                numberValue = CachedFormatters.getAmountFormat("###,###,###,##0.##").parse(stringValue);
            }

            if (type.equals(BigDecimal.class)) {
                value = new BigDecimal(numberValue.doubleValue());
            } else if (type.equals(Long.class) || type.equals(long.class)) {
                value = (long)(numberValue.doubleValue() * 100.0);
            } else if (type.equals(Double.class) || type.equals(double.class)) {
                value = numberValue.doubleValue();
            } else {
                addMessage(Warnings.Message.Type.ERROR, "Ongeldig getal!", "Veld "+getLabel(labels, parameterName)+" is geen geldig getal.");
            }

            field.set(targetObject, value);
        } catch (IllegalAccessException e) {
            logger.warn(e.getMessage(),e);
            addMessage(Warnings.Message.Type.ERROR, e.getMessage(), e.getMessage());
        } catch (ParseException e) {
            addMessage(Warnings.Message.Type.ERROR, "Ongeldig getal!", "Veld "+getLabel(labels, parameterName)+" is geen geldig getal.");
        } catch (NumberFormatException e) {
            addMessage(Warnings.Message.Type.ERROR, "Ongeldig getal!", "Veld "+getLabel(labels, parameterName)+" is geen geldig getal.");
        }
    }

    protected void storeDate(Map <String, String> labels, String parameterName, Object targetObject, String targetField) {
        Field field  = ReflectHelper.get().getField(targetObject, targetField);

        Class<?> type = field.getType();
        Object value = null;
        String stringValue = getParameter(parameterName);

        try {
            if (stringValue == null) {
                value = null;
            } else if (type.equals(String.class)) {
                value = stringValue;
            } else if (type.equals(Long.class) || type.equals(long.class)) {
                try {
                    if (stringValue.length() ==  "dd-MM-yyyy".length()) {
                        value = CachedFormatters.getDateFormat("dd-MM-yyyy").parse(stringValue);
                        value = ((Date)value).getTime();
                    } else if (stringValue.length() ==  "dd-MM-yy".length()) {
                        value = CachedFormatters.getDateFormat("dd-MM-yy").parse(stringValue);
                        value = ((Date)value).getTime();
                    } else {
                        addMessage(Warnings.Message.Type.ERROR, "Ongeldige datum!", "Veld "+getLabel(labels, parameterName)+" is geen geldige datum.");
                    }
                } catch (ParseException e) {
                    addMessage(Warnings.Message.Type.ERROR, "Ongeldige datum!", "Veld "+getLabel(labels, parameterName)+" is geen geldige datum.");
                }

            } else if (type.equals(Date.class)) {
                try {
                    if (stringValue.length() ==  "dd-MM-yyyy".length()) {
                        value = CachedFormatters.getDateFormat("dd-MM-yyyy").parse(stringValue);
                    } else if (stringValue.length() ==  "dd-MM-yy".length()) {
                        value = CachedFormatters.getDateFormat("dd-MM-yy").parse(stringValue);
                    } else {
                        addMessage(Warnings.Message.Type.ERROR, "Ongeldige datum!", "Veld "+getLabel(labels, parameterName)+" is geen geldige datum.");
                    }
                } catch (ParseException e) {
                    addMessage(Warnings.Message.Type.ERROR, "Ongeldige datum!", "Veld "+getLabel(labels, parameterName)+" is geen geldige datum.");
                }
            }

            field.set(targetObject, value);
        } catch (IllegalAccessException e) {
            logger.warn(e.getMessage(),e);
            addMessage(Warnings.Message.Type.ERROR, e.getMessage(), e.getMessage());
        } catch (NumberFormatException e) {
            addMessage(Warnings.Message.Type.ERROR, "Ongeldig getal!", "Veld "+getLabel(labels, parameterName)+" is geen geldig getal.");
        }
    }

}
