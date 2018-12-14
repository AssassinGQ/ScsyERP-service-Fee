package cn.AssassinG.ScsyERP.Fee.core.dao.impl;

import cn.AssassinG.ScsyERP.Fee.core.dao.TransportContractDao;
import cn.AssassinG.ScsyERP.Fee.facade.entity.TransportContract;
import cn.AssassinG.ScsyERP.common.core.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository(value="TransportContractDao")
public class TransportContractDaoImpl extends BaseDaoImpl<TransportContract> implements TransportContractDao {
}
