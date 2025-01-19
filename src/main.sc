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
        a: Успех
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
        a: Успех
        event: noMatch || toState = "./"        
            
state: ChooseFlowerType
    a: Какой тип цветка вы бы хотели? (комнатное, садовое)
    q!: *
    script:
        var type = $request.query;
        // Проверка и обработка введенного типа
        if (type.match(/(комнатное|садовое)/i)) \{
            $session.selectedType = type;
            $reactions.answer("Вы выбрали тип: " + type);
            // Переход к следующему состоянию, например, подтверждение заказа
            $reactions.go("/ConfirmOrder");
        \} else \{
            $reactions.answer("Извините, я не понял тип. Пожалуйста, выберите из: комнатное, садовое.");
            $reactions.go("/ChooseFlowerType");
        \}

state: ConfirmOrder
    a: Вы выбрали цветок \{\{ $session.selectedSize \}\} размера и типа \{\{ $session.selectedType \}\}. Подтвердите заказ.
    buttons:
        "Подтвердить" -> /OrderConfirmed
        "Отменить" -> /OrderCancelled

state: OrderConfirmed
    a: Ваш заказ подтвержден! Спасибо за покупку.
    script:
        // Логика для подтверждения заказа, например, сохранение в базе данных
        // Очистка сессии
        delete $session.selectedSize;
        delete $session.selectedType;

state: OrderCancelled
    a: Ваш заказ отменен. Если хотите сделать новый заказ, начните сначала.
    script:
        // Очистка сессии
        delete $session.selectedSize;
        delete $session.selectedType;

state: Cart
    intent!: /корзина
    a: Ваша корзина:
    script:
        $temp.totalSum = 0;
        for (var i = 0; i < $session.cart.length; i++) \{
            var item = $session.cart[i];
            $reactions.answer(item.name + " - " + item.price + " руб.");
            $temp.totalSum += item.price;
        \}
        $reactions.answer("Общая сумма: " + $temp.totalSum + " руб.");
    buttons:
        \{text: "Оформить заказ", request_contact: true\}
        "Меню" -> /Main

state: GetPhoneNumber
    event: telegramSendContact
    script:
        $client.phone_number = $request.rawRequest.message.contact.phone_number;
    a: Спасибо! Наш менеджер свяжется с вами по номеру телефона \{\{ $client.phone_number \}\}.