theme: /

    state: Приветствие
        q!: $regex</start>
        random: 
            a: Добрый день!
            a: ХЕЛЛОУ МАЗАФАКЕР
            a: Здравствуй друг,купи растеньице
        buttons:
            {text: "Наш сайт", url: "https://elovpark.ru/"}
            "Сделать заказ" -> /Приветствие
        event: noMatch || toState = "./"

    state: Не понял
        event!: noMatch
        a: Извините, я не понял.

    state: Действие
        event!: /Оформление заказа
        a: Оформление заказа

    state: Описание