/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2021/2/19 18:09:18                           */
/*==============================================================*/


drop table if exists cpd_advertising;

drop table if exists cpd_advertising_activity_raletion;

drop table if exists cpd_display_advertising;

drop table if exists cpd_finance;

drop table if exists cpd_sku_costprice;

drop table if exists cpd_stadvertising;

drop table if exists cpd_video_advertising;

drop table if exists oms_order_refund;

drop table if exists oms_order_refund_repeat;

drop table if exists oms_order_return;

drop table if exists oms_order_return_repeat;

drop table if exists pms_asin_type_relation;

drop table if exists pms_bad_commodity;

drop table if exists pms_bad_commodity_repeat;

drop table if exists pms_sku_info;

drop table if exists sys_zw_ip_filter;

/*==============================================================*/
/* Table: cpd_advertising                                       */
/*==============================================================*/
create table cpd_advertising
(
   id                   bigint not null auto_increment comment '主键',
   store_code           varchar(64) comment '店铺',
   country              varchar(64) comment '国家',
   type                 varchar(64) comment '型号',
   asin                 varchar(64) comment 'asin',
   sku                  varchar(64) comment 'sku',
   click                bigint comment '点击量',
   exposure             bigint comment '曝光',
   month                varchar(64) comment '月份',
   ctr                  float comment 'ctr',
   cvr                  float comment 'cvr',
   advertising_order    int comment '广告订单量',
   advertising_spend    decimal(15,2) comment '广告花费',
   acos                 float comment 'acos',
   cpc                  decimal(15,2) comment 'cpc',
   advertising_order_percentage float comment '广告订单占比',
   acoas                float comment 'acoas',
   refund_rate          float comment '退款率',
   sessions             int comment 'sessions',
   advertising_sales    decimal(15,2) comment '广告销售额',
   sales                decimal(15,2) comment '销售额',
   refund               int comment '退款量',
   order_num            int comment '订单量',
   sales_num            int comment '销量',
   create_by            varchar(64) comment '创建者',
   create_time          datetime comment '创建时间',
   update_by            varchar(64) comment '更新者',
   update_time          datetime comment '更新时间',
   isdelete             tinyint comment '删除标记（1为删除,2为未删除）',
   primary key (id)
);

alter table cpd_advertising comment '广告源数据表';

/*==============================================================*/
/* Table: cpd_advertising_activity_raletion                     */
/*==============================================================*/
create table cpd_advertising_activity_raletion
(
   id                   bigint not null auto_increment comment '主键',
   activity             varchar(128) comment '广告活动',
   asin                 varchar(64) comment 'ASIN',
   sku                  varchar(64) comment '标准SKU',
   store_code           varchar(32) comment '店铺',
   commissioner         varchar(32) comment '广告专员',
   primary key (id)
);

alter table cpd_advertising_activity_raletion comment '广告活动映射表';

/*==============================================================*/
/* Table: cpd_display_advertising                               */
/*==============================================================*/
create table cpd_display_advertising
(
   id                   bigint not null auto_increment comment '主键',
   store_code           varchar(64) comment '账号简称',
   asin                 varchar(64) comment 'ASIN',
   sku                  varchar(64) comment '标准SKU',
   commissioner         varchar(32) comment '广告专员',
   type                 varchar(16) comment '产品类型',
   link                 varchar(128) comment 'Listing链接',
   profit               decimal(15,2) comment '利润（不计算广告点数，只计算平台佣金，税费，不良率）',
   cpa                  decimal(15,2) comment 'CPA（广告费用/订单总数）',
   cpa_profit           decimal(15,2) comment 'CPA盈亏(利润值-CPA)',
   cpa_profit_level     tinyint comment 'CPA等级',
   ctr_level            tinyint comment 'CTR等级',
   cvr_level            tinyint comment 'CVR等级',
   acos_level           tinyint comment 'ACOS等级',
   operation_feedback   varchar(128) comment '广告专员操作反馈',
   identification_code  varchar(256) comment '识别码',
   start_date           date comment 'Start Date',
   end_date             date comment 'End Date',
   currency             varchar(64) comment 'Currency',
   campaign_name        varchar(128) comment 'Campaign Name',
   ad_group_name        varchar(64) comment 'Ad Group Name',
   targeting            varchar(64) comment 'Targeting',
   impressions          integer comment 'Impressions',
   clicks               integer comment 'Clicks',
   ctr                  float comment 'Click-Thru Rate (CTR)',
   cpc                  decimal(15,2) comment 'Cost Per Click (CPC)',
   spend                decimal(15,2) comment 'Spend',
   total_sales          decimal(15,2) comment '7 Day Total Sales ',
   acos                 float comment 'Total Advertising Cost of Sales (ACoS) ',
   roas                 decimal(15,2) comment 'Total Return on Advertising Spend (RoAS)',
   total_orders         integer comment '7 Day Total Orders (#)',
   total_units          integer comment '7 Day Total Units (#)',
   cvr                  float comment '7 Day Conversion Rate',
   costprice            decimal(15,2) comment '单价',
   create_by            varchar(64) comment '创建者',
   create_time          datetime comment '创建时间',
   update_by            varchar(64) comment '更新者',
   update_time          datetime comment '更新时间',
   primary key (id)
);

