theme: /

    state: Приветствие
        q!: $regex</start>
        random: 
            a: Добрый день!
            a: Здравствуй друг,купи растеньице
        buttons:
            {text: "Наш сайт", url: "https://elovpark.ru/"}
        buttons:
            "Выбрать растение" -> /Критерии выбора
        intent: /Оформление заказа || toState = "/Действие"
        event: noMatch || toState = "./"

    state: Не понял
        event!: noMatch
        a: Извините, я не понял.

    state: Действие
        intent!: /Оформление заказа
        a: Оформление заказа
        intent: /Информация о растении || toState = "./"
        event: noMatch || toState = "./"

    state: Критерии выбора
        a: Какие у вас предпочтения?
        intent: /Оформление заказа || toState = "/Вывод растения"
        event: noMatch || toState = "./"

    state: Вывод растения
        q!: * @Имя_растения *
        a: Название продукта: {{ $parseTree.Имя_растения.name }}