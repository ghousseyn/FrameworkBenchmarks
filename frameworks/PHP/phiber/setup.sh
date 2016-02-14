#!/bin/bash

fw_depends php nginx composer

sed -i 's|localhost|'"${DBHOST}"'|g' application/config.php
sed -i 's|".*/FrameworkBenchmarks/php-phiber|"'"${TROOT}"'|g' deploy/php-phiber
sed -i 's|Directory .*/FrameworkBenchmarks/php-phiber|Directory '"${TROOT}"'|g' deploy/php-phiber
sed -i 's|root .*/FrameworkBenchmarks/php-phiber|root '"${TROOT}"'|g' deploy/nginx.conf
sed -i 's|/usr/local/nginx/|'"${IROOT}"'/nginx/|g' deploy/nginx.conf

php-fpm --fpm-config $FWROOT/config/php-fpm.conf -g $TROOT/deploy/php-fpm.pid
nginx -c $TROOT/deploy/nginx.conf
