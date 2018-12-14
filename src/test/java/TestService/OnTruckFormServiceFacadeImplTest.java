package TestService;

import cn.AssassinG.ScsyERP.Fee.core.biz.OnTruckFormBiz;
import cn.AssassinG.ScsyERP.Fee.core.dao.OnTruckFormDao;
import cn.AssassinG.ScsyERP.Fee.facade.entity.OnTruckForm;
import cn.AssassinG.ScsyERP.Fee.facade.service.OnTruckFormServiceFacade;
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
public class OnTruckFormServiceFacadeImplTest {
    private static Logger logger = Logger.getLogger(OnTruckFormServiceFacadeImplTest.class);
    @Autowired
    private OnTruckFormServiceFacade onTruckFormService;
    @Autowired
    private OnTruckFormBiz onTruckFormBiz;
    @Autowired
    private OnTruckFormDao onTruckFormDao;

    private Long onTruckFormId;

    @Before
    public void setUp() throws Exception {
        OnTruckForm onTruckForm = new OnTruckForm();
        onTruckForm.setCorporation(1L);
        onTruckForm.setFormNumber(StringUtils.getRandomStr(10));
        onTruckForm.setSignMan(StringUtils.getRandomStr(6));
        onTruckFormId = onTruckFormBiz.create(onTruckForm);
    }

    @After
    public void tearDown() throws Exception {
        onTruckFormDao.delete(onTruckFormId);
    }

    @Test
    public void create() {
        OnTruckForm onTruckForm = new OnTruckForm();
        onTruckForm.setCorporation(1L);
        onTruckForm.setFormNumber(StringUtils.getRandomStr(10));
        onTruckForm.setSignMan(StringUtils.getRandomStr(8));
        Long id = onTruckFormService.create(onTruckForm);
        if(id == null){
            throw new RuntimeException("Create nothing");
        }else {
            logger.info("Created : " + onTruckFormDao.getById(id));
        }
    }

    @Test
    public void update() {
        OnTruckForm onTruckForm = onTruckFormDao.getById(onTruckFormId);
        String new_name = StringUtils.getRandomStr(6);
        logger.info("Before Update: "+onTruckForm);
        onTruckForm.setSignMan(new_name);
        onTruckFormService.update(onTruckForm);
        OnTruckForm onTruckForm_check = onTruckFormDao.getById(onTruckFormId);
        if(!onTruckForm_check.getSignMan().equals(new_name)){
            throw new RuntimeException("Update failed");
        }else{
            logger.info("Updated succeed");
        }
    }

    @Test
    public void deleteById() {
        onTruckFormService.deleteById(onTruckFormId);
        OnTruckForm onTruckForm_check = onTruckFormDao.getById(onTruckFormId);
        if(!onTruckForm_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void delete() {
        OnTruckForm onTruckForm_test = onTruckFormDao.getById(onTruckFormId);
        onTruckFormService.delete(onTruckForm_test);
        OnTruckForm onTruckForm_check = onTruckFormDao.getById(onTruckFormId);
        if(!onTruckForm_check.getIfDeleted()){
            throw new RuntimeException("Delete failed");
        }else {
            logger.info("Delete succeed");
        }
    }

    @Test
    public void getById() {
        OnTruckForm onTruckForm = onTruckFormService.getById(onTruckFormId);
        if(onTruckForm == null || onTruckForm.getIfDeleted()){
            throw new RuntimeException("getById null");
        }else{
            if(onTruckForm.getId().longValue() != onTruckFormId.longValue()){
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
        paramMap.put("Id", onTruckFormId);
        OnTruckForm onTruckForm = onTruckFormService.getBy(paramMap);
        if(onTruckForm.getId() != onTruckFormId){
            throw new RuntimeException("getBy failed");
        }else{
            logger.info("getBy succeed");
        }
    }

    @Test
    public void listBy() {
        OnTruckForm onTruckForm = onTruckFormDao.getById(onTruckFormId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        paramMap.put("Name", onTruckForm.getSignMan());
        List<OnTruckForm> onTruckForms = onTruckFormService.listBy(paramMap);
        for (int i = 0; i < onTruckForms.size(); i++)
            logger.info("Item" + i + ":" + onTruckForms.get(i));
    }

    @Test
    public void listPage() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("IfDeleted", false);
        PageParam pageParam = new PageParam(2, 2);
        PageBean<OnTruckForm> pageBean = onTruckFormService.listPage(pageParam, paramMap);
        logger.info(pageBean);
        List<OnTruckForm> onTruckForms = pageBean.getRecordList();
        for (int i = 0; i < onTruckForms.size(); i++)
            logger.info("Item" + i + ":" + onTruckForms.get(i));
    }

//    @Test
//    public void createWithUser() {
//        OnTruckForm onTruckForm = new OnTruckForm();
//        onTruckForm.setCorporation(1L);
//        onTruckForm.setDept(OnTruckFormDeptType.FinancialOnTruckForm);
//        User user = new User();
//        user.setUserName(StringUtils.getRandomStr(8));
//        user.setPassWord("d123456");
//        user.setPhone("188" + StringUtils.getRandomStr(8, StringUtils.StrType.NUMBER));
//        user.setCorporation(1L);
//        onTruckFormService.createWithUser(onTruckForm, user);
//        if(user.getUserType() != UserType.OnTruckForm || user.getUserInfo().longValue() != onTruckForm.getId().longValue()){
//            throw new RuntimeException("createWithUser failed");
//        }else {
//            logger.info("createWithUser succeed : OnTruckFormID:" + onTruckForm.getId() + ", userID:" + user.getId());
//        }
//    }

    @Test
    public void updateByMap() {
        String new_name = StringUtils.getRandomStr(6);
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("signMan", new_name);
        onTruckFormService.updateByMap(onTruckFormId, paramMap);
        OnTruckForm onTruckForm_check = onTruckFormDao.getById(onTruckFormId);
        if(!onTruckForm_check.getSignMan().equals(new_name)){
            throw new RuntimeException("updateByMap failed");
        }else{
            logger.info("updateByMap succeed");
        }
    }

//    @Test
//    public void deleteByUserId() {
//        onTruckFormService.deleteByUserId(userId);
//        User user_check = userDao.getById(userId);
//        if(user_check == null || !user_check.getIfDeleted() || user_check.getUserType().getValue().intValue() != UserType.OnTruckForm.getValue().intValue()){
//            throw new RuntimeException("deleteByUserId failed");
//        }
//        OnTruckForm onTruckForm_check = onTruckFormDao.getById(user_check.getUserInfo());
//        if(onTruckForm_check == null || !onTruckForm_check.getIfDeleted()){
//            throw new RuntimeException("deleteByUserId failed");
//        }
//        logger.info("deleteByUserId succeed");
//    }
}