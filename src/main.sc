theme: /

    state: Приветствие
        q!: $regex</start>
        random: 
            a: Добрый день! Чем могу помочь?
            a: Приветствую! Готов помочь вам с выбором растения.
        buttons:
            {text: "Наш сайт", url: "https://elovpark.ru/"}
            "Выбрать растение" -> /Обработка ответа
            "Корзина" -> /Корзина
        intent: /sys/aimylogic/ru/parting || toState = "/Проверка"
        event: noMatch || toState = "./"
    
    state: Обработка ответа
        q!: * # Ответ пользователя
        script:
            var userInput = $parseTree.text ? $parseTree.text.toLowerCase() : '';
            $session.myResult = "Вы сказали: " + userInput + ". Давайте выберем растение!";
            return { toState: "/Уточнение цвета" };  // Переход к следующему состоянию
        a: {{ $session.myResult }}
        buttons:
            "Дальше" -> /Уточнение цвета
        event: noMatch || toState = "./"
    
    state: Уточнение цвета
        q!: * # Пользовательский текст
        script:
            var userInput = $parseTree.text ? $parseTree.text.toLowerCase() : '';
            var colorMatch = userInput.match(/зеленый|белый|красный|синий|желтый/i);
            
            if (colorMatch) {
                $session.selectedColor = colorMatch[0];
                $session.myResult = "Вы выбрали цвет: " + $session.selectedColor + ". Какой размер растения вас интересует? (большой, средний, маленький)";
                return { toState: "/Уточнение размера" };  // Переход к следующему состоянию
            } else {
                $session.myResult = "Я не распознал цвет. Пожалуйста, укажите цвет растения.";
                return { toState: "/Уточнение цвета" };  // Остаемся на том же этапе
            }
        a: {{ $session.myResult }}
        buttons:
            "Дальше" -> /Уточнение размера
        event: noMatch || toState = "./"
    
    state: Уточнение размера
        q!: * # Пользовательский текст
        script:
            var userInput = $parseTree.text ? $parseTree.text.toLowerCase() : '';
            var sizeMatch = userInput.match(/большой|средний|маленький/i);
            
            if (sizeMatch) {
                $session.selectedSize = sizeMatch[0];
                $session.myResult = "Вы выбрали размер: " + $session.selectedSize + ". Какой тип растения вас интересует? (цветок, суккулент, дерево)";
                return { toState: "/Уточнение типа" };  // Переход к следующему состоянию
            } else {
                $session.myResult = "Я не распознал размер. Пожалуйста, укажите размер растения.";
                return { toState: "/Уточнение размера" };  // Остаемся на том же этапе
            }
        a: {{ $session.myResult }}
        buttons:
            "Дальше" -> /Уточнение типа
        event: noMatch || toState = "./"
    
    state: Уточнение типа
        q!: * # Пользовательский текст
        script:
            var userInput = $parseTree.text ? $parseTree.text.toLowerCase() : '';
            var typeMatch = userInput.match(/цветок|суккулент|дерево/i);
            
            if (typeMatch) {
                $session.selectedType = typeMatch[0];
                $session.myResult = "Вы выбрали тип: " + $session.selectedType + ". Спасибо за выбор! Я подберу для вас подходящие растения.";
                // Здесь можно добавить логику для поиска растений на основе выбранных параметров
            } else {
                $session.myResult = "Я не распознал тип. Пожалуйста, укажите тип растения.";
                return { toState: "/Уточнение типа" };  // Остаемся на том же этапе
            }
        a: {{ $session.myResult }}
        buttons:
            "На главную"
