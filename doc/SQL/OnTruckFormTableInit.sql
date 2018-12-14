drop table if exists t_ontruck_form;

create table t_ontruck_form (
  id            BIGINT(20)  NOT NULL AUTO_INCREMENT   COMMENT 'ID',
  corporation   BIGINT(20)  NOT NULL                    COMMENT '所属承运方ID',
  #   create_time DATETIME  NOT NULL DEFAULT now()  COMMENT '创建时间',
  #   update_time DATETIME  NOT NULL DEFAULT now()  COMMENT '最后修改时间',
  create_time   DATETIME    NOT NULL DEFAULT now()    COMMENT '创建时间',
  update_time   DATETIME    NOT NULL DEFAULT now()    COMMENT '最后修改时间',
  delete_time   DATETIME                                COMMENT '删除时间',
  is_deleted    BOOLEAN     NOT NULL DEFAULT false    COMMENT '数据是否已经删除',
  form_number       VARCHAR(30) NOT NULL               COMMENT '随车清单号',
  project           BIGINT(20)                          COMMENT '项目工程',
  out_storage_form  BIGINT(20)                          COMMENT '出库单',
  tally_man         BIGINT(20)                          COMMENT '理货员',
  quality_test_man  BIGINT(20)                          COMMENT '质检放行员',
  sign_man          VARCHAR(20)                         COMMENT '签收人',
  sign_time         DATETIME                            COMMENT '签收时间',
  account_status    VARCHAR(15)                         COMMENT '记账状态',
  pictures          TEXT                                COMMENT '图片',
  if_completed      BOOLEAN  NOT NULL DEFAULT FALSE   COMMENT '当前随车清单是否已经完成',
  PRIMARY KEY (id)
);

alter table t_ontruck_form comment '随车清单信息表';

## 权限的初始化数据
insert into t_ontruck_form(corporation, form_number, project, out_storage_form, if_completed) values (-1, "asdasda", 1, 1, false);
insert into t_ontruck_form(corporation, form_number, project, out_storage_form, if_completed) values (-1, "asdasd", 1, 1, false);