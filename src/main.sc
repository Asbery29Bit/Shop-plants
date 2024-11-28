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
        intent: /sys/aimylogic/ru/hello || toState = "/Приветствие"

    state: Не понял
        event!: noMatch
        a: Извините, я не понял.

    state: Действие
    {
    activators {
        intent("/Покажи интенданты") // Обработка интента
    }
        
        reactions.say("Вот доступные интенты:")
        intentsList.forEach { intent ->
            reactions.say("- $intent")
        }

        reactions.buttons("Назад", "Ещё что-то")
        }
    }
