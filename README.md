# internal

## env:项目环境设置
项目编码,jdk版本
常用plugin
repositories/pluginRepositories
distributionManagement

## bom:外部依赖设置
```shell script
mvn deploy -DaltDeploymentRepository="nexus-releases::default::http://47.104.84.63:8081/repository/maven-releases/"
```

## base:整合env+bom 方便使用


## nexus proxy
> https://maven.aliyun.com/repository/central


## version update
mvn versions:set -DnewVersion=<...>
