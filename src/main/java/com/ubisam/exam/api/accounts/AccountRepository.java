package com.ubisam.exam.api.accounts;


import com.ubisam.exam.domain.Account;

import io.u2ware.common.data.jpa.repository.RestfulJpaRepository;

public interface AccountRepository extends RestfulJpaRepository<Account,String>{
    

}
