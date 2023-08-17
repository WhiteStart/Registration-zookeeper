```yml
version: '3'

services:
  zookeeper:
    image: docker.io/bitnami/zookeeper:3.8
    container_name: zoo
    ports:
      - "2181:2181"
    environment:
      - ZOO_ENABLE_AUTH=yes
      - ZOO_SERVER_USERS=user
      - ZOO_SERVER_PASSWORDS=password
      - ZOO_CLIENT_USER=user
      - ZOO_CLIENT_PASSWORD=password
```



采用了 PrettyZoo 作为 UI 界面，并在源码中添加 JVM 参数来实现带认证的链接

https://github.com/vran-dev/PrettyZoo

在app/build.gradle中添加如下参数,在运行源码即可

```
def runArgsValue = [
    ......
"-Djava.security.auth.login.config={your_path}/jaas.conf",
```