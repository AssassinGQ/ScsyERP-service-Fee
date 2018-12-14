package cn.AssassinG.ScsyERP.Fee.facade.service.impl;

import cn.AssassinG.ScsyERP.Fee.core.biz.TransportContractBiz;
import cn.AssassinG.ScsyERP.Fee.facade.entity.TransportContract;
import cn.AssassinG.ScsyERP.Fee.facade.service.TransportContractServiceFacade;
import cn.AssassinG.ScsyERP.common.core.biz.FormBiz;
import cn.AssassinG.ScsyERP.common.core.service.impl.FormServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("TransportContractServiceFacade")
public class TransportContractServiceFacadeImpl extends FormServiceImpl<TransportContract> implements TransportContractServiceFacade {
    @Autowired
    private TransportContractBiz transportContractBiz;

    protected FormBiz<TransportContract> getFormBiz() {
        return this.transportContractBiz;
    }
}
