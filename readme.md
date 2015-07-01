### Подключение liquibase

**Liquibase** — это система управления миграциями базы данных.
Настройки в файле `liquibase.properties`.

### Настройка Maven

В Maven должен появиться профиль `database-changelog`.
Вклюаем его галочкой в окошке Maven Projects, либо командой `clean install -P database-changelog`, если
вы повесили Maven на кнопку запуска.

### Готово

При следующей сборке проекта Maven будут удалены все существующие таблицы в базе данных и
созданы новые на основе чейнжлогов, которые лежат в папке `resources/db`. Если необходимо
оставить существующие данные в файле `liquibase.properties` свойство `dropFirst` меняем на `false`

### liquibase:update

Не всегда удобно запускать проект, лишь для того, чтобы потестить ваши changeset.
Можно просто запустить задачу обновления БД
Для запуска этой задачи потребуется подтянуть ресурсы(урлы бд, пароли и тд), поэтому
сделать это можно через создание новой задачи для мавена(в "Edit configuration in IDEA")
таск для запуска должен быть такой
`resources:resources properties:read-project-properties liquibase:update`
с профилем `database-changelog`
или если через командную строку
`mvn resources:resources properties:read-project-properties liquibase:update -P database-changelog`


### Деплой на сервере
Такой же процесс как и на локальных машинах, но команды нужно выполнять вручную
Чтобы положить war в ваш томкат нужно собрать проект вот так
 mvn clean install cargo:deploy -P your-profile
 
### Модуль авторизации и security
Для подключения и сборки модуля нужно спулить проект http://jetbrainslab.it.kpfu.ru/ainurminibaev/pretty_student_client.git
собрать там модуль auth-module ( mvn clean install) чтобы исходники проекта появились у вас в .m2
дальше проект будет работать, периодически обновлять проект
//TODO придумать maven репозиторий для упрощения этого процесса
//TODO если вдруг нужен будет док по модулю, то напишу
