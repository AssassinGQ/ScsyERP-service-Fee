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
import cn.AssassinG.ScsyERP.common.utils.ValidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Component("OnTruckFormBiz")
public class OnTruckFormBizImpl extends FormBizImpl<OnTruckForm> implements OnTruckFormBiz {
    @Autowired
    private OnTruckFormDao onTruckFormDao;
    protected BaseDao<OnTruckForm> getDao() {
        return this.onTruckFormDao;
    }

    @Override
    public Long create(OnTruckForm onTruckForm) {
        if(onTruckForm.getFormNumber() == null){
            onTruckForm.setFormNumber(String.valueOf(System.currentTimeMillis()));
        }
        if(onTruckForm.getAccountStatus() == null) {
            onTruckForm.setAccountStatus(cn.AssassinG.ScsyERP.common.enums.AccountStatus.WRZ);
        }
        onTruckForm.setIfCompleted(false);
        ValidUtils.ValidationWithExp(onTruckForm);
//        Map<String, Object> queryMap = new HashMap<String, Object>();
//        queryMap.put("IfDeleted", false);
//        queryMap.put("Warehouse", outStorageForm.getWarehouse());
//        queryMap.put("IfCompleted", false);
//        List<InStorageForm> inStorageForms = inStorageFormDao.listBy(queryMap);
//        if(inStorageForms.size() > 1){
//            throw new InStorageFormBizException(InStorageFormBizException.INSTORAGEFORMBIZ_UNKNOWN_ERROR, "仓库（主键：%d）存在多个活跃的入库单", outStorageForm.getWarehouse());
//        }else if(inStorageForms.size() == 1){
//            throw new InStorageFormBizException(InStorageFormBizException.INSTORAGEFORMBIZ_UNKNOWN_ERROR, "当前仓库已经存在一个活跃的入库单，仓库主键：%d", inStorageForms.get(0).getWarehouse());
//        }else{
        Long id = getDao().insert(onTruckForm);
        if(!onTruckForm.getFormNumber().startsWith("otf")){
            onTruckForm.setFormNumber("otf" + id);
            getDao().update(onTruckForm);
        }
        return id;
//        }
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
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd  HH:mm:ss");
            Date signTime = paramMap.get("signTime") == null ? null : new Date(Long.parseLong(paramMap.get("signTime")));
            String accountStatusstr = paramMap.get("accountStatus");
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
            if(accountStatusstr != null) {
                if(accountStatusstr.equals("未入账"))
                    inStorageForm.setAccountStatus(AccountStatus.WRZ);
                else{
                    inStorageForm.setAccountStatus(AccountStatus.YRZ);
                }
                flag = true;
            }
            if (flag) {
                this.update(inStorageForm);
            }
        }catch(NumberFormatException e){
            throw new OnTruckFormBizException(OnTruckFormBizException.ONTRUCKFORMBIZ_NOSUIT_RESULT, "参数格式错误：%s, %s" ,e.getMessage(), e.getLocalizedMessage());
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
    //数据库事务管理的坑
    @Override
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
        onTruckForm.setIfCompleted(true);
        this.update(onTruckForm);
    }
}
