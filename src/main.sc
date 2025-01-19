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
        go: /Подбор растений
        event: noMatch || toState = "./"
    
    state: Подбор растений
        script:
            var plants = [
                { name: "Роза", color: "красный", size: "маленький", type: "садовый" },
                { name: "Фикус", color: "зеленый", size: "большой", type: "комнатный" },
                { name: "Тюльпан", color: "желтый", size: "средний", type: "садовый" },
                { name: "Лавр", color: "зеленый", size: "маленький", type: "комнатный" },
                { name: "Гербера", color: "красный", size: "средний", type: "садовый" },
                { name: "Кактус", color: "зеленый", size: "маленький", type: "комнатный" },
                { name: "Орхидея", color: "белый", size: "маленький", type: "комнатный" },
                { name: "Петуния", color: "синий", size: "средний", type: "садовый" },
                { name: "Лилия", color: "белый", size: "большой", type: "садовый" },
                { name: "Суккулент", color: "зеленый", size: "маленький", type: "комнатный" },
                { name: "Астра", color: "красный", size: "средний", type: "садовый" },
                { name: "Бегония", color: "розовый", size: "маленький", type: "комнатный" },
                { name: "Каллы", color: "белый", size: "средний", type: "садовый" },
                { name: "Пальма", color: "зеленый", size: "большой", type: "комнатный" },
                { name: "Нарцисс", color: "желтый", size: "маленький", type: "садовый" },
                { name: "Фиалка", color: "синий", size: "маленький", type: "комнатный" },
                { name: "Гладиолус", color: "красный", size: "большой", type: "садовый" },
                { name: "Мирт", color: "зеленый", size: "маленький", type: "комнатный" },
                { name: "Цинерария", color: "синий", size: "средний", type: "комнатный" },
                { name: "Клематис", color: "белый", size: "большой", type: "садовый" },
                { name: "Лаванда", color: "синий", size: "средний", type: "садовый" }
            ];
    
            var matches = plants.filter(function(plant) {
                return plant.color === $session.selectedColor &&
                       plant.size === $session.selectedSize &&
                       plant.type === $session.selectedType;
            });
    
            if (matches.length > 0) {
                $session.myResult = "Мы нашли подходящие варианты: " + matches.map(function(plant) { return plant.name; }).join(", ") + ".";
            } else {
                var randomPlant = plants[Math.floor(Math.random() * plants.length)];
                $session.myResult += " К сожалению, мы не нашли растений, соответствующих вашим параметрам, но мы можем предложить вам: " + randomPlant.name  + " или " + randomPlant.name + ".";
            }
        a: {{ $session.myResult }}
        a: Спасибо за ваш выбор!
        event: noMatch || toState = "./"
        
    state: Cart
        q: /корзина
        a: Ваша корзина:
        script:
            if ($session.cart && $session.cart.length > 0) \{
                $temp.totalSum = 0;
                for (var i = 0; i < $session.cart.length; i++) \{
                    var item = $session.cart[i];
                    $reactions.answer(item.name + " - " + item.price + " руб.");
                    $temp.totalSum += item.price;
                \}
                $reactions.answer("Общая сумма: " + $temp.totalSum + " руб.");
                $reactions.answer("Вы можете оформить заказ или вернуться в меню.");
            \} else \{
                $reactions.answer("Ваша корзина пуста. Пожалуйста, добавьте товары в корзину.");
                $reactions.go("/Main");
            \}
        buttons:
            "Оформить заказ" -> /Checkout
            "Меню" -> /Main

    state: Checkout
        a: Пожалуйста, предоставьте ваш контактный номер для оформления заказа.
        buttons:
            \{text: "Отправить номер", request_contact: true\}
    
    state: GetPhoneNumber
        event: telegramSendContact
        script:
            $client.phone_number = $request.rawRequest.message.contact.phone_number;
            $reactions.answer("Спасибо! Наш менеджер свяжется с вами по номеру телефона " + $client.phone_number + ".");
            // Логика для подтверждения заказа, например, сохранение в базе данных
            // Очистка корзины после оформления заказа
            delete $session.cart;
            $reactions.go("/Main");
