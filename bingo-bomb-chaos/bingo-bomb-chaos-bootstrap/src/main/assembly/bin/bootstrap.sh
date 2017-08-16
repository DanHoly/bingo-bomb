#!/bin/bash
PROJECT_BIN="${BASH_SOURCE-$0}"
PROJECT_BIN="$(dirname "${PROJECT_BIN}")"
PROJECT_BIN_DIR="$(cd "${PROJECT_BIN}"; pwd)"
PROJECT_HOME_DIR="$(cd "${PROJECT_BIN}/../";pwd)"
PROJECT_PID_FILE="${PROJECT_HOME_DIR}/project.pid"
PROJECT_CONFIG_DIR="${PROJECT_HOME_DIR}/config"
PROJECT_SERVER_NAME=${PROJECT_HOME_DIR##*/}
PROJECT_LOG_DIR=$PROJECT_HOME_DIR/logs
if [ ! -d $PROJECT_LOG_DIR ]; then
	mkdir -p $PROJECT_LOG_DIR
fi

PROJECT_STDOUT_FILE=${PROJECT_LOG_DIR}/stdout.log
PROJECT_LIB_DIR=${PROJECT_HOME_DIR}/lib
PROJECT_LIB_JARS=`ls ${PROJECT_LIB_DIR} | grep .jar | awk '{print "'${PROJECT_LIB_DIR}'/"$0}' | tr "\n" ":"`

JAVA_OPTS=" -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true "
JAVA_DEBUG_OPTS=""
if [ "$2" = "debug" ]; then
	JAVA_DEBUG_OPTS=" -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n "
fi

JAVA_JMX_OPTS=""
if [ "$2" = "jmx" ]; then
	JAVA_JMX_OPTS=" -Dcom.sun.management.jmxremote.port=1099 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false "
fi

JAVA_MEM_OPTS=""
BITS=`java -version 2>&1 | grep -i 64-bit`
if [ -n "$BITS" ]; then
	JAVA_MEM_OPTS=" -server -Xmx1g -Xms1g -Xmn256m -XX:PermSize=128m -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "
else
	JAVA_MEM_OPTS=" -server -Xms1g -Xmx1g -XX:PermSize=128m -XX:SurvivorRatio=2 -XX:+UseParallelGC "
fi

function status(){
	PIDS=`ps -ef | grep java | grep "$PROJECT_SERVER_NAME" | awk '{print $2}'`
	if [ -n "$PIDS" ]; then
		echo "The $PROJECT_SERVER_NAME already started!"
		echo "PID: $PIDS"
		exit 0
	else
		echo "The $PROJECT_SERVER_NAME does not started!"
		exit 0
	fi
}

function start(){
	echo -e "Starting the $PROJECT_SERVER_NAME ...\c"
	rm -rf ${PROJECT_STDOUT_FILE}
	nohup java $JAVA_OPTS $JAVA_MEM_OPTS $JAVA_DEBUG_OPTS $JAVA_JMX_OPTS -classpath $PROJECT_CONFIG_DIR:$PROJECT_LIB_JARS com.alibaba.dubbo.container.Main > $PROJECT_STDOUT_FILE 2>&1 &
	PIDS=$!
	echo -n $PIDS > ${PROJECT_PID_FILE}
	local COUNT=0
	local STARTED
	while [ $COUNT -lt 1 ];do
		echo -e ".\c"
		sleep 1
		COUNT=0
		if [ -f "$PROJECT_STDOUT_FILE" ];then
			STARTED=`grep "Dubbo service server started" $PROJECT_STDOUT_FILE`
			if [ -n "$STARTED" ];then
				COUNT=1
				break
			fi
		fi
	done
	echo "OK!"
	echo "PID: $PIDS"
	echo "STDOUT: $PROJECT_STDOUT_FILE"
}

function stop(){
	PIDS=`ps -ef | grep java | grep "$PROJECT_SERVER_NAME" | awk '{print $2}'`
	if [ -z "$PIDS" ]; then
		echo "The $PROJECT_SERVER_NAME does not started!"
	else 
		echo -e "Stopping the $PROJECT_SERVER_NAME ...\c"
		
		for PID in $PIDS ; do
			kill -9 $PID > /dev/null 2>&1
		done
																		
		local COUNT=0
		while [ $COUNT -lt 1 ]; do    
			echo -e ".\c"
			sleep 1
			COUNT=1
			for PID in $PIDS ; do
				PID_EXIST=`ps -f -p $PID | grep java`
				if [ -n "$PID_EXIST" ]; then
					COUNT=0
					break
				fi
			done
		done
		echo "OK!"
		echo "PID: $PIDS"
	fi
}

case $1 in
	start)
		start
		;;
	stop)
		stop
		;;
	status)
		status
		;;
	restart)
		stop
		start
		;;
	*)
		echo "Usage: $0 {start|stop|status|restart}"
		exit 1
		;;
esac

exit 0

