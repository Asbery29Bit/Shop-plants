theme: /

    state: Приветствие
        q!: $regex</start>
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
            $session.recipient = userInput.match(/бабу*|сын*|внучк*|самого себя|себе/i) ? userInput.match(/бабушки|сын|внучка|самого себя/i)[0] : "неизвестному получателю";
            $session.myResult = "Ответьте на пару наших вопросов и мы подберем цветок для " + $session.recipient + ".";
        a: {{ $session.myResult }}
        a: Какой цвет цветка вы бы хотели?
        go: /Уточнение цвета
        event: noMatch || toState = "./"
    
    state: Уточнение цвета
        q!: * # Пользовательский текст
        script:
           var userInput = $parseTree.text ? $parseTree.text.toLowerCase() : '';
           if (!userInput) {
               $session.myResult = "Пожалуйста, укажите цвет растения.";
           } else {
               var colorMatch = userInput.match(/зеленый|белый|красный|синий|желтый/i);
               
               if (colorMatch) {
                   $session.selectedColor = colorMatch[0];
                   $session.myResult = "Вы выбрали цвет: " + $session.selectedColor + ".";
               } else {
                   $session.myResult = "Я не распознал цвет. Пожалуйста, укажите один из следующих цветов: зеленый, белый, красный, синий, желтый.";
               }
           }
        a: {{ $session.myResult }}
        a: Какого размера цветок вы бы хотели?
        go: /Уточнение размера
        event: noMatch || toState = "./"
    
    state: Уточнение размера
        a: Какого размера цветок вы бы хотели?
        q!: * # Пользовательский текст
        script:
            var userInput = $parseTree.text ? $parseTree.text.toLowerCase() : '';
            if (!userInput) {
                $session.myResult = "Пожалуйста, укажите размер растения.";
                return { toState: "/Уточнение размера" }; // Повторяем вопрос
            }
            var sizeMatch = userInput.match(/большой|средний|маленький/i);
            
            if (sizeMatch) {
                $session.selectedSize = sizeMatch[0];
                // Здесь можно добавить логику для поиска подходящих растений
                var availablePlants = [
                    { name: "Маленький зеленый кактус", color: "зеленый", size: "маленький" },
                    { name: "Маленький белый цветок", color: "белый", size: "маленький" },
                    { name: "Маленький красный цветок", color: "красный", size: "маленький" },
                    { name: "Средний синий цветок", color: "синий", size: "средний" },
                    { name: "Большой желтый цветок", color: "желтый", size: "большой" },
                    { name: "Средний зеленый куст", color: "зеленый", size: "средний" },
                    { name: "Большой красный роза", color: "красный", size: "большой" }
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
            } else {
                $session.myResult = "Я не распознал размер. Пожалуйста, укажите размер растения.";
                return { toState: "/Уточнение размера" };
            }

