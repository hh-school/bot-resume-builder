Здесь будет описание, как запустить и настроить бота

### Запуск maven проекта
#### из командной строки
`mvn package exec:java`
#### из IntelliJ IDEA
1. Импортируем проект из Maven
2. Открываем `Run` -> `Edit Configurations...`
3. Добавляем новую конфигурацию Maven
4. Во вкладке `Parameters` для `Command line` указываем `package exec:java`
