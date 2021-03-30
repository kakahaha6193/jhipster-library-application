package com.khmt.myapp.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.khmt.myapp.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.khmt.myapp.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.khmt.myapp.domain.User.class.getName());
            createCache(cm, com.khmt.myapp.domain.Authority.class.getName());
            createCache(cm, com.khmt.myapp.domain.User.class.getName() + ".authorities");
            createCache(cm, com.khmt.myapp.domain.Sach.class.getName());
            createCache(cm, com.khmt.myapp.domain.Sach.class.getName() + ".nhapsaches");
            createCache(cm, com.khmt.myapp.domain.Cuonsach.class.getName());
            createCache(cm, com.khmt.myapp.domain.Giasach.class.getName());
            createCache(cm, com.khmt.myapp.domain.Nhaxuatban.class.getName());
            createCache(cm, com.khmt.myapp.domain.Theloai.class.getName());
            createCache(cm, com.khmt.myapp.domain.Phongdungsach.class.getName());
            createCache(cm, com.khmt.myapp.domain.Nhapsach.class.getName());
            createCache(cm, com.khmt.myapp.domain.Nhapsach.class.getName() + ".saches");
            createCache(cm, com.khmt.myapp.domain.Phongdocsach.class.getName());
            createCache(cm, com.khmt.myapp.domain.Muonsach.class.getName());
            createCache(cm, com.khmt.myapp.domain.Muonsach.class.getName() + ".cuonsaches");
            createCache(cm, com.khmt.myapp.domain.Thuephong.class.getName());
            createCache(cm, com.khmt.myapp.domain.Thuthu.class.getName());
            createCache(cm, com.khmt.myapp.domain.Thuthu.class.getName() + ".nhapsaches");
            createCache(cm, com.khmt.myapp.domain.Thuthu.class.getName() + ".muonsaches");
            createCache(cm, com.khmt.myapp.domain.Thuthu.class.getName() + ".thuephongs");
            createCache(cm, com.khmt.myapp.domain.Docgia.class.getName());
            createCache(cm, com.khmt.myapp.domain.Docgia.class.getName() + ".thuephongs");
            createCache(cm, com.khmt.myapp.domain.Docgia.class.getName() + ".muonsaches");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
