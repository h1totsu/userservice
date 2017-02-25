package com.dev.activedir;

import com.dev.activedir.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.naming.NamingException;
import javax.naming.ldap.LdapContext;

public class ActiveDirTester {
    @Autowired
    private ActiveDirectoryConnectionUtils adConnectionUtils;

    @Autowired
    private ActiveDirectoryLdapService adLdapService;

    public void testGetUserMailByDomainWithUser(String fqn)
    {
        LdapContext ctx = adConnectionUtils.createContext(url, username, password);

        UserInfo userInfo = new UserInfo();
        userInfo.setFirstName("Konstantin");
        userInfo.setLastName("Romanenko");
        userInfo.setUserName("ih1totsu");

        try {
            adLdapService.addUserToDomain(userInfo, "domainName", "ou", ctx);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
