theme: /

state: Приветствие
    q!: $regex</start>
    random: 
        a: Добрый день! Чем могу помочь?
        a: Приветствую! Готов помочь вам с выбором растения.
    buttons:
        {text: "Наш сайт", url: "https://elovpark.ru/"}
        "Выбрать растение" -> /Поиск растения
        "Корзина" -> /Корзина
    intent: /sys/aimylogic/ru/parting || toState = "/Проверка"
    event: noMatch || toState = "./"

    state: Поиск растения
    q!: * # Пользовательский текст
    script:
        if (!$parseTree || !$parseTree.text) {
            $session.myResult = "К сожалению, я не смог обработать ваш запрос. Попробуйте сформулировать его по-другому.";
        } else {
            var userInput = $parseTree.text.toLowerCase();
            var questions = [];

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
        var colorMatch = userInput.match(/зеленый|белый|красный|синий|желтый/i);
        
        if (colorMatch) {
            $session.selectedColor = colorMatch[0];
            $session.myResult = `Вы выбрали цвет: ${$session.selectedColor}. Какой размер растения вас интересует? (большой, средний, маленький)`;
            toState = "/Уточнение размера";  // Переход к следующему состоянию
        } else {
            $session.myResult = "Я не распознал цвет. Пожалуйста, укажите цвет растения.";
        }
    a: {{ $session.myResult }}
    buttons:
        "На главную" -> /Приветствие
    event: noMatch || toState = "./"

    state: Уточнение размера
    q!: * # Пользовательский текст
    script:
        var userInput = $parseTree.text.toLowerCase();
        var sizeMatch = userInput.match(/большой|средний|маленький/i);
        
        if (sizeMatch) {
            $session.selectedSize = sizeMatch[0];
            $session.myResult = `Вы выбрали размер: ${$session.selectedSize}. Какой тип растения вас интересует? (цветок, суккулент, дерево)`;
            toState = "/Уточнение типа";  // Переход к следующему состоянию
        } else {
            $session.myResult = "Я не распознал размер. Пожалуйста, укажите размер растения.";
        }
    a: {{ $session.myResult }}
    buttons:
        "На главную" -> /Приветствие
    event: noMatch || toState = "./"

    state: Уточнение типа
    q!: * # Пользовательский текст
    script:
        var userInput = $parseTree.text.toLowerCase();
        var typeMatch = userInput.match(/цветок|суккулент|дерево/i);
        
        if (typeMatch) {
            $session.selectedType = typeMatch[0];
