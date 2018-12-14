package cn.AssassinG.ScsyERP.Fee.core.biz.impl;

import cn.AssassinG.ScsyERP.Fee.core.biz.OnTruckFormBiz;
import cn.AssassinG.ScsyERP.Fee.core.dao.OnTruckFormDao;
import cn.AssassinG.ScsyERP.Fee.facade.entity.OnTruckForm;
import cn.AssassinG.ScsyERP.Fee.facade.exceptions.OnTruckFormBizException;
import cn.AssassinG.ScsyERP.File.facade.entity.MyFile;
import cn.AssassinG.ScsyERP.File.facade.service.MyFileServiceFacade;
import cn.AssassinG.ScsyERP.common.core.biz.impl.FormBizImpl;
import cn.AssassinG.ScsyERP.common.core.dao.BaseDao;
import cn.AssassinG.ScsyERP.common.enums.AccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Component("OnTruckFormBiz")
public class OnTruckFormBizImpl extends FormBizImpl<OnTruckForm> implements OnTruckFormBiz {
    @Autowired
    private OnTruckFormDao onTruckFormDao;
    protected BaseDao<OnTruckForm> getDao() {
        return this.onTruckFormDao;
    }

    /**
     * @param entityId
     * @param paramMap 随车清单字段(tallyMan,qualityTestMan,signMan,signTime,accountStatus)
     */
    @Transactional
    public void updateByMap(Long entityId, Map<String, String> paramMap) {
        if(entityId == null){
            throw new OnTruckFormBizException(OnTruckFormBizException.ONTRUCKFORMBIZ_PARAMS_ILLEGAL, "随车清单基本信息主键不能为空");
        }
        OnTruckForm inStorageForm = this.getById(entityId);
        if(inStorageForm == null || inStorageForm.getIfDeleted()){
            throw new OnTruckFormBizException(OnTruckFormBizException.ONTRUCKFORMBIZ_NOSUIT_RESULT, "没有符合条件的随车清单基本信息，entityId: %d", entityId);
        }
        try{
            Long tallyMan = paramMap.get("tallyMan") == null ? null : Long.valueOf(paramMap.get("tallyMan"));
            Long qualityTestMan = paramMap.get("qualityTestMan") == null ? null : Long.valueOf(paramMap.get("qualityTestMan"));
            String signMan = paramMap.get("signMan");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd  HH:mm:ss");
            Date signTime = paramMap.get("signTime") == null ? null : sdf.parse(paramMap.get("signTime"));
            AccountStatus accountStatus = AccountStatus.getEnum(paramMap.get("accountStatus"));
            boolean flag = false;
            if(tallyMan != null) {
                inStorageForm.setTallyMan(tallyMan);
                flag = true;
            }
            if(qualityTestMan != null) {
                inStorageForm.setQualityTestMan(qualityTestMan);
                flag = true;
            }
            if(signMan != null && !signMan.isEmpty()) {
                inStorageForm.setSignMan(signMan);
                flag = true;
            }
            if(signTime != null) {
                inStorageForm.setSignTime(signTime);
                flag = true;
            }
            if(accountStatus != null) {
                inStorageForm.setAccountStatus(accountStatus);
                flag = true;
            }
            if (flag) {
                this.update(inStorageForm);
            }
        }catch(NumberFormatException | ParseException e){
            throw new OnTruckFormBizException(OnTruckFormBizException.ONTRUCKFORMBIZ_NOSUIT_RESULT, "参数格式错误："+e.getMessage());
        }
    }

    @Autowired
    private MyFileServiceFacade myFileServiceFacade;

    @Transactional
    public void addPicture(Long entityId, Long pictureId) {
        if(entityId == null){
            throw new OnTruckFormBizException(OnTruckFormBizException.ONTRUCKFORMBIZ_PARAMS_ILLEGAL, "随车清单基本信息主键不能为空");
        }
        if(pictureId == null){
            throw new OnTruckFormBizException(OnTruckFormBizException.ONTRUCKFORMBIZ_PARAMS_ILLEGAL, "图片基本信息主键不能为空");
        }
        OnTruckForm onTruckForm = this.getById(entityId);
        if(onTruckForm == null || onTruckForm.getIfDeleted()){
            throw new OnTruckFormBizException(OnTruckFormBizException.ONTRUCKFORMBIZ_NOSUIT_RESULT, "没有符合条件的随车清单基本信息，entityId: %d", entityId);
        }
        MyFile myFile = myFileServiceFacade.getById(pictureId);
        if(myFile == null || myFile.getIfDeleted()){
            throw new OnTruckFormBizException(OnTruckFormBizException.ONTRUCKFORMBIZ_NOSUIT_RESULT, "没有符合条件的图片基本信息，entityId: %d", entityId);
        }
        onTruckForm.getPictures().add(myFile.getId());
        this.update(onTruckForm);
    }

    @Transactional
    public void removePicture(Long entityId, Long pictureId) {
        if(entityId == null){
            throw new OnTruckFormBizException(OnTruckFormBizException.ONTRUCKFORMBIZ_PARAMS_ILLEGAL, "随车清单基本信息主键不能为空");
        }
        if(pictureId == null){
            throw new OnTruckFormBizException(OnTruckFormBizException.ONTRUCKFORMBIZ_PARAMS_ILLEGAL, "图片基本信息主键不能为空");
        }
        OnTruckForm onTruckForm = this.getById(entityId);
        if(onTruckForm == null || onTruckForm.getIfDeleted()){
            throw new OnTruckFormBizException(OnTruckFormBizException.ONTRUCKFORMBIZ_NOSUIT_RESULT, "没有符合条件的随车清单基本信息，entityId: %d", entityId);
        }
        MyFile myFile = myFileServiceFacade.getById(pictureId);
        if(myFile == null || myFile.getIfDeleted()){
            throw new OnTruckFormBizException(OnTruckFormBizException.ONTRUCKFORMBIZ_NOSUIT_RESULT, "没有符合条件的图片基本信息，entityId: %d", entityId);
        }
        onTruckForm.getPictures().remove(myFile.getId());
        this.update(onTruckForm);
    }

    //todo statistic
    @Override
    @Transactional
    public void complete(Long entityId) {
        if(entityId == null){
            throw new OnTruckFormBizException(OnTruckFormBizException.ONTRUCKFORMBIZ_PARAMS_ILLEGAL, "随车清单基本信息主键不能为空");
        }
        OnTruckForm onTruckForm = this.getById(entityId);
        if(onTruckForm == null || onTruckForm.getIfDeleted()){
            throw new OnTruckFormBizException(OnTruckFormBizException.ONTRUCKFORMBIZ_NOSUIT_RESULT, "没有符合条件的随车清单基本信息，entityId: %d", entityId);
        }
        if(onTruckForm.getSignTime() == null){
            throw new OnTruckFormBizException(OnTruckFormBizException.ONTRUCKFORMBIZ_CANNOTOPERATE, "随车清单还没有被签收，不能完成，entityId: %d", entityId);
        }
        this.update(onTruckForm);
    }
}