alter table cpd_display_advertising comment 'Display广告数据源表';

/*==============================================================*/
/* Table: cpd_finance                                           */
/*==============================================================*/
create table cpd_finance
(
   id                   bigint not null auto_increment comment '主键',
   month                varchar(64) not null comment '月份',
   site                 varchar(64) comment '站点',
   principal            varchar(64) comment '负责人',
   type                 varchar(64) comment '型号',
   sales_revenue        decimal(15,2) comment '销售收入(含自配送）',
   compensation         decimal(15,2) comment '赔偿金',
   final_freight        decimal(15,2) comment '尾程运费信用',
   packaging_fee        decimal(15,2) comment '包装费',
   promotion_fee        decimal(15,2) comment '促销费',
   platform_refund      decimal(15,2) comment '平台退款',
   final_freight_return decimal(15,2) comment '尾程运费退回（N6+T6+AG6,占销售比例14.5%）',
   selfdelivery_commission decimal(15,2) comment '自配送销售佣金',
   fba_sales_commission decimal(15,2) comment 'FBA销售佣金',
   other_transaction_fee decimal(15,2) comment '其他交易费',
   storage_fee          decimal(15,2) comment '仓储费',
   shipping_label_fee   decimal(15,2) comment '运输标签费',
   platform_service_fee decimal(15,2) comment '平台服务费',
   platform_refund_service_fee decimal(15,2) comment '平台退款服务费',
   platform_service_adjustment_fee decimal(15,2) comment '平台服务调整费',
   advertising_fee      decimal(15,2) comment '广告费（3.32%）',
   service_provider_fee decimal(15,2) comment '服务商费用',
   gross_profit         decimal(15,2) comment '财务报表毛利',
   asinking_num         int comment 'Asinking数量',
   yc_delivery_num      int comment '易仓出货量',
   price                decimal(15,2) comment '单价',
   mixed_vat            decimal(15,2) comment '17, 18 混合增值税',
   other_fee            decimal(15,2) comment '其他',
   mixed_vat2           decimal(15,2) comment '34 35 混合增值税',
   clear_broker_fee     decimal(15,2) comment '清算经纪费',
   final_freight_return2 decimal(15,2) comment '尾程运费退回',
   create_by            varchar(64) comment '创建者',
   create_time          datetime comment '创建时间',
   update_by            varchar(64) comment '更新者',
   update_time          datetime comment '更新时间',
   isdelete             tinyint comment '删除标记（1为删除,2为未删除）',
   primary key (id)
);

alter table cpd_finance comment '财务数据表';

/*==============================================================*/
/* Table: cpd_sku_costprice                                     */
/*==============================================================*/
create table cpd_sku_costprice
(
   id                   bigint not null auto_increment comment '主键',
   sku                  varchar(64) comment '标准SKU',
   costprice            decimal(15,2) comment '成本价(不含平台费，广告，不良等费用）',
   primary key (id)
);

alter table cpd_sku_costprice comment '成本表';

/*==============================================================*/
/* Table: cpd_stadvertising                                     */
/*==============================================================*/
create table cpd_stadvertising
(
   id                   bigint not null auto_increment comment '主键',
   store_code           varchar(64) comment '账号简称',
   asin                 varchar(64) comment 'ASIN',
   sku                  varchar(64) comment '标准SKU',
   commissioner         varchar(32) comment '广告专员',
   type                 varchar(16) comment '产品类型',
   link                 varchar(128) comment 'Listing链接',
   profit               decimal(15,2) comment '利润（不计算广告点数，只计算平台佣金，税费，不良率）',
   cpa                  decimal(15,2) comment 'CPA（广告费用/订单总数）',
   cpa_profit           decimal(15,2) comment 'CPA盈亏(利润值-CPA)',
   cpa_profit_level     tinyint comment 'CPA等级',
   ctr_level            tinyint comment 'CTR等级',
   cvr_level            tinyint comment 'CVR等级',
   acos_level           tinyint comment 'ACOS等级',
   operation_feedback   varchar(128) comment '广告专员操作反馈',
   identification_code  varchar(256) comment '识别码',
   start_date           date comment 'Start Date',
   end_date             date comment 'End Date',
   portfolio_name       varchar(64) comment 'Portfolio name',
   currency             varchar(64) comment 'Currency',
   campaign_name        varchar(128) comment 'Campaign Name',
   ad_group_name        varchar(64) comment 'Ad Group Name',
   targeting            varchar(64) comment 'Targeting',
   match_type           varchar(64) comment 'Match Type',
   customer_search_term varchar(128) comment 'Customer Search Term',
   impressions          integer comment 'Impressions',
   clicks               integer comment 'Clicks',
   ctr                  float comment 'Click-Thru Rate (CTR)',
   cpc                  decimal(15,2) comment 'Cost Per Click (CPC)',
   spend                decimal(15,2) comment 'Spend',
   total_sales          decimal(15,2) comment '7 Day Total Sales ',
   acos                 float comment 'Total Advertising Cost of Sales (ACoS) ',
   roas                 decimal(15,2) comment 'Total Return on Advertising Spend (RoAS)',
   total_orders         integer comment '7 Day Total Orders (#)',
   total_units          integer comment '7 Day Total Units (#)',
   cvr                  float comment '7 Day Conversion Rate',
   advertised_sku_units integer comment '7 Day Advertised SKU Units (#)',
   other_sku_units      integer comment '7 Day Other SKU Units (#)',
   advertised_sku_sales decimal(15,2) comment '7 Day Advertised SKU Sales ',
   other_sku_sales      decimal(15,2) comment '7 Day Other SKU Sales ',
   costprice            decimal(15,2) comment '单价',
   create_by            varchar(64) comment '创建者',
   create_time          datetime comment '创建时间',
   update_by            varchar(64) comment '更新者',
   update_time          datetime comment '更新时间',
   primary key (id)
);

alter table cpd_stadvertising comment 'ST广告数据源表';

/*==============================================================*/
/* Table: cpd_video_advertising                                 */
/*==============================================================*/
create table cpd_video_advertising
(
   id                   bigint not null auto_increment comment '主键',
   store_code           varchar(64) comment '账号简称',
   asin                 varchar(64) comment 'ASIN',
   sku                  varchar(64) comment '标准SKU',
   commissioner         varchar(32) comment '广告专员',
   type                 varchar(16) comment '产品类型',
   link                 varchar(128) comment 'Listing链接',
   profit               decimal(15,2) comment '利润（不计算广告点数，只计算平台佣金，税费，不良率）',
   cpa                  decimal(15,2) comment 'CPA（广告费用/订单总数）',
   cpa_profit           decimal(15,2) comment 'CPA盈亏(利润值-CPA)',
   cpa_profit_level     tinyint comment 'CPA等级',
   ctr_level            tinyint comment 'CTR等级',
   cvr_level            tinyint comment 'CVR等级',
   acos_level           tinyint comment 'ACOS等级',
   operation_feedback   varchar(128) comment '广告专员操作反馈',
   identification_code  varchar(256) comment '识别码',
   start_date           date comment 'Start Date',
   end_date             date comment 'End Date',
   portfolio_name       varchar(64) comment 'Portfolio name',
   currency             varchar(64) comment 'Currency',
   campaign_name        varchar(128) comment 'Campaign Name',
   ad_group_name        varchar(64) comment 'Ad Group Name',
   targeting            varchar(64) comment 'Targeting',
   match_type           varchar(64) comment 'Match Type',
   customer_search_term varchar(256) comment 'Customer Search Term',
   impressions          integer comment 'Impressions',
   clicks               integer comment 'Clicks',
   ctr                  float comment 'Click-Thru Rate (CTR)',
   cpc                  decimal(15,2) comment 'Cost Per Click (CPC)',
   spend                decimal(15,2) comment 'Spend',
   total_sales          decimal(15,2) comment '7 Day Total Sales ',
   acos                 float comment 'Total Advertising Cost of Sales (ACoS) ',
   roas                 decimal(15,2) comment 'Total Return on Advertising Spend (RoAS)',
   total_orders         integer comment '7 Day Total Orders (#)',
   total_units          integer comment '7 Day Total Units (#)',
   cvr                  float comment '7 Day Conversion Rate',
   costprice            decimal(15,2) comment '单价',
   create_by            varchar(64) comment '创建者',
   create_time          datetime comment '创建时间',
   update_by            varchar(64) comment '更新者',
   update_time          datetime comment '更新时间',
   primary key (id)
);

alter table cpd_video_advertising comment '视频广告数据源表';

/*==============================================================*/
/* Table: oms_order_refund                                      */
/*==============================================================*/
create table oms_order_refund
(
   id                   bigint not null auto_increment comment '主键',
   store_code           varchar(64) comment '账号-站点',
   date                 date comment 'Date',
   order_id             varchar(64) comment 'Order ID',
   sku                  varchar(128) comment 'SKU',
   transaction_type     varchar(64) comment 'Transaction type',
   payment_type         varchar(64) comment 'Payment Type',
   payment_detail       varchar(64) comment 'Payment Detail',
   amount               varchar(64) comment 'Amount',
   quantity             integer comment 'Quantity',
   product_title        varchar(256) comment 'Product Title',
   create_by            varchar(64) comment '创建者',
   create_time          datetime comment '创建时间',
   update_by            varchar(64) comment '更新者',
   update_time          datetime comment '更新时间',
   primary key (id)
);

alter table oms_order_refund comment '退款表';

/*==============================================================*/
/* Table: oms_order_refund_repeat                               */
/*==============================================================*/
create table oms_order_refund_repeat
(
   id                   bigint not null auto_increment comment '主键',
   repeat_id            varchar(64) comment '重复id',
   store_code           varchar(64) comment '账号-站点',
   date                 date comment 'Date',
   order_id             varchar(64) comment 'Order ID',
   sku                  varchar(128) comment 'SKU',
   transaction_type     varchar(64) comment 'Transaction type',
   payment_type         varchar(64) comment 'Payment Type',
   payment_detail       varchar(64) comment 'Payment Detail',
   amount               varchar(64) comment 'Amount',
   quantity             integer comment 'Quantity',
   product_title        varchar(256) comment 'Product Title',
   create_by            varchar(64) comment '创建者',
   create_time          datetime comment '创建时间',
   update_by            varchar(64) comment '更新者',
   update_time          datetime comment '更新时间',
   primary key (id)
);

alter table oms_order_refund_repeat comment '退款表(重复数据）';

/*==============================================================*/
/* Table: oms_order_return                                      */
/*==============================================================*/
create table oms_order_return
(
   id                   bigint not null auto_increment comment '主键',
   store_code           varchar(32) comment '账号-站点',
   return_date          datetime comment 'return-date',
   order_id             varchar(64) comment 'order-id',
   sku                  varchar(128) comment 'sku',
   asin                 varchar(64) comment 'asin',
   fnsku                varchar(128) comment 'fnsku',
   product_name         varchar(256) comment 'product-name',
   quantity             integer comment 'quantity',
   fulfillment_center_id varchar(64) comment 'fulfillment-center-id',
   detailed_disposition varchar(64) comment 'detailed-disposition',
   reason               varchar(64) comment 'reason',
   status               varchar(64) comment 'status',
   license_plate_number varchar(32) comment 'license-plate-number',
   customer_comments    varchar(64) comment 'customer-comments',
   update_time          datetime comment '更新时间',
   detailed_disposition_forcn varchar(64) comment 'detailed-disposition翻译',
   create_by            varchar(64) comment '创建者',
   create_time          datetime comment '创建时间',
   update_by            varchar(64) comment '更新者',
   reason_forcn         varchar(64) comment 'reason翻译',
   primary key (id)
);

alter table oms_order_return comment '退货表';

/*==============================================================*/
/* Table: oms_order_return_repeat                               */
/*==============================================================*/
create table oms_order_return_repeat
(
   id                   bigint not null auto_increment comment '主键',
   repeat_id            varchar(64) comment '重复id',
   store_code           varchar(32) comment '账号-站点',
   return_date          datetime comment 'return-date',
   order_id             varchar(64) comment 'order-id',
   sku                  varchar(128) comment 'sku',
   asin                 varchar(64) comment 'asin',
   fnsku                varchar(128) comment 'fnsku',
   product_name         varchar(256) comment 'product-name',
   quantity             integer comment 'quantity',
   fulfillment_center_id varchar(64) comment 'fulfillment-center-id',
   detailed_disposition varchar(64) comment 'detailed-disposition',
   reason               varchar(64) comment 'reason',
   status               varchar(64) comment 'status',
   license_plate_number varchar(32) comment 'license-plate-number',
   customer_comments    varchar(64) comment 'customer-comments',
   detailed_disposition_forcn varchar(64) comment 'detailed-disposition翻译',
   reason_forcn         varchar(64) comment 'reason翻译',
   create_by            varchar(64) comment '创建者',
   create_time          datetime comment '创建时间',
   update_by            varchar(64) comment '更新者',
   update_time          datetime comment '更新时间',
   primary key (id)
);

alter table oms_order_return_repeat comment '退货表重复数据表';

/*==============================================================*/
/* Table: pms_asin_type_relation                                */
/*==============================================================*/
create table pms_asin_type_relation
(
   id                   bigint not null auto_increment comment '主键',
   type                 varchar(64) comment '型号',
   asin                 varchar(64) comment 'ASIN',
   isdelete             tinyint comment '删除标记（1为删除,2为未删除）',
   primary key (id)
);

alter table pms_asin_type_relation comment 'ASIN与型号关联表';

/*==============================================================*/
/* Table: pms_bad_commodity                                     */
/*==============================================================*/
create table pms_bad_commodity
(
   id                   bigint not null auto_increment comment '主键',
   store_code           varchar(64) comment '账户站点',
   principal            varchar(64) comment '销售负责人',
   order_date           date comment '订单日期',
   deal_date            date comment '处理日期',
   order_id             varchar(64) comment '订单号或无订单号',
   type                 varchar(64) comment '型号',
   sku                  varchar(128) comment '标准SKU',
   bad_num              integer comment '不良总数
            （个数）',
   reissue_num          integer comment '补发数量
            （个数）',
   refund_num           integer comment '退款数量
            （个数）',
   traceback_code       varchar(64) comment '追溯码',
   bad_reason           varchar(64) comment '不良原因',
   remarks              varchar(64) comment '备注（具体客诉原因）',
   solution             varchar(64) comment '产品不良解决方式',
   bad_source           varchar(64) comment '不良来源',
   fba_sku              varchar(128) comment 'FBA SKU',
   return_date          varchar(64) comment 'return-date',
   type2                varchar(64) comment '型号2',
   eachset_num          integer comment '每套个数',
   bad_num2             integer comment '不良总个数',
   yc_sku               varchar(64) comment '补发或退款
            易仓SKU',
   reissue2             integer comment '补发数量
            （个数）',
   reissue_tracking_id  varchar(64) comment '补发跟踪号',
   isdelivered          tinyint comment '补发是否送达',
   refund_num2          integer comment '退款数量
            （个数）',
   refund_amount        decimal(15,2) comment '退款金额
            （默认原币）',
   mail_follow_times    varchar(32) comment '邮件主动
            跟进次数',
   last_follow_time     date comment '最后跟进时间
            时间格式：yyyy/MM/dd',
   isdone               varchar(32) comment '是否完成',
   feedback_case_id     varchar(64) comment 'Feedback
            Case ID',
   remarks2             varchar(64) comment '备注',
   pic_url1             varchar(512) comment '不良图片一',
   pic_url2             varchar(128) comment '不良图片二',
   pic_url3             varchar(128) comment '不良图片三',
   create_by            varchar(64) comment '创建者',
   create_time          datetime comment '创建时间',
   update_by            varchar(64) comment '更新者',
   update_time          datetime comment '更新时间',
   primary key (id)
);

