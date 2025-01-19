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
               }
           }
        a: {{ $session.myResult }}
        a: Какого размера цветок вы бы хотели?
        go: /Уточнение размера
        event: noMatch || toState = "./"
    
    state: Main
    q: (.*)
    script:
        var query = $request.query;
        var results = [];
        for (var i = 0; i < plants.length; i++) \{
            var plant = plants[i];
            if (query.includes(plant.name) || query.includes(plant.attributes.type) || query.includes(plant.attributes.care)) \{
                results.push(plant);
            \}
        \}
        if (results.length > 0) \{
            $session.myResult = results.map(function(result) \{
                return result.name + " - " + result.attributes.price + " руб.";
            \}).join("\n");
            $reactions.answer("Мы нашли следующие растения для вас:");
            $reactions.answer($session.myResult);
            $reactions.answer("Какого размера цветок вы бы хотели?");
            $reactions.go("/SizeClarification");
        \} else \{
            $reactions.answer("К сожалению, мы не нашли подходящих растений.");
        \}
    buttons:
        "Корзина" -> /Cart

    state: SizeClarification
        a: Какого размера цветок вы бы хотели?
        q!: *
        script:
            var size = $request.query;
            // Проверка и обработка введенного размера
            if (size.match(/(маленький|средний|большой)/i)) \{
                $session.selectedSize = size;
                $reactions.answer("Вы выбрали размер: " + size);
                // Переход к следующему состоянию, например, выбор типа цветка
                $reactions.go("/ChooseFlowerType");
            \} else \{
                $reactions.answer("Извините, я не понял размер. Пожалуйста, выберите из: маленький, средний, большой.");
                $reactions.go("/SizeClarification");
            \}
    
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