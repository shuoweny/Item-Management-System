#!/bin/bash
readonly Q0="CREATE OR REPLACE USER 'comp30022team012'@'localhost' IDENTIFIED BY 'mIigY78xU8C4vk4spyd0FMnXdxyjOATw';"
readonly Q1="CREATE DATABASE IF NOT EXISTS item_management;"
readonly Q2="GRANT ALL ON *.* TO 'comp30022team012'@'localhost' IDENTIFIED BY 'mIigY78xU8C4vk4spyd0FMnXdxyjOATw';"
readonly Q3="FLUSH PRIVILEGES;"
readonly SQL="${Q0}${Q1}${Q2}${Q3}"

mkdir /run/mysqld
chown mysql /run/mysqld
mysqld &
until [ -e /run/mysqld/mysqld.sock ]
do
     sleep 5
done
sudo mysql -uroot -e "$SQL"

nginx
java -jar /root/build/libs/item-management-0.0.1-SNAPSHOT.jar
