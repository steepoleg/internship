Для тестирования работоспособности авторизации составим набор позитивных и набор негативных сценариев.
Предположим, что в системе зарегистрирован пользователь с номером телефона 89208849057, электронной почтой oleg@bk.ru и паролем &QWErty90

Позитивные сценарии:
Логин: oleg@bk.ru         Пароль: &QWErty90
Логин: 89208849057      Пароль: &QWErty90
Логин: 9208849057        Пароль: &QWErty90
Логин: 79208849057      Пароль: &QWErty90
Логин: +79208849057    Пароль: &QWErty90

Негативные сценарии:
Логин: oleg@bk.ru    Пароль: QWErty90
Логин: oleg@bk.ru    Пароль: &QWErty90&QWErty90zxcvbnmm
Логин: oleg@bk.ru    Пароль: &QWErtyЖ
Логин: olegbk.ru        Пароль: &QWErty90
Логин: 208849057     Пароль: &QWErty90
Логин: oleg@bkru    Пароль: &QWErty90
Логин: oleg@bk.       Пароль: &QWErty90
Логин: @bk.ru          Пароль: &QWErty90
Логин: пустое поле  Пароль: &QWErty90
Логин: oleg@bk.ru    Пароль: пустое поле
Логин: @                    Пароль: &QWErty90
Логин:  null                Пароль: &QWErty90
Логин: oleg@bk.ru    Пароль: null 
Логин: null                 Пароль: null 
Логин: пустое поле   Пароль: пустое поле
Логин: oleg@bk.ru    Пароль: &&&&^&*(
Логин: oleg@bk.ru    Пароль: &&&&8888
Логин: oleg@bk.ru    Пароль: &&&&asdf
Логин: oleg@bk.ru    Пароль: &QW   Erty90
Логин: oleg   @bk.ru    Пароль: &QWErty90