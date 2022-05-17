INSERT INTO jchat.chat_types (id, type)
VALUES (0, 'PRIVATE'),
       (1, 'GROUP')
;

INSERT INTO jchat.users (id, first_name, last_name, email, password, confirmed)
VALUES (1, 'test', 'last', 'test@gmail.com', '$2a$08$gVoyKUzDrEombZdK463B..Zlo51f9zQb5ZJbuBjybSs6ZLQtvDv3a', true),
       (2, 'test2', 'last2', 'test2@gmail.com', '$2a$08$gVoyKUzDrEombZdK463B..Zlo51f9zQb5ZJbuBjybSs6ZLQtvDv3a', false)
;

INSERT INTO jchat.chats (id, title, image, type_id)
VALUES (1, 'test', '/upload/user-avatar/.png', 0),
       (2, 'test2', '', 1)
;

INSERT INTO jchat.messages (id, user_id, chat_id, text, pinned)
VALUES (1, 1, 1, 'message text 1', true),
       (2, 1, 1, 'message text 2', false),
       (3, 2, 2, 'message text 3', true),
       (4, 2, 2, 'message text 4', false)
;

INSERT INTO jchat.roles (id, title)
VALUES (1, 'ADMIN'),
       (2, 'USER')
;

INSERT INTO jchat.user_roles (user_id, role_id)
VALUES (1, 1),
       (2, 2)
;
