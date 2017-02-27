package com.dev.activedir;

import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;

/**
 * Provides Ldap context for connection
 */
@Component
public class ActiveDirectoryConnectionUtils {

    public static final String LDAP_CONNECT_TIMEOUT = "com.sun.jndi.ldap.connect.timeout";
    public static final String LDAP_ATTRIBUTES_BINARY = "java.naming.ldap.attributes.binary";
    public static final String LDAP_CONNECT_POOL = "com.sun.jndi.ldap.connect.pool";

    /**
     * Creates Ldap context to server
     * @param url - Active Directory DNS server url
     * @param user - Admin username
     * @param pass - Admin password
     * @return - Ldap context
     */
    public LdapContext createContext(String url, String user, String pass) {
        Hashtable<String, String> env = getProperties(url, user, pass);
        LdapContext ctx;
        try {
            ctx = new InitialLdapContext(env, null);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return ctx;
    }

    /**
     * Creates context properties
     * @param serverUrl - Active Directory DNS server url
     * @param user - Admin username
     * @param password - Admin username
     * @return
     */
    private Hashtable<String, String> getProperties(String serverUrl, String user, String password) {
        //create an initial directory context
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.REFERRAL, "ignore");
        env.put(LDAP_CONNECT_POOL, "false");
        //environment property to specify how long to wait for a pooled connection.
        // If you omit this property, the application will wait indefinitely.
        env.put(LDAP_CONNECT_TIMEOUT, "300000");
        env.put(Context.PROVIDER_URL, serverUrl);
        env.put(Context.SECURITY_PRINCIPAL, user);
        env.put(Context.SECURITY_CREDENTIALS, password);
        env.put(LDAP_ATTRIBUTES_BINARY, "tokenGroups objectSid objectGUID");
        return env;
    }
}