package cn.AssassinG.ScsyERP.Fee.core.biz.impl;

import cn.AssassinG.ScsyERP.Fee.core.biz.TransportContractBiz;
import cn.AssassinG.ScsyERP.Fee.core.dao.TransportContractDao;
import cn.AssassinG.ScsyERP.Fee.facade.entity.TransportContract;
import cn.AssassinG.ScsyERP.Fee.facade.enums.OilCardType;
import cn.AssassinG.ScsyERP.Fee.facade.exceptions.OnTruckFormBizException;
import cn.AssassinG.ScsyERP.Fee.facade.exceptions.TransportContractBizException;
import cn.AssassinG.ScsyERP.OutStorage.facade.entity.OutStorageForm;
import cn.AssassinG.ScsyERP.OutStorage.facade.service.OutStorageFormServiceFacade;
import cn.AssassinG.ScsyERP.common.core.biz.impl.FormBizImpl;
import cn.AssassinG.ScsyERP.common.core.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Map;

@Component("TransportContractBiz")
public class TransportContractBizImpl extends FormBizImpl<TransportContract> implements TransportContractBiz {
    @Autowired
    private TransportContractDao transportContractDao;
    protected BaseDao<TransportContract> getDao() {
        return this.transportContractDao;
    }

    /**
     * @param entityId
     * @param paramMap 运输合同字段(supplier,productInsurance,realWeight,prePay,oilCardType,oilCardNumber,oilCardMoney,preRepairFee,fareByWeight,totalFareByWeight,fareByTruck)
     */
    @Transactional
    public void updateByMap(Long entityId, Map<String, String> paramMap) {
        if(entityId == null){
            throw new TransportContractBizException(TransportContractBizException.TRANSPORTCONTRACTBIZ_PARAMS_ILLEGAL, "入库单基本信息主键不能为空");
        }
        TransportContract inStorageForm = this.getById(entityId);
        if(inStorageForm == null || inStorageForm.getIfDeleted()){
            throw new TransportContractBizException(TransportContractBizException.TRANSPORTCONTRACTBIZ_NOSUIT_RESULT, "没有符合条件的入库单基本信息，entityId: %d", entityId);
        }
        try{
            String supplier = paramMap.get("supplier");
            Double productInsurance = paramMap.get("productInsurance") == null ? null : Double.valueOf(paramMap.get("productInsurance"));
            Double realWeight = paramMap.get("realWeight") == null ? null : Double.valueOf(paramMap.get("realWeight"));
            Double prePay = paramMap.get("prePay") == null ? null : Double.valueOf(paramMap.get("prePay"));
            OilCardType oilCardType = OilCardType.getEnum(paramMap.get("oilCardType"));
            String oilCardNumber = paramMap.get("oilCardNumber");
            Double oilCardMoney = paramMap.get("oilCardMoney") == null ? null : Double.valueOf(paramMap.get("oilCardMoney"));
            Double preRepairFee = paramMap.get("preRepairFee") == null ? null : Double.valueOf(paramMap.get("preRepairFee"));
            Double fareByWeight = paramMap.get("fareByWeight") == null ? null : Double.valueOf(paramMap.get("fareByWeight"));
            Double totalFareByWeight = paramMap.get("totalFareByWeight") == null ? null : Double.valueOf(paramMap.get("totalFareByWeight"));
            Double fareByTruck = paramMap.get("fareByTruck") == null ? null : Double.valueOf(paramMap.get("fareByTruck"));
            boolean flag = false;
            if(supplier != null && !supplier.isEmpty()) {
                inStorageForm.setSupplier(supplier);
                flag = true;
            }
            if(productInsurance != null) {
                inStorageForm.setProductInsurance(productInsurance);
                flag = true;
            }
            if(realWeight != null) {
                inStorageForm.setRealWeight(realWeight);
                flag = true;
            }
            if(prePay != null) {
                inStorageForm.setPrePay(prePay);
                flag = true;
            }
            if(oilCardType != null) {
                inStorageForm.setOilCardType(oilCardType);
                flag = true;
            }
            if(oilCardNumber != null && !oilCardNumber.isEmpty()) {
                inStorageForm.setOilCardNumber(oilCardNumber);
                flag = true;
            }
            if(oilCardMoney != null) {
                inStorageForm.setOilCardMoney(oilCardMoney);
                flag = true;
            }
            if(preRepairFee != null) {
                inStorageForm.setPreRepairFee(preRepairFee);
                flag = true;
            }
            if(fareByWeight != null) {
                inStorageForm.setFareByWeight(fareByWeight);
                flag = true;
            }
            if(totalFareByWeight != null) {
                inStorageForm.setPreRepairFee(totalFareByWeight);
                flag = true;
            }
            if(fareByTruck != null) {
                inStorageForm.setPreRepairFee(fareByTruck);
                flag = true;
            }
            if (flag) {
                this.update(inStorageForm);
            }
        }catch(NumberFormatException e){
            throw new TransportContractBizException(TransportContractBizException.TRANSPORTCONTRACTBIZ_NOSUIT_RESULT, "参数格式错误："+e.getMessage());
        }
    }

    @Autowired
    private OutStorageFormServiceFacade outStorageFormServiceFacade;
    //todo statistic
    @Override
    public void complete(Long entityId) {
        if(entityId == null){
            throw new TransportContractBizException(TransportContractBizException.TRANSPORTCONTRACTBIZ_PARAMS_ILLEGAL, "运输合同基本信息主键不能为空");
        }
        TransportContract transportContract = this.getById(entityId);
        if(transportContract == null || transportContract.getIfDeleted()){
            throw new TransportContractBizException(TransportContractBizException.TRANSPORTCONTRACTBIZ_NOSUIT_RESULT, "没有符合条件的运输合同基本信息，entityId: %d", entityId);
        }
        //实际结算重量
        if(transportContract.getRealWeight() == null){
            OutStorageForm outStorageForm = outStorageFormServiceFacade.getById(transportContract.getOutStorageForm());
            if(outStorageForm == null || !outStorageForm.getIfCompleted() || outStorageForm.getTotalWeight() == null)
                transportContract.setRealWeight(0.0);
            else
                transportContract.setRealWeight(outStorageForm.getTotalWeight());
        }
        //按吨结算合计金额
        //按车结算金额
        this.update(transportContract);
    }
}
