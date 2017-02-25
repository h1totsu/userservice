package com.dev.activedir.domain;

import org.springframework.beans.factory.annotation.Value;

public class ContextSettings {
    @Value("${ad.url}")
    private String url;
    @Value("${ad.baseDN}")
    private String baseDN;
    @Value("${ad.domainRoot")
    private String domainRoot;

    public ContextSettings() {}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBaseDN() {
        return baseDN;
    }

    public void setBaseDN(String baseDN) {
        this.baseDN = baseDN;
    }

    public String getDomainRoot() {
        return domainRoot;
    }

    public void setDomainRoot(String domainRoot) {
        this.domainRoot = domainRoot;
    }
}
