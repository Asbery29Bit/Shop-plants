theme: /

    state: Приветствие
        q!: $regex</start>
        a: Здравствуйте! Чем могу помочь?
        buttons:
            {text: "Наш сайт", url: "https://elovpark.ru/"}
            "Корзина" -> /Корзина
            "Оформление заказа" -> /Оформление заказа
        go: /Оформление заказа
        intent: /sys/aimylogic/ru/parting || toState = "/Проверка"
        event: noMatch || toState = "/Обработка ответа"
    
    state: Обработка ответа
        q!: * # Ответ пользователя
        script:
            var userInput = $parseTree.text ? $parseTree.text.toLowerCase() : '';
                // Определяем, для кого нужны цветы
                $session.recipient = userInput.match(/бабу*|сын*|внучк*|самого себя|себе/i) ? userInput.match(/бабушки|сын|внучка|самого себя|себе|бабушка|внучке|сыну|самому себе/i)[0] : "неизвестному получателю";
                $session.myResult = "Ответьте на пару наших вопросов и мы подберем растение для " + $session.recipient + ".";
        a: {{ $session.myResult }}
        go!: /Запрос цвета
        event: noMatch || toState = "./"
        
    state: Оформление заказа
        intent!: /Оформление заказа
        script:
            $session.recipient = $parseTree._recipient;
        if: $session.recipient == undefined
            a: Ответьте на пару наших вопросов, и мы подберем растение для неизвестного получателя.
            a: Какого цвета растение вы бы хотели?
            go: /Уточнение цвета
        else: 
            a: Ответьте на пару наших вопросов, и мы подберем растение для {{$session.recipient}}
            a: Какого цвета растение вы бы хотели?
            go: /Уточнение цвета
        event: noMatch || toState = "./"
        
    state: Уточнение цвета
        intent!: /Уточнение цвета
        script:
            $session.color = $parseTree._color;
        if: $session.color == undefined
            a: Я не понял цвет. Вы сказали: {{$request.query}}
            go: /Уточнение цвета
        else: 
            a: вы выбрали цвет {{$session.color}}
            a: Какого размера растение вы бы хотели?
            go: /Уточнение размера
        buttons:
            "Не указывать" -> /Уточнение размера
        event: noMatch || toState = "./"
        
        
    
    state: Уточнение размера
        intent!: /Уточнение размера
        script:
            $session.size = $parseTree._size;
        if: $session.size == undefined
            a: Я не понял размер. Вы сказали: {{$request.query}}
            go: /Уточнение размера
        else: 
            a: вы выбрали размер {{$session.size}}
            a: Какой тип растения вы бы хотели?
            go: /Уточнение типа
        buttons:
            "Не указывать" -> /Уточнение типа
        event: noMatch || toState = "./"
        
        
    
    state: Уточнение типа
        intent!: /Уточнение типа
        script:
            $session.type = $parseTree._type;
        if: $session.type == undefined
            a: Я не понял тип. Вы сказали: {{$request.query}}
            go: /Уточнение типа
        else: 
            a: вы выбрали тип {{$session.type}}
            go: /Подбор растений
        buttons:
            "Не указывать" -> /Подбор растений
        event: noMatch || toState = "./"
    
    state: Подбор растений
        a: Подбираем подходящие варианты...
        script:
            $temp.response = $http.post(
                "http://185.242.118.144:8000/find_plants", 
                {
                    body: {
                        color: $session.color,
                        size: $session.size,
                        type: $session.type
                    },
                    headers: {
                        "Content-Type": "application/json"
                    }
                }
            );
        # Отправляем запрос на внешний API для поиска растений
        if: $temp.response.isOk && $temp.response.data.results.length > 0
            script:
                $temp.plantMessages = "";
                $temp.index = 0;
                while ($temp.index < $temp.response.data.results.length) {
                    $temp.plantMessages += "---\n";
                    $temp.plantMessages += "Название: " + $temp.response.data.results[$temp.index].name + "\n";
                    $temp.plantMessages += "Цвет: " + $temp.response.data.results[$temp.index].color + "\n";
                    $temp.plantMessages += "Размер: " + $temp.response.data.results[$temp.index].size + "\n";
                    $temp.plantMessages += "Тип: " + $temp.response.data.results[$temp.index].type + "\n";
                    $temp.plantMessages += "Ссылка: " + $temp.response.data.results[$temp.index].link + "\n";
                    $temp.index++;
                }
            a: |
                Вот несколько растений, которые мы нашли:
                {{$temp.plantMessages}}
        else: 
            # Если растения не найдены или произошла ошибка
            a: Не удалось найти растения по вашим параметрам. Попробуй ещё раз.
