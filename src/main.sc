theme: /

state: Start
    q!: /start
    script:
        $context.session = {};
        $context.client = {};
        $context.temp = {};
        $context.response = {};
    a: Привет! Я бот-магазин растений. Помогу вам выбрать и заказать растения.
    go!: /ChooseCity

state: Приветствие
    q!: $regex</start>
    random: 
        a: Добрый день!
        a: Здравствуй друг, купи растеньице
    buttons:
        {text: "Наш сайт", url: "https://elovpark.ru/"}
    buttons:
        "Выбрать растение" -> /Фильтры
    intent: /sys/aimylogic/ru/parting || toState = "/Проверка"
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
    intent: /Оформление заказа || toState = "./"
    event: noMatch || toState = "./"

state: Вывод растения
    q!: * @Имя_растения *
    a: Название продукта: {{ $parseTree.Имя_растения.name }}

state: Поиск растения
    intent: /Поиск растений || toState = "./"
    event: noMatch || toState = "./"
    a: Пожалуйста, опишите, что бы вы хотели?

state: Фильтры
    q: * @Размер * || toState = "/Фильтры"
    event: noMatch || toState = "/Фильтры"
    q: * @Цена * || toState = "/Фильтры"
    a: Вы можете написать название желаемого растения или же выбрать растение по одному из следующих критериев:
        - Размер (большой, средний, маленький)
        - Цена
        - Световые условия (тень, полутень, умеренные, ярко)
        - Цвет
        - Частота полива (регулярная, редкая, умеренная)
        - Температура (прохладная, комнатная, теплая)
    q: * @Световые_условия * || toState = "/Фильтры"
    q: * @Температура * || toState = "/Фильтры"
    q: * @Цвет * || toState = "/Проверка"
    q: * @Частота_полива * || toState = "/Фильтры"

state: Проверка
    a: Например:
