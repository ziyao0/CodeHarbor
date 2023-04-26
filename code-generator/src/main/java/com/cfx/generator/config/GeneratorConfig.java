package com.cfx.generator.config;

import com.baomidou.mybatisplus.generator.config.rules.DateType;

/**
 * @author Eason
 * @since 2023/4/26
 */
public class GeneratorConfig {
    /**
     * 生成文件的输出目录【默认 D 盘根目录】
     */
    private String projectDir;
    /**
     * 是否覆盖已有文件
     */
    private boolean fileOverride = false;
    /**
     * 开发人员
     */
    private String author = "zhangziyao";

    private boolean open = false;

    /**
     * 是否在xml中添加二级缓存配置
     */
    private boolean enableCache = false;

    /**
     * 开启 swagger2 模式
     */
    private boolean swagger2 = false;

    /**
     * 开启 ActiveRecord 模式
     */
    private boolean activeRecord = false;

    /**
     * 开启 BaseResultMap
     */
    private boolean baseResultMap = false;

    /**
     * 时间类型对应策略
     */
    private DateType dateType = DateType.TIME_PACK;
    /**
     * 开启 baseColumnList
     */
    private boolean baseColumnList = false;

    private String serviceName = "%sService";

    private String driverName;

    private String userName;

    private String url;

    private String password;

    //PackageConfig

    private String moduleName;
    private String parent;

    // TemplateConfig

    private String entity;

    private String service;

    private String controller;


    // StrategyConfig

    private String superEntityClass;
    private String superControllerClass;
    /**
     * 表名，多个英文逗号分割
     */
    private String include;

    private String superEntityColumns;

    private String templatePath = "/templates/mapper.xml.ftl";

    public String getProjectDir() {
        return projectDir;
    }

    public void setProjectDir(String projectDir) {
        this.projectDir = projectDir;
    }

    public boolean isFileOverride() {
        return fileOverride;
    }

    public void setFileOverride(boolean fileOverride) {
        this.fileOverride = fileOverride;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isEnableCache() {
        return enableCache;
    }

    public void setEnableCache(boolean enableCache) {
        this.enableCache = enableCache;
    }

    public boolean isSwagger2() {
        return swagger2;
    }

    public void setSwagger2(boolean swagger2) {
        this.swagger2 = swagger2;
    }

    public boolean isActiveRecord() {
        return activeRecord;
    }

    public void setActiveRecord(boolean activeRecord) {
        this.activeRecord = activeRecord;
    }

    public boolean isBaseResultMap() {
        return baseResultMap;
    }

    public void setBaseResultMap(boolean baseResultMap) {
        this.baseResultMap = baseResultMap;
    }

    public DateType getDateType() {
        return dateType;
    }

    public void setDateType(DateType dateType) {
        this.dateType = dateType;
    }

    public boolean isBaseColumnList() {
        return baseColumnList;
    }

    public void setBaseColumnList(boolean baseColumnList) {
        this.baseColumnList = baseColumnList;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getSuperEntityClass() {
        return superEntityClass;
    }

    public void setSuperEntityClass(String superEntityClass) {
        this.superEntityClass = superEntityClass;
    }

    public String getSuperControllerClass() {
        return superControllerClass;
    }

    public void setSuperControllerClass(String superControllerClass) {
        this.superControllerClass = superControllerClass;
    }

    public String getInclude() {
        return include;
    }

    public void setInclude(String include) {
        this.include = include;
    }

    public String getSuperEntityColumns() {
        return superEntityColumns;
    }

    public void setSuperEntityColumns(String superEntityColumns) {
        this.superEntityColumns = superEntityColumns;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }
}
