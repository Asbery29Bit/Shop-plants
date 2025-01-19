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
        var recipientMatch = userInput.match(/бабу|сын|внучк|самого себя|себе/i);
        $session.recipient = recipientMatch ? recipientMatch[0] : "неизвестному получателю";
        $session.myResult = "Ответьте на пару наших вопросов и мы подберем цветок для " + $session.recipient + ".";
    a: {{ $session.myResult }}
    go!: /Уточнение типа цветка
    event: noMatch || toState = "./"

state: Уточнение типа цветка
    a: Какой тип цветка вы бы хотели? (например, роза, тюльпан, орхидея)
    q!: * # Пользовательский текст
    script:
        var userInput = $parseTree.text ? $parseTree.text.toLowerCase() : '';
        var flowerTypeMatch = userInput.match(/роза|тюльпан|орхидея/i);
        
        if (flowerTypeMatch) {
            $session.selectedFlowerType = flowerTypeMatch[0];
            $session.myResult = "Вы выбрали тип цветка: " + $session.selectedFlowerType + ".";
        } else {
            $session.myResult = "Я не распознал тип цветка. Пожалуйста, укажите тип цветка.";
        }
    a: {{ $session.myResult }}
    go: /Уточнение цвета
    event: noMatch || toState = "./"

state: Уточнение цвета
    a: Какой цвет цветка вы бы хотели?
    q!: * # Пользовательский текст
    script:
        var userInput = $parseTree.text ? $parseTree.text.toLowerCase() : '';
        var colorMatch = userInput.match(/зеленый|белый|красный|синий|желтый/i);
        
        if (colorMatch) {
            $session.selectedColor = colorMatch[0];
            $session.myResult = "Вы выбрали цвет: " + $session.selectedColor + ".";
        } else {
            $session.myResult = "Я не распознал цвет. Пожалуйста, укажите цвет растения.";
        }
    a: {{ $session.myResult }}
    go: /Уточнение размера
    event: noMatch || toState = "./"

state: Уточнение размера
    a: Какого размера цветок вы бы хотели?
    q!: * # Пользовательский текст
    script:
        var userInput = $parseTree.text ? $parseTree.text.toLowerCase() : '';
        var sizeMatch = userInput.match(/большой|средний|маленький/i);
        
        if (sizeMatch) {
            $session.selectedSize = sizeMatch[0];
            var availablePlants = [
                { name: "Маленький зеленый кактус", color: "зеленый", size: "маленький", type: "кактус" },
                { name: "Маленький белый цветок", color: "белый", size: "маленький", type: "цветок" },
                { name: "Большая красная роза", color: "красный", size: "большой", type: "роза" },
                { name: "Средний желтый тюльпан", color: "желтый", size: "средний", type: "тюльпан" },
                { name: "Большая орхидея", color: "белый", size: "большой", type: "орхидея" }
            ];
            
            var matchingPlants = availablePlants.filter(function(plant) {
                return plant.color === $session.selectedColor && plant.size === $session.selectedSize && plant.type === $session.selected