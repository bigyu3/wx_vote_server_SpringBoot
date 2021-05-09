import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;


public class CodeGenerator {

    public static void main(String[] args) {


        //1.创建generator对象
        AutoGenerator autoGenerator = new AutoGenerator();
        //2.配置数据源
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/wx_votesystem?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=CTT");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("root");
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");

        autoGenerator.setDataSource(dataSourceConfig);

        //3.全局配置(指明这些类生成的具体位置以及作者....)
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(System.getProperty("user.dir")+"/src/main/java");
        globalConfig.setOpen(false);//不打开文件
        globalConfig.setAuthor("tianyuguo");
        //让service名字前面没有I
//        globalConfig.setServiceName("%Service");

        autoGenerator.setGlobalConfig(globalConfig);

        //4.设置包信息(生成的类放在哪个包里面)
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.gty");
//        packageConfig.setModuleName("generator");
        packageConfig.setController("controller");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("service.impl");
        packageConfig.setEntity("entity");

        autoGenerator.setPackageInfo(packageConfig);

        //5.配置策略
        StrategyConfig strategyConfig = new StrategyConfig();

        //提供lombok
        strategyConfig.setEntityLombokModel(true);
        //支持驼峰
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        autoGenerator.setStrategy(strategyConfig);

        //6.执行
        autoGenerator.execute();

    }
}
