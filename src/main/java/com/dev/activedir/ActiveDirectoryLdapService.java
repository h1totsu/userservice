package com.dev.activedir;

import com.dev.activedir.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

@Component
public class ActiveDirectoryLdapService {
    //Attribute names
    public static final String ATTRIBUTE_S_AM_ACCOUNT_NAME = "sAMAccountName";
    public static final String ATTRIBUTE_OBJECT_CLASS = "objectClass";
    public static final String ATTRIBUTE_TOP = "top";
    public static final String ATTRIBUTE_PERSON = "person";
    public static final String ATTRIBUTE_ORG_PERSON = "organizationalPerson";
    public static final String ATTRIBUTE_USER = "user";
    public static final String ATTRIBUTE_USER_NAME = "userPrincipalName";
    public static final String ATTRIBUTE_GIVEN_NAME = "givenName";
    public static final String ATTRIBUTE_CN = "cn";
    public static final String ATTRIBUTE_SN = "sn";
    public static final String ATTRIBUTE_UID = "uid";
    public static final String ATTRIBUTE_USER_PASSWORD = "userpassword";
    public static final String ATTRIBUTE_USER_ACCOUNT_CONTROL = "userAccountControl";

    @Value("${ad.domainRoot}")
    private String domainRoot;

    @Value("${ad.baseDN}")
    private String domainName;

    private final String ADMIN_NAME = "CN=Администратор,CN=Users,DC=corp,DC=h1totsu";

    @Value("${ad.password}")
    private String adminPassword;

    @Value("${ad.url}")
    private String domainUrl;

    @Autowired
    ActiveDirectoryConnectionUtils adConnectionUtils;

    /**
     * @param userInfo
     * @return - user created or not
     * @throws NamingException
     */
    public boolean addUserToDomain(UserInfo userInfo)
            throws NamingException {

        // Create a container set of attributes
        Attributes container = new BasicAttributes();

        // Create the object class to add
        Attribute objClasses = new BasicAttribute(ATTRIBUTE_OBJECT_CLASS);
        objClasses.add(ATTRIBUTE_TOP);
        objClasses.add(ATTRIBUTE_PERSON);
        objClasses.add(ATTRIBUTE_ORG_PERSON);
        objClasses.add(ATTRIBUTE_USER);

        // Assign the username, first name, and last name
        String cnValue = userInfo.getUserName();
        Attribute cn = new BasicAttribute(ATTRIBUTE_CN, userInfo.getUserName());
        Attribute sAMAccountName = new BasicAttribute(ATTRIBUTE_S_AM_ACCOUNT_NAME, userInfo.getUserName());
        Attribute principalName = new BasicAttribute(ATTRIBUTE_USER_NAME, userInfo.getUserName()
                + "@" + domainName);
        Attribute givenName = new BasicAttribute(ATTRIBUTE_GIVEN_NAME, userInfo.getFirstName());
        Attribute sn = new BasicAttribute(ATTRIBUTE_SN, userInfo.getLastName());
        Attribute uid = new BasicAttribute(ATTRIBUTE_UID, userInfo.getUserName());

        // Add password
        Attribute userPassword = new BasicAttribute(ATTRIBUTE_USER_PASSWORD, userInfo.getPassword());

        // Add these to the container
        container.put(objClasses);
        container.put(sAMAccountName);
        container.put(principalName);
        container.put(cn);
        container.put(sn);
        container.put(givenName);
        container.put(uid);
        container.put(userPassword);

        // Create the entry
        try {
            LdapContext ctx = adConnectionUtils.createContext(domainUrl, ADMIN_NAME, adminPassword);
            ctx.createSubcontext(getUserDN(cnValue, userInfo.getOrganizationUnit()), container);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String getUserDN(String aUsername, String aOU) {
        return "cn=" + aUsername + ",ou=" + aOU + "," + domainRoot;
    }
}