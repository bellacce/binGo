package com.wenhui.common.base.utils;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description :  [mybatisplay反向生成工具类]
 * @Project : wh_shopbox
 * @ClassName：MpGenerator
 * @Package Name :com.wenhui.common
 * @Author : 1437914471@qq.com
 * @Date :2019 年 09月 17 日 10:31
 * @ModifcationHistory : ------Who----------When------------What----------
 */
public class MpGenerator {
    /**
     * <p>
     * MySQL 生成演示
     * </p>
     */
    public static String tableprefix = ""; //表前缀
    public static String authot = "XinHe"; //auyhor前缀
    public static String packgName = "shop_box";
    public static File file = new File(packgName);
    public static String path = file.getAbsolutePath();

    public static void main(String[] args) {

// 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(path + "/src/main/java");
        gc.setFileOverride(false);  //是否覆盖已有文件
        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        gc.setOpen(false);//是否打开文件
        gc.setAuthor(authot);
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
//        gc.setServiceName("%sService");
//        gc.setServiceImplName("%sServiceImpl");
//        gc.setControllerName("%sController");

// 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        /*new MySqlTypeConvert() {
            @Override
            public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                return super.processTypeConvert(globalConfig, fieldType);
            }
        }*/
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert());
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("shopbox");
        dsc.setPassword("WH-shopbox");
        dsc.setUrl("jdbc:mysql://rm-bp1o7hjzg7y5u01qeio.mysql.rds.aliyuncs.com:3306/shop_box?autoReconnect=true&useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=UTC");


// 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
        strategy.setTablePrefix(new String[]{tableprefix});// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(new String[]{"store_user"}); // 需要生成的表
//        strategy.setInclude(new String[]{"base_config","store_activity","store_address","store_advert","store_blind_box","store_blind_box_deal","store_blind_box_order","store_blind_box_rule","store_cart","store_category","store_coupon","store_coupon_rule","store_coupon_user","store_freight","store_freight_order","store_freight_rule","store_goods","store_goods_comment","store_goods_sku","store_goods_spec","store_help","store_lexicon","store_member_level","store_member_useful","store_order","store_order_goods","store_order_refund","store_point_record","store_spec","store_spec_value","store_user","user_wallet","user_wallet_log","user_withdraw_cash_list"}); // 需要生成的表
        // 【实体】是否为lombok模型（默认 false）<a href="https://projectlombok.org/">document</a>
        strategy.setEntityLombokModel(true);
        /*生成 @RestController 控制器*/
        strategy.setRestControllerStyle(true);


        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.wenhui.project");
        pc.setController("web.rest");
        pc.setEntity("dal.mybatis.dataobject");
        pc.setService("biz.service");
        pc.setServiceImpl("biz.serviceimpl");
        pc.setMapper("dal.mybatis.dao");
        //XML文件配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("wh", this.getConfig().getGlobalConfig().getAuthor());
                this.setMap(map);
            }
        };
        //xml生成路径E:\maven - idea\wenhui\wh_user\src\main\resources\mybatis\mapper
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return "shop_box/src/main/resources/" + "/mybatis/mapper/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        cfg.setFileOutConfigList(focList);

        // 关闭默认 xml 生成，调整生成 至 根目录
        TemplateConfig tc = new TemplateConfig();
        tc.setXml(null);

        AutoGenerator mpg = new AutoGenerator();
        mpg.setGlobalConfig(gc); //全局配置
        mpg.setDataSource(dsc);  //数据源配置
        mpg.setStrategy(strategy);  //生成策略配置
        mpg.setPackageInfo(pc); //包配置
        mpg.setCfg(cfg); //xml配置
        mpg.setTemplate(tc);

        // 执行生成
        mpg.execute();

        // 打印注入设置【可无】
        System.err.println(mpg.getCfg().getMap().get("wh"));
    }
}
