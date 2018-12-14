package cn.AssassinG.ScsyERP.Fee.core.dao.MyBatisHandler;

import cn.AssassinG.ScsyERP.Fee.facade.enums.OilCardType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OilCardTypeHandler extends BaseTypeHandler<OilCardType> {

//    private Class<AdminDeptType> type;
//    private AdminDeptType[] enums;
//
//    public AdminDeptTypeHandler(Class<AdminDeptType> type) {
//        if(type == null)
//            throw new IllegalArgumentException("type参数不能为空");
//        this.type = type;
//        this.enums = type.getEnumConstants();
//        if(this.enums == null){
//            throw new IllegalArgumentException(type.getSimpleName() + "不是一个枚举类型");
//        }
//    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, OilCardType oilCardType, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, oilCardType.getName());
    }

    @Override
    public OilCardType getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String str = resultSet.getString(s);
        if(resultSet.wasNull())
            return null;
        else{
            return OilCardType.getEnum(str);
        }
    }

    @Override
    public OilCardType getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String str = resultSet.getString(i);
        if(resultSet.wasNull())
            return null;
        else{
            return OilCardType.getEnum(str);
        }
    }

    @Override
    public OilCardType getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String str = callableStatement.getString(i);
        if(callableStatement.wasNull())
            return null;
        else{
            return OilCardType.getEnum(str);
        }
    }
}
