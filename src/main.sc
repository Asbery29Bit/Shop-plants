theme: /
    
    state: Приветствие
        script:
            $session.color = null
            $session.size = null
            $session.type = null
            $session.chose = false
        q!: $regex</start>
        a: Здравствуйте! Чем могу помочь?
        go: /Оформление заказа
        event: noMatch || toState = "/Обработка ответа"
        
    state: Оформление заказа
        intent!: /Оформление заказа
        script:
            if ($parseTree._recipient != undefined && $parseTree._recipient.date != undefined) {
                $session.recipient = $parseTree._recipient.date;
            } else {
                $session.recipient = null;
            }
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
            if ($parseTree._color != undefined && $parseTree._color.date != undefined) {
                $session.color = $parseTree._color.date;
            } else {
                $session.color = null;
            }
        if: $session.color == null
            a: Вы сказали: "{{$request.query}}". Но растения такого цвета я не знаю, пожалуйста, укажите другой.
        else: 
            a: Вы выбрали цвет {{$session.color}}.
            a: Какого размера растение вы бы хотели?
            go: /Уточнение размера
        event: noMatch || toState = "./"
        
        
    
    state: Уточнение размера
        intent!: /Уточнение размера
        script:
            if ($parseTree._size != undefined && $parseTree._size.date != undefined) {
                $session.size = $parseTree._size.date;
            } else {
                $session.size = null;
            }
        if: $session.size == undefined
            a: Вы сказали: "{{$request.query}}". Но растения такого размера я не знаю, пожалуйста, укажите другой
            #go: /Уточнение размера
        else: 
            a: Вы выбрали размер {{$session.size}}
            a: Какой тип растения вы бы хотели?
            go: /Уточнение типа
        event: noMatch || toState = "./"
        
        
    
    state: Уточнение типа
        intent!: /Уточнение типа
        script:
            if ($parseTree._type != undefined && $parseTree._type.date != undefined) {
                $session.type = $parseTree._type.date;
            } else {
                $session.type = null;
            }
        if: $session.type == undefined
            a: Вы сказали: "{{$request.query}}". Но растения такого типа я не знаю, пожалуйста, укажите другой
            #go: /Уточнение типа
        else: 
            a: Вы выбрали тип {{$session.type}}
            go!: /Проверка
        event: noMatch || toState = "./"
        
        
    
    state: Не понял
        a: Извините я не понял
        go!: /Проверка
    
    state: Проверка
        script:
            $session.chose = true;
        a: Вы задали следующие параметры: \n{{$session.color}}, {{$session.size}}, {{$session.type}}
        a: Все верно?
        event: noMatch || toState = "/Не понял"
        
    state: Согласие
        intent: /Согласие
        if: $session.chose == true
            script: 
                $session.chose = false
            go!: /Подбор растений
        else:
            a: Извините я не понял, пожалуйста, повторите запрос

        
    state: Несогласие
        intent: /Несогласие
        if: $session.chose == true
            script: 
                $session.chose = false
                $session.color = null
                $session.size = null
                $session.type = null
            a: Хорошо, давайте начнем сначала
            a: Какого цвета растение вы бы хотели?
            go: /Уточнение цвета
        else:
            a: Извините я не понял, пожалуйста, повторите запрос
            
    
    state: Подбор растений
        intent!: /Подбор растений
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
            $session.color = null
            $session.size = null
            $session.type = null
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
            a: Не удалось найти растения по вашим параметрам. Попробуйте ещё раз.
        buttons:
            "Вернуться в начало" -> /Приветствие