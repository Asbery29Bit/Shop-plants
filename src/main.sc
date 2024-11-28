theme: /

    state: Приветствие
        q!: $regex</start>
        random: 
            a: Добрый день!
            a: Здравствуйте
            a: Здравствуй друг,купи растеньице
        buttons:
            {text: "Наш сайт", url: "https://elovpark.ru/"}
        intent: /Поиск растений || toState = "/Действие"

    state: Не понял
        event!: noMatch
        a: Извините, я не понял.

    state: Действие
        a: ?
            

    state: Описание