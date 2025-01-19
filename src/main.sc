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
            $session.recipient = userInput.match(/бабу*|сын*|внучк*|самого себя|себе/i) ? userInput.match(/бабушки|сын|внучка|самого себя|себе|бабушка|внучке|сыну|самому себе/i)[0] : "неизвестному получателю";
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
                   return { toState: "/Уточнение цвета" };
               }
           }
        a: {{ $session.myResult }}
        a: Какого размера цветок вы бы хотели?
        go: /Уточнение размера
        event: noMatch || toState = "./"
    
    state: Уточнение размера
        q!: * # Пользовательский текст
        script:
            var userInput = $parseTree.text ? $parseTree.text.toLowerCase() : '';
            if (!userInput) {
                $session.myResult = "Пожалуйста, укажите размер растения.";
                return { toState: "/Уточнение размера" }; // Повторяем вопрос
            } else {
                var sizeMatch = userInput.match(/большой|средний|маленький/i);
                
                if (sizeMatch) {
                       $session.selectedSize = sizeMatch[0];
                       $session.myResult = "Вы выбрали размер: " + $session.selectedSize + ".";
                   } else {
                       $session.myResult = "Я не распознал размер. Пожалуйста, укажите один из следующих размеров: маленький, средний, большой.";
                       return { toState: "/Уточнение размера" };
                   }
            }
        a: {{ $session.myResult }}
        a: Какой тип растения вы бы хотели?
        go: /Уточнение типа
        event: noMatch || toState = "./"
    
    state: Уточнение типа
        q!: * # Пользовательский текст
        script:
            var userInput = $parseTree.text ? $parseTree.text.toLowerCase() : '';
            if (!userInput) {
                $session.myResult = "Пожалуйста, укажите тип растения.";
                return { toState: "/Уточнение типа" }; // Повторяем вопрос
            } else {
                var typeMatch = userInput.match(/комнатный|садовый/i);
                
                if (typeMatch) {
                       $session.selectedType = typeMatch[0];
                       $session.myResult = "Вы выбрали тип: " + $session.selectedType + ".";
                   } else {
                       $session.myResult = "Я не распознал тип. Пожалуйста, укажите один из следующих типов: комнатный или садовый.";
                       return { toState: "/Уточнение типа" };
                   }
            }
        a: {{ $session.myResult }}
        a: Подбираем подходящие варианты...
        go!: /Подбор растений
        event: noMatch || toState = "./"
    
    state: Подбор растений
        script:
            var plants = [
                { name: "Роза", color: "красный", size: "маленький", type: "садовый", link:"-"},
                { name: "Фикус", color: "зеленый", size: "большой", type: "комнатный", link:"-" },
                { name: "Тюльпан", color: "желтый", size: "средний", type: "садовый", link:"-" },
                { name: "Лавр", color: "зеленый", size: "маленький", type: "комнатный", link:"-"},
                { name: "Гербера", color: "красный", size: "средний", type: "садовый", link:"-" },
                { name: "Кактус", color: "зеленый", size: "маленький", type: "комнатный", link:"-" },
                { name: "Орхидея", color: "белый", size: "маленький", type: "комнатный", link:"-" },
                { name: "Петуния", color: "синий", size: "средний", type: "садовый", link:"-" },
                { name: "Лилия", color: "белый", size: "большой", type: "садовый", link:"-" },
                { name: "Суккулент", color: "зеленый", size: "маленький", type: "комнатный", link:"-" },
                { name: "Астра", color: "красный", size: "средний", type: "садовый", link:"-" },
                { name: "Бегония", color: "розовый", size: "маленький", type: "комнатный", link:"-" },
                { name: "Каллы", color: "белый", size: "средний", type: "садовый", link:"-" },
                { name: "Пальма", color: "зеленый", size: "большой", type: "комнатный", link:"-" },
                { name: "Нарцисс", color: "желтый", size: "маленький", type: "садовый", link:"-" },
                { name: "Фиалка", color: "синий", size: "маленький", type: "комнатный", link:"-" },
                { name: "Гладиолус", color: "красный", size: "большой", type: "садовый", link:"-" },
                { name: "Мирт", color: "зеленый", size: "маленький", type: "комнатный", link:"-" },
                { name: "Цинерария", color: "синий", size: "средний", type: "комнатный", link:"-" },
                { name: "Клематис", color: "белый", size: "большой", type: "садовый", link:"-" },
                { name: "Лаванда", color: "синий", size: "средний", type: "садовый", link:"-" }
            ];
    
            var matches = plants.filter(function(plant) {
                return plant.color === $session.selectedColor &&
                       plant.size === $session.selectedSize &&
                       plant.type === $session.selectedType;
            });
    
            if (matches.length > 0) {
                $session.myResult = "Мы нашли подходящие варианты: " + matches.map(function(plant) { return plant.name + " (цвет: " + plant.color + ")"; }).join("\n") + ".";
            } else {
                var randomPlant = plants[Math.floor(Math.random() * plants.length)];
                $session.myResult += " К сожалению, мы не нашли растений, соответствующих вашим параметрам, но мы можем предложить вам: " + randomPlant.name + " (цвет: " + randomPlant.color + ")" + ".";
            }
        a: {{ $session.myResult }}
        a: Спасибо за ваш выбор!
        event: noMatch || toState = "./"