alter table pms_bad_commodity comment '不良记录表';

/*==============================================================*/
/* Table: pms_bad_commodity_repeat                              */
/*==============================================================*/
create table pms_bad_commodity_repeat
(
   id                   bigint not null auto_increment comment '主键',
   repeat_id            varchar(64) comment '重复id',
   store_code           varchar(64) comment '账户站点',
   principal            varchar(64) comment '销售负责人',
   order_date           date comment '订单日期',
   deal_date            date comment '处理日期',
   order_id             varchar(64) comment '订单号或无订单号',
   type                 varchar(64) comment '型号',
   sku                  varchar(128) comment '标准SKU',
   bad_num              integer comment '不良总数
            （个数）',
   reissue_num          integer comment '补发数量
            （个数）',
   refund_num           integer comment '退款数量
            （个数）',
   traceback_code       varchar(64) comment '追溯码',
   bad_reason           varchar(64) comment '不良原因',
   remarks              varchar(64) comment '备注（具体客诉原因）',
   solution             varchar(64) comment '产品不良解决方式',
   bad_source           varchar(64) comment '不良来源',
   fba_sku              varchar(128) comment 'FBA SKU',
   return_date          varchar(64) comment 'return-date',
   type2                varchar(64) comment '型号2',
   eachset_num          integer comment '每套个数',
   bad_num2             integer comment '不良总个数',
   yc_sku               varchar(64) comment '补发或退款
            易仓SKU',
   reissue2             integer comment '补发数量
            （个数）',
   reissue_tracking_id  varchar(64) comment '补发跟踪号',
   isdelivered          tinyint comment '补发是否送达',
   refund_num2          integer comment '退款数量
            （个数）',
   refund_amount        decimal(15,2) comment '退款金额
            （默认原币）',
   mail_follow_times    varchar(32) comment '邮件主动
            跟进次数',
   last_follow_time     date comment '最后跟进时间
            时间格式：yyyy/MM/dd',
   isdone               varchar(32) comment '是否完成',
   feedback_case_id     varchar(64) comment 'Feedback
            Case ID',
   remarks2             varchar(64) comment '备注',
   pic_url1             varchar(512) comment '不良图片一',
   pic_url2             varchar(128) comment '不良图片二',
   pic_url3             varchar(128) comment '不良图片三',
   create_by            varchar(64) comment '创建者',
   create_time          datetime comment '创建时间',
   update_by            varchar(64) comment '更新者',
   update_time          datetime comment '更新时间',
   primary key (id)
);

alter table pms_bad_commodity_repeat comment '不良记录重复数据表';

/*==============================================================*/
/* Table: pms_sku_info                                          */
/*==============================================================*/
create table pms_sku_info
(
   sku_id               bigint not null auto_increment comment 'skuId',
   spu_id               bigint comment 'spuId',
   sku_name             varchar(255) comment 'sku名称',
   sku_desc             varchar(2000) comment 'sku介绍描述',
   catalog_id           bigint comment '所属分类id',
   brand_id             bigint comment '品牌id',
   sku_default_img      varchar(255) comment '默认图片',
   sku_title            varchar(255) comment '标题',
   sku_subtitle         varchar(2000) comment '副标题',
   price                decimal(18,4) comment '价格',
   sale_count           bigint comment '销量',
   primary key (sku_id)
);

alter table pms_sku_info comment 'sku信息';

/*==============================================================*/
/* Table: sys_zw_ip_filter                                      */
/*==============================================================*/
create table sys_zw_ip_filter
(
   id                   bigint not null auto_increment comment '主键',
   ip                   varchar(64) comment 'IP地址',
   moudle               varchar(64) comment '模块',
   mark                 tinyint comment '标记',
   create_by            bigint comment '创建者',
   create_time          datetime comment '创建时间',
   update_by            bigint comment '更新者',
   update_time          datetime comment '更新时间',
   primary key (id)
);

alter table sys_zw_ip_filter comment 'ZwIpFilter';

