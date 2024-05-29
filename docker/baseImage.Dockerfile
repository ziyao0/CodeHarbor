# 使用官方的 Ubuntu 镜像作为基础镜像。
FROM ubuntu:latest

LABEL maintainer="1337891505@qq.com"

# 安装基础命令和jdk17
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk curl wget vim git unzip && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# 设置java环境变量
ENV JAVA_HOME /usr/lib/jvm/java-17-openjdk-amd64
ENV PATH $JAVA_HOME/bin:$PATH

# 验证安装
RUN java -version

# 设置工作目录
WORKDIR /root

# Add any additional setup or files here
# COPY your-file /some-path/

# Expose any required ports (optional)
# EXPOSE 8080

# 设置容器启动时的默认命令为bash
CMD ["bash"]
