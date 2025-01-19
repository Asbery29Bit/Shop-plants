theme: /

    state: Приветствие
        q!: $regex</start>
        random: 
            a: Добрый день! Чем могу помочь?
            a: Приветствую! Готов помочь вам с выбором растения.
        buttons:
            {text: "Наш сайт", url: "https://elovpark.ru/"}
            "Выбрать растение" -> /Поиск растения
        intent: /sys/aimylogic/ru/parting || toState = "/Проверка"
        event: noMatch || toState = "./"

    state: Поиск растения
        q!: * # Пользовательский текст
        script:
            var userInput = $parseTree.text || "";
            
            var Color = $parseTree.text.match(/Green|Белый/i) ? true : false;
            var Размер = $parseTree.text.match(/Большой|Средний|Маленький/i);
            
            // Пример: Логика фильтрации (подставьте свою логику работы с базой данных)
            var результат = [];
            if (Color && Размер) {
                if (Размер[0] === 'Большой') {
                    результат = ['Фикус', 'Монстера'];
                } else if (размер[0] === 'Средний') {
                    результат = ['Драцена'];
                }
            }

            if (результат.length > 0) {
                $session.myResult = результат.join(", ");
            } else {
                $session.myResult = "Подходящие варианты не найдены.";
            }
        a: Вот что я нашел по вашему запросу: {{ $session.myResult }}
        buttons:
            "Выбрать другое растение" -> /Фильтры
            "На главную" -> /Приветствие
        intent: /Оформление заказа || toState = "/Оформление заказа"
        event: noMatch || toState = "./"    

    state: Не понял
        event!: noMatch
        a: Простите, я не совсем понял. Попробуйте задать вопрос по-другому.