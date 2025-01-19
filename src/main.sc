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
        if (!$parseTree || !$parseTree.text) {
            $session.myResult = "К сожалению, я не смог обработать ваш запрос. Попробуйте сформулировать его по-другому.";
        } else {
            // Получаем текст пользователя
            var userInput = $parseTree.text.toLowerCase();
            var results = [];
            var questions = [];

            // Проверяем наличие ключевых слов
            if (userInput.includes("цвет") || userInput.includes("цветок")) {
                questions.push("Какой цвет вас интересует? (например, зеленый, белый, красный)");
            }
            if (userInput.includes("размер") || userInput.includes("большой") || userInput.includes("маленький")) {
                questions.push("Какой размер растения вы предпочитаете? (большой, средний, маленький)");
            }
            if (userInput.includes("тип") || userInput.includes("растение")) {
                questions.push("Какой тип растения вас интересует? (цветок, суккулент, дерево)");
            }
            if (userInput.includes("уход") || userInput.includes("легкий") || userInput.includes("сложный")) {
                questions.push("Какой уровень ухода вы предпочитаете? (легкий, средний, сложный)");
            }

            // Если есть вопросы, задаем их
            if (questions.length > 0) {
                $session.myResult = questions.join(" ");
            } else {
                $session.myResult = "К сожалению, я не смог понять ваш запрос. Можете уточнить, что именно вас интересует?";
            }
        }
    a: {{ $session.myResult }}
    buttons:
        "На главную" -> /Приветствие
    event: noMatch || toState = "./"

state: Уточнение цвета
    q!: * # Пользовательский текст
    script:
        var userInput = $parseTree.text.toLowerCase();
        var colorMatch =
