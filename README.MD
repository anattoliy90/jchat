### Описание

Простой чат на rest api.

Пользователь регистрируется и подтверждает свою почту. Авторизация реализована с помощью jwt токена.

Собеседника можно найти в поиске по емейлу, имени или фамилии.

Чаты могут быть 1 на 1 или группы. В группы можно добавлять других пользователей. В группах есть админ (по умолчанию создатель группы), он может менять название и картинку группы, делать админом других пользователей, удалять сообщения других пользователей, банить пользователей.

Чаты можно удалить. При удалении группы, происходит и выход из нее. 

Пользователи могут отправлять сообщения, редактировать и удалять их. 

Можно менять данные в профиле пользователя.

### Список задач

- swagger
- авторизация/регистрация
  - ролевка: админ, юзер
- chats
  - контроллер зайти/выйти из чата
  - add - добавить в setUsers и setAdmins пользователя создавшего чат, использовать jwt токен
  - контроллер добавление/удаление admins
  - чат 1 на 1 и группы - разделить логику?
- тесты junit
- сделать часть функционала реактивным с помощью mono и flux

## Rest api

### Auth

login (POST)

```
http://localhost:8080/api/v1/auth/login

{
    "email": "test@gmail.com",
    "password": 123
}
```

newAccessToken (POST)

```
http://localhost:8080/api/v1/auth/newAccessToken

{
    "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MkBnbWFpbC5jb20iLCJleHAiOjE2NTMxNjAxOTl9.FASXeV2HXMsEYaTmI1x1tfiaLcSN5EN_arEDRIetUFXiByEdOYDUJ8y2dDdyrmuPbqqACydEkF-9aTODHUk1nA"
}
```

refreshTokens (POST)

```
http://localhost:8080/api/v1/auth/refreshTokens

{
    "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MkBnbWFpbC5jb20iLCJleHAiOjE2NTMxNjAxOTl9.FASXeV2HXMsEYaTmI1x1tfiaLcSN5EN_arEDRIetUFXiByEdOYDUJ8y2dDdyrmuPbqqACydEkF-9aTODHUk1nA"
}
```

### Users

get (GET)

```
http://localhost:8080/api/v1/users/1
```

add (POST)

```
http://localhost:8080/api/v1/users/add

{
    "firstName": "name",
    "lastName": "last name",
    "email": "new@gmail.com",
    "password": "$2a$08$gVoyKUzDrEombZdK463B..Zlo51f9zQb5ZJbuBjybSs6ZLQtvDv3a"
}
```

update (PUT)

```
http://localhost:8080/api/v1/users/1

{
    "firstName": "name2",
    "lastName": "last name2",
    "email": "new2@gmail.com",
    "confirmed": true
}
```

delete (DELETE)

```
http://localhost:8080/api/v1/users/1
```

findByEmail (GET)
```
http://localhost:8080/api/v1/users/find?email=test
```

searchByFirstAndOrLastName (GET)

```
http://localhost:8080/api/v1/users/search?firstName=test&lastName=last
```

avatar (POST)

```
http://localhost:8080/api/v1/users/1/avatar
```

changePassword (POST)

```
http://localhost:8080/api/v1/users/1/change/password

{
    "oldPassword": "12345678",
    "newPassword": "87654321"
}
```

activation (POST)

```
http://localhost:8080/api/v1/users/activation/7f8f2437-30e5-47a0-89c5-c7bb3020e0e1
```

### Chats

getById (GET)

```
http://localhost:8080/api/v1/chats/1
```

add (POST)

```
http://localhost:8080/api/v1/chats/add

{
    "title": "chat name",
    "image": "/upload/user-avatar/.png",
    "chatType": "PRIVATE"
}
```

update (PUT)

```
http://localhost:8080/api/v1/chats/1

{
    "title": "chat name2",
    "image": "/upload/user-avatar2/.png",
    "chatType": "GROUP"
}
```

delete (DELETE)

```
http://localhost:8080/api/v1/chats/1
```

search (GET)

```
http://localhost:8080/api/v1/chats/search/title
```

### Messages

getById (GET)

```
http://localhost:8080/api/v1/messages/1
```

add (POST)

```
http://localhost:8080/api/v1/messages/add

{
    "chat": {"id": 1},
    "text": "text"
}
```

update (PUT)

```
http://localhost:8080/api/v1/messages/1

{
    "text": "text",
    "pinned": true
}
```

delete (DELETE)

```
http://localhost:8080/api/v1/messages/1
```

findByChat (GET)

```
http://localhost:8080/api/v1/messages/find/chat/1
```

pin (POST)

```
http://localhost:8080/api/v1/messages/1/pin

{
    "pin": true
}
```
