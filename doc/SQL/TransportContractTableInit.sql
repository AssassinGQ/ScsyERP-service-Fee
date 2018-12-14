drop table if exists t_transport_contract;

create table t_transport_contract (
  id            BIGINT(20)  NOT NULL AUTO_INCREMENT     COMMENT 'ID',
  corporation   BIGINT(20)  NOT NULL                    COMMENT '所属承运方ID',
  #   create_time DATETIME   NOT NULL DEFAULT now()    COMMENT '创建时间',
  #   update_time DATETIME   NOT NULL DEFAULT now()    COMMENT '最后修改时间',
  create_time   DATETIME    NOT NULL DEFAULT now()     COMMENT '创建时间',
  update_time   DATETIME    NOT NULL DEFAULT now()     COMMENT '最后修改时间',
  delete_time   DATETIME                                 COMMENT '删除时间',
  is_deleted    BOOLEAN     NOT NULL DEFAULT false     COMMENT '数据是否已经删除',
  contract_number       VARCHAR(30) NOT NULL           COMMENT '运输合同单号',
  ontruck_form          BIGINT(20)                       COMMENT '随车单',
  project               BIGINT(20)                       COMMENT '工程项目',
  out_storage_form      BIGINT(20)                       COMMENT '出库单',
  truck                 BIGINT(20)                       COMMENT '运输车辆',
  supplier              VARCHAR(15)                     COMMENT '合供方',
  product_insurance     DOUBLE(5,2)                     COMMENT '货物保价',
  real_weight           DOUBLE(5,2)                     COMMENT '实际结算重量',
  fare_by_weight        DOUBLE(5,2)                     COMMENT '按吨结算费用',
  total_fare_by_weight  DOUBLE(5,2)                     COMMENT '按吨结算合计金额',
  fare_by_truck         DOUBLE(5,2)                     COMMENT '按车结算费用',
  prepay                DOUBLE(5,2)                     COMMENT '装车后预付',
  oil_card_type         VARCHAR(5)                      COMMENT '油卡类型',
  oil_card_number       VARCHAR(20)                     COMMENT '油卡卡号',
  oil_card_money        DOUBLE(5,2)                     COMMENT '油卡金额',
  pre_repair_fee        DOUBLE(5,2)                     COMMENT '预付维修费用金额',
  if_completed          BOOLEAN  NOT NULL DEFAULT FALSE      COMMENT '当前运输合同是否已经完成',
  PRIMARY KEY (id)
);

alter table t_transport_contract comment '运输合同信息表';

## 权限的初始化数据
insert into t_transport_contract(corporation, contract_number, if_completed) values (-1, "aaaaaaa", false);
insert into t_transport_contract(corporation, contract_number, if_completed) values (-1, "bbbbbbb", false);