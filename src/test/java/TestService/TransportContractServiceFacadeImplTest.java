package TestService;

import cn.AssassinG.ScsyERP.Fee.core.biz.TransportContractBiz;
import cn.AssassinG.ScsyERP.Fee.core.dao.TransportContractDao;
import cn.AssassinG.ScsyERP.Fee.facade.entity.TransportContract;
import cn.AssassinG.ScsyERP.Fee.facade.service.TransportContractServiceFacade;
import cn.AssassinG.ScsyERP.common.page.PageBean;
import cn.AssassinG.ScsyERP.common.page.PageParam;
import cn.AssassinG.ScsyERP.common.utils.StringUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml"})
public class TransportContractServiceFacadeImplTest {
    private static Logger logger = Logger.getLogger(TransportContractServiceFacadeImplTest.class);
    @Autowired
    private TransportContractServiceFacade transportContractService;
    @Autowired
    private TransportContractBiz transportContractBiz;
    @Autowired
    private TransportContractDao transportContractDao;

    private Long transportContractId;

    @Before
    public void setUp() throws Exception {
        TransportContract transportContract = new TransportContract();
        transportContract.setCorporation(1L);
        transportContract.setContractNumber(StringUtils.getRandomStr(10));
        transportContract.setOilCardNumber(StringUtils.getRandomStr(6));
        transportContractId = transportContractBiz.create(transportContract);
    }

    @After
    public void tearDown() throws Exception {
        transportContractDao.delete(transportContractId);
    }

    @Test
    public void create() {
        TransportContract transportContract = new TransportContract();
        transportContract.setCorporation(1L);
        transportContract.setContractNumber(StringUtils.getRandomStr(10));
        transportContract.setOilCardNumber(StringUtils.getRandomStr(8));
        Long id = transportContractService.create(transportContract);
        if(id == null){
            throw new RuntimeException("Create nothing");
        }else {
            logger.info("Created : " + transportContractDao.getById(id));
        }
    }

    @Test
    public void update() {
        TransportContract transportContract = transportContractDao.getById(transportContractId);
        String new_name = StringUtils.getRandomStr(6);
        logger.info("Before Update: "+transportContract);
        transportContract.setOilCardNumber(new_name);
        transportContractService.update(transportContract);
        TransportContract transportContract_check = transportContractDao.getById(transportContractId);
        if(!transportContract_check.getOilCardNumber().equals(new_name)){
            throw new RuntimeException("Update failed");
        }else{
            logger.info("Updated succeed");
        }
    }

    @Test
    public void deleteById() {
        transportContractService.deleteById(transportContractId);
        TransportContract transportContract_check = transportContractDao.getById(transportContractId);
        if(!transportContract_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void delete() {
        TransportContract transportContract_test = transportContractDao.getById(transportContractId);
        transportContractService.delete(transportContract_test);
        TransportContract transportContract_check = transportContractDao.getById(transportContractId);
        if(!transportContract_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void getById() {
        TransportContract transportContract = transportContractService.getById(transportContractId);
        if(transportContract == null || transportContract.getIfDeleted()){
            throw new RuntimeException("getById null");
        }else{
            if(transportContract.getId().longValue() != transportContractId.longValue()){
                throw new RuntimeException("getById failed");
            }else{
                logger.info("getById succeed");
            }
        }
    }

    @Test
    public void getBy() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        paramMap.put("Id", transportContractId);
        TransportContract transportContract = transportContractService.getBy(paramMap);
        if(transportContract.getId() != transportContractId){
            throw new RuntimeException("getBy failed");
        }else{
            logger.info("getBy succeed");
        }
    }

    @Test
    public void listBy() {
        TransportContract transportContract = transportContractDao.getById(transportContractId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        paramMap.put("Name", transportContract.getOilCardNumber());
        List<TransportContract> transportContracts = transportContractService.listBy(paramMap);
        for (int i = 0; i < transportContracts.size(); i++)
            logger.info("Item" + i + ":" + transportContracts.get(i));
    }

    @Test
    public void listPage() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        PageParam pageParam = new PageParam(2, 2);
        PageBean<TransportContract> pageBean = transportContractService.listPage(pageParam, paramMap);
        logger.info(pageBean);
        List<TransportContract> transportContracts = pageBean.getRecordList();
        for (int i = 0; i < transportContracts.size(); i++)
            logger.info("Item" + i + ":" + transportContracts.get(i));
    }

//    @Test
//    public void createWithUser() {
//        TransportContract transportContract = new TransportContract();
//        transportContract.setCorporation(1L);
//        transportContract.setDept(TransportContractDeptType.FinancialTransportContract);
//        User user = new User();
//        user.setUserName(StringUtils.getRandomStr(8));
//        user.setPassWord("d123456");
//        user.setPhone("188" + StringUtils.getRandomStr(8, StringUtils.StrType.NUMBER));
//        user.setCorporation(1L);
//        transportContractService.createWithUser(transportContract, user);
//        if(user.getUserType() != UserType.TransportContract || user.getUserInfo().longValue() != transportContract.getId().longValue()){
//            throw new RuntimeException("createWithUser failed");
//        }else {
//            logger.info("createWithUser succeed : TransportContractID:" + transportContract.getId() + ", userID:" + user.getId());
//        }
//    }

    @Test
    public void updateByMap() {
        String new_name = StringUtils.getRandomStr(6);
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("oilCardNumber", new_name);
        transportContractService.updateByMap(transportContractId, paramMap);
        TransportContract transportContract_check = transportContractDao.getById(transportContractId);
        if(!transportContract_check.getOilCardNumber().equals(new_name)){
            throw new RuntimeException("updateByMap failed");
        }else{
            logger.info("updateByMap succeed");
        }
    }

//    @Test
//    public void deleteByUserId() {
//        transportContractService.deleteByUserId(userId);
//        User user_check = userDao.getById(userId);
//        if(user_check == null || !user_check.getIfDeleted() || user_check.getUserType().getValue().intValue() != UserType.TransportContract.getValue().intValue()){
//            throw new RuntimeException("deleteByUserId failed");
//        }
//        TransportContract transportContract_check = transportContractDao.getById(user_check.getUserInfo());
//        if(transportContract_check == null || !transportContract_check.getIfDeleted()){
//            throw new RuntimeException("deleteByUserId failed");
//        }
//        logger.info("deleteByUserId succeed");
//    }
}