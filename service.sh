#!/bin/sh

### BEGIN INIT INFO
# Provides:          {{ service_name }}
# Required-Start:    $local_fs $remote_fs $network
# Required-Stop:     $local_fs $remote_fs $network
# Should-Start:      $named
# Should-Stop:       $named
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: Start Tomcat {{ service_name }}.
# Description:       Start the Tomcat {{ service_name }} servlet engine.
### END INIT INFO

SERVICE_NAME={{ words }}
PATH_TO_JAR={{ words.jar }}
PID_PATH_NAME={{ domain_home }}/pid.file

OPTS="{{ java_opts }}"

TIMEOUT_STOP={{ timeout_stop }}
START_TIMEOUT_SECONDS={{ start_timeout_seconds }}
SERVICE_PORT={{ management_service_port }}

check()
{
    # Каждую секунду дергаем health check пока сервис не сообщит о том,
    # что он поднялся или пока не упремся в таймаут.
    for i in $(seq 1 ${START_TIMEOUT_SECONDS})
    do
        sleep 1s
        # Получаем JSON с состоянием сервиса и смотрим на поле "status".
        # Если запрос не проходит, или возвращается пустой ответ,
        # подставляем заглушку с фейковым статусом
        RESULT=$(curl -s -f http://localhost:${SERVICE_PORT}/${SERVICE_NAME}/actuator/health | \
                 ( grep . || echo '{ "status": "NOT UP" }' ) | \
                 python -c 'import sys, json; print json.load(sys.stdin)["status"]')
        if [ "${RESULT}" = "UP" ]; then
            echo "$SERVICE_NAME started with $RESULT in ${i} second(s) !"
            return
        fi
    done

    echo "$SERVICE_NAME failed to start !"

    if [ -f $PID_PATH_NAME ]; then
        PID=$(cat $PID_PATH_NAME);
        kill $PID;
        rm $PID_PATH_NAME
    fi

    exit 7

}

start()
{
    echo "$SERVICE_NAME starting ..."
    nohup java -jar $OPTS $PATH_TO_JAR /tmp 2>> /dev/null >> /dev/null & echo $! > $PID_PATH_NAME

    echo "${SERVICE_NAME} checking..."

    #check
}

stop()
{
    PID=$(cat $PID_PATH_NAME);
    echo "$SERVICE_NAME stopping!"
    kill $PID;
    echo "$SERVICE_NAME stopped!"
    rm $PID_PATH_NAME
}

# Main
case $1 in
    start)
        echo "Starting $SERVICE_NAME ..."
        if [ ! -f $PID_PATH_NAME ]; then
            start
        else
            echo "$SERVICE_NAME is already running."
        fi
    ;;
    stop)
        if [ -f $PID_PATH_NAME ]; then
            stop
        else
            echo "$SERVICE_NAME is not running!"
        fi
    ;;
    restart)
        if [ -f $PID_PATH_NAME ]; then
            stop
            # Даем приложению время остановиться
            sleep ${TIMEOUT_STOP}
            start
        else
            echo "$SERVICE_NAME is not running!"
            start
        fi
    ;;
esac
