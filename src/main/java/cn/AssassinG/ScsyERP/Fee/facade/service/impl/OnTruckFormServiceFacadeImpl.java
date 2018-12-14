package cn.AssassinG.ScsyERP.Fee.facade.service.impl;

import cn.AssassinG.ScsyERP.Fee.core.biz.OnTruckFormBiz;
import cn.AssassinG.ScsyERP.Fee.facade.entity.OnTruckForm;
import cn.AssassinG.ScsyERP.Fee.facade.service.OnTruckFormServiceFacade;
import cn.AssassinG.ScsyERP.common.core.biz.FormBiz;
import cn.AssassinG.ScsyERP.common.core.service.impl.FormServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("OnTruckFormServiceFacade")
public class OnTruckFormServiceFacadeImpl extends FormServiceImpl<OnTruckForm> implements OnTruckFormServiceFacade {
    @Autowired
    private OnTruckFormBiz onTruckFormBiz;

    protected FormBiz<OnTruckForm> getFormBiz() {
        return this.onTruckFormBiz;
    }


    @Override
    public void addPicture(Long entityId, Long pictureId) {
        onTruckFormBiz.addPicture(entityId, pictureId);
    }

    @Override
    public void removePicture(Long entityId, Long pictureId) {
        onTruckFormBiz.removePicture(entityId, pictureId);
    }
}
