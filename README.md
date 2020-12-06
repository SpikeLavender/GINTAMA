#### Docker
###### DockerFile
    ```
    #1.指定基础镜像，并且必须是第一条指令
    FROM openjdk:8-jdk-alpine
         
    #2.指明该镜像的作者和其电子邮件
    MAINTAINER hetengjiao "hetengjiao_xjtu@163.com"
         
    #3.添加一个存储空间
    VOLUME /tmp
         
    #4.暴露8080端口
    EXPOSE 8080
         
    #5.添加变量，如果使用dockerfile-maven-plugin，则会自动替换这里的变量内容
    ARG JAR_FILE=base-images.jar
         
    #6.往容器中添加jar包
    ADD ${JAR_FILE} app.jar
         
    #7.容器启动时需要执行的命令
    ENTRYPOINT ["java","-Djava.security.egd=file:/dev/urandom -Dspring.profiles.active=prod -Dserver.port=8080 -Dlogging.path=/root/logs/","-jar","/app.jar"]
     ```
###### 创建镜像
   
 `docker build -t stefanie-db-adpter .`
 
###### 运行
`docker run -d --name stefanie-db-adpter -p 8081:8080 -e NACOS_SERVER_ADDR="123.207.218.247:8848" -e NACOS_NAMESPACE="a87d34ca-8b93-4141-9d12-3bed7b2ac7d3" stefanie-db-adpter`


```
 docker build -t wezard-pay .
 docker build -t wezard-server .
 docker run -d --name wezard-pay -p 8093:8080 -e NACOS_SERVER_ADDR=123.207.218.247:8848 -e NACOS_NAMESPACE=a87d34ca-8b93-4141-9d12-3bed7b2ac7d3 wezard-pay
 docker run -d --name wezard-server -p 8094:8080 -e NACOS_SERVER_ADDR=123.207.218.247:8848 -e NACOS_NAMESPACE=a87d34ca-8b93-4141-9d12-3bed7b2ac7d3 wezard-server
 docker cp wezard-pay.jar wezard-pay:/app.jar
 docker cp wezard.jar wezard-server:/app.jar
 docker restart wezard-server && docker attach --sig-proxy=false wezard-server
 docker restart wezard-pay && docker attach --sig-proxy=false wezard-pay

```