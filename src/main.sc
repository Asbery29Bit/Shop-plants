theme: /

    state: Приветствие
        q!: $regex</start>
        random: 
            a: Добрый день!
            a: ХЕЛЛОУ МАЗАФАКЕР
            a: Здравствуй друг,купи растеньице
        buttons:
            {text: "Наш сайт", url: "https://elovpark.ru/"}
        intent: /Оформление заказа  toState = "/Действие"
        event: smsFailedEvent  toState = "./"
        event: noMatch  toState = "./"

    state: Не понял
        event!: noMatch
        a: Извините, я не понял.

    state: Действие
        intent!: /Оформление заказа
        a: Оформление заказа
        intent: /Информация о растении  toState = "./"
        event: noMatch || toState = "./"

    state: Описание