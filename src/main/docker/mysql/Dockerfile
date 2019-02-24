FROM mysql:5.7

# 修改时区，使得容器时间为北京时间
RUN echo "Asia/Shanghai" > /etc/timezone

VOLUME my.cnf:/etc/mysql/my.cnf mysql

#将所需文件放到容器中
ADD setup.sh /etc/mysql/setup.sh
ADD schema.sql /etc/mysql/schema.sql
ADD privileges.sql /etc/mysql/privileges.sql

EXPOSE 3306
ENV MYSQL_ALLOW_EMPTY_PASSWORD=yes
#设置容器启动时执行的命令
CMD ["sh", "/etc/mysql/setup.sh"]