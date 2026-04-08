package com.ubisam.exam.api.accounts;


import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import com.ubisam.exam.domain.Account;
import com.ubisam.exam.domain.auditing.AuditedAuditor;
import com.ubisam.exam.domain.exception.ResponseStatusExceptions;

import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;
import io.u2ware.common.data.rest.core.annotation.HandleAfterRead;
import io.u2ware.common.data.rest.core.annotation.HandleBeforeRead;

@Component
@RepositoryEventHandler
public class AccountHandler {
    
    protected Log logger = LogFactory.getLog(getClass());


    @HandleBeforeCreate
    public void HandleBeforeCreate(Account e) throws Exception{
        logger.info("@HandleBeforeCreate : "+e);
        throw ResponseStatusExceptions.UNAUTHORIZED;
    }

    @HandleBeforeSave
    public void HandleBeforeSave(Account e)throws Exception{
        logger.info("@HandleBeforeSave : "+e);
        if(AuditedAuditor.hasNotPermission("ROLE_ADMIN")) {
            throw ResponseStatusExceptions.UNAUTHORIZED;
        }
    }

    @HandleBeforeDelete
    public void HandleBeforeDelete(Account e)throws Exception{
        logger.info("@HandleBeforeDelete : "+e);
        if(AuditedAuditor.hasNotPermission("ROLE_ADMIN")) {
            throw ResponseStatusExceptions.UNAUTHORIZED;
        }
    }

    @HandleAfterRead
    public void HandleAfterRead(Account e, Serializable s)throws Exception{
        logger.info("@HandleAfterRead : "+e);

        if(AuditedAuditor.hasNotPermission("ROLE_ADMIN")) {
            throw ResponseStatusExceptions.UNAUTHORIZED;
        }
    }

    @HandleBeforeRead
    public void HandleBeforeRead(Account e, Specification<Account> s)throws Exception{
        logger.info("@HandleBeforeRead : "+e);
        if(AuditedAuditor.hasNotPermission("ROLE_ADMIN")) {
            throw ResponseStatusExceptions.UNAUTHORIZED;
        }

        JpaSpecificationBuilder.of(Account.class)
            .where()
                .and().containing("username", e.getUsername())
        .build(s);

    }

}
