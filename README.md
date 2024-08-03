# Task Manager

Проект представляет собой простой менеджер задач.
Пользователь может:

1) добавлять
2) удалять
3) редактировать задачи
4) менять статус задачи

Сложной логики в проекте нет, на данный момент это просто CRUD приложение,
для того чтобы попробовать новые для себя технологии.

## Стек технологий
- Java 21 / Kotlin / TS
- Spring Boot / React
- Postgres
- Kafka
- Docker

## Сборка и запуск

Для сборки и запуска проекта создайте .env файл
в корне проекта и укажите в нем переменные окружения:

```
JWT_KEY=hhpofdybcsiotabikhfgcgoajhlfzqmctkzvxabq
ACCESS_TOKEN_EXPIRATION=3600000

DB_HOST=localhost
DB_PORT=5432
DB_NAME=task-tracker
DB_USER=postgres
DB_PASSWORD=password

KAFKA_PORT=9092

ADMIN_MAIL=admin@admin.ru
ADMIN_PASS=password

MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=user@mail.com
MAIL_PASSWORD=password

MAIL_SMTP_CONNECTION_TIME_OUT=15000
MAIL_SMTP_TIME_OUT=30000
MAIL_SMTP_WRITE_TIME_OUT=15000
```


```shell 
docker compose --env-file .env up -d
```