#!/bin/sh
if [ ! -d /maven/ce2017-distribution-0.1.0 ]
then
 unzip -d /maven /maven/ce2017-distribution-0.1.0.zip > /dev/null 2>&1
fi

/maven/ce2017-distribution-0.1.0/bin/karaf server
