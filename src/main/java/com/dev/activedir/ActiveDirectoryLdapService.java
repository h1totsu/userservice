package com.dev.activedir;

import com.dev.activedir.domain.ContextSettings;
import com.dev.activedir.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.naming.ldap.LdapContext;

@Component
public class ActiveDirectoryLdapService {
    //Attribute names
    private static final String AD_ATTR_NAME_TOKEN_GROUPS = "tokenGroups";
    private static final String AD_ATTR_NAME_OBJECT_CLASS = "objectClass";
    private static final String AD_ATTR_NAME_OBJECT_CATEGORY = "objectCategory";
    private static final String AD_ATTR_NAME_MEMBER = "member";
    private static final String AD_ATTR_NAME_MEMBER_OF = "memberOf";
    private static final String AD_ATTR_NAME_DESCRIPTION = "description";
    private static final String AD_ATTR_NAME_OBJECT_GUID = "objectGUID";
    private static final String AD_ATTR_NAME_OBJECT_SID = "objectSid";
    private static final String AD_ATTR_NAME_DISTINGUISHED_NAME = "distinguishedName";
    private static final String AD_ATTR_NAME_CN = "cn";
    private static final String AD_ATTR_NAME_USER_PRINCIPAL_NAME = "userPrincipalName";
    private static final String AD_ATTR_NAME_USER_EMAIL = "mail";
    private static final String AD_ATTR_NAME_GROUP_TYPE = "groupType";
    private static final String AD_ATTR_NAME_SAM_ACCOUNT_TYPE = "sAMAccountType";
    private static final String AD_ATTR_NAME_USER_ACCOUNT_CONTROL = "userAccountControl";

    @Value("${ad.domainRoot")
    private String domainRoot;

    /**
     * @param userInfo
     * @param domainName
     * @param ou         - organization unit
     * @param ctx
     * @return - user created or not
     * @throws NamingException
     */
    public boolean addUserToDomain(UserInfo userInfo, String domainName, String ou, LdapContext ctx)
            throws NamingException {

        // Create a container set of attributes
        Attributes container = new BasicAttributes();

        // Create the objectclass to add
        Attribute objClasses = new BasicAttribute("objectClass");
        objClasses.add("top");
        objClasses.add("person");
        objClasses.add("organizationalPerson");
        objClasses.add("user");

        // Assign the username, first name, and last name
        String cnValue = new StringBuffer(userInfo.getFirstName()).append(" ")
                .append(userInfo.getLastName()).toString();
        Attribute cn = new BasicAttribute("cn", cnValue);
        Attribute sAMAccountName = new BasicAttribute("sAMAccountName", userInfo.getUserName());
        Attribute principalName = new BasicAttribute("userPrincipalName", userInfo.getUserName()
                + "@" + domainName);
        Attribute givenName = new BasicAttribute("givenName", userInfo.getFirstName());
        Attribute sn = new BasicAttribute("sn", userInfo.getLastName());
        Attribute uid = new BasicAttribute("uid", userInfo.getUserName());

        // Add password
        Attribute userPassword = new BasicAttribute("userpassword", userInfo.getPassword());

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
            ctx.createSubcontext(getUserDN(cnValue, ou), container);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String getUserDN(String aUsername, String aOU) {
        return "cn=" + aUsername + ",ou=" + aOU + "," + domainRoot;
    }
}