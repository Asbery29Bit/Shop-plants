theme: /

    state: Приветствие
        q!: $regex</start>
        random: 
            a: Здравствуйте! Чем могу помочь?
            go: /Обработка ответа
        buttons:
            {text: "Наш сайт", url: "https://elovpark.ru/"}
            "Корзина" -> /Корзина
        intent: /sys/aimylogic/ru/parting || toState = "/Проверка"
        event: noMatch || toState = "/Обработка ответа"

    state: Обработка ответа
        q!: * # Ответ пользователя
        script:
            var userInput = $parseTree.text ? $parseTree.text.toLowerCase() : '';
            // Определяем, для кого нужны цветы
            $session.recipient = userInput.match(/бабуш*сын|внучка|самого себя/i) ? userInput.match(/бабуш*|сын|внучка|самого себя/i)[0] : "неизвестному получателю";
            $session.myResult = "Вы сказали: " + userInput + ". Какой цвет вы бы хотели для " + $session.recipient + "?";
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
                $session.myResult = "Вы выбрали цвет: " + $session.selectedColor + ". Какого размера вы хотите?";
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
                // Здесь можно добавить логику для поиска подходящих растений
                var availablePlants = [
                    { name: "Маленький зеленый кактус", color: "зеленый", size: "маленький" },
                    { name: "Маленький белый цветок", color: "белый", size: "маленький" },
                    { name: "Маленький красный цветок", color: "красный", size: "маленький" }
                ];
                
                var matchingPlants = availablePlants.filter(function(plant) {
                    return plant.color === $session.selectedColor && plant.size === $session.selectedSize;
                });

                if (matchingPlants.length > 0) {
                    var plantNames = matchingPlants.map(function(plant) {
                        return plant.name;
                    }).join(", ");
                    $session.myResult = "Мы можем предложить вам следующие цветы для " + $session.recipient + ": " + plantNames + ". Хотите добавить их в корзину?";
                    $session.selectedPlants = matchingPlants; // Сохраняем выбранные растения в сессии
                } else {
                    $session.myResult = "К сожалению, нет доступных растений с такими параметрами.";
                }
                
                return { toState: "/Предложение" };  // Переход к следующему состоянию
            } else {
                $session.myResult = "Я не распознал размер. Пожалуйста, укажите размер растения.";
                return { toState: "/Уточнение размера" };
            }