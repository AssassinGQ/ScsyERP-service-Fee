package cn.AssassinG.ScsyERP.Fee.core.biz;

import cn.AssassinG.ScsyERP.Fee.facade.entity.OnTruckForm;
import cn.AssassinG.ScsyERP.common.core.biz.FormBiz;

public interface OnTruckFormBiz extends FormBiz<OnTruckForm> {
    void addPicture(Long entityId, Long pictureId);
    void removePicture(Long entityId, Long pictureId);
}
