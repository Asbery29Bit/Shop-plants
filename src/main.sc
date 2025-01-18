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

state: ChooseCity || modal = true
    a: Выберите ваш город.
    buttons:
        "Москва" -> ./RememberCity
        "Санкт-Петербург" -> ./RememberCity

    state: RememberCity
        script:
            $client.city = $request.query;
            $session.cart = [];
        go!: /ChoosePlant

    state: ClickButtons
        q: *
        a: Нажмите, пожалуйста, кнопку.
        go!: ..

state: ChoosePlant
    a: Выберите растение.
    buttons:
        "Фикус" -> ./SelectVariation
        "Кактус" -> ./SelectVariation
        "Орхидея" -> ./SelectVariation

    state: SelectVariation
        script:
            $client.plant = $request.query;
        a: Выберите размер растения.
        buttons:
            "Маленький" -> ./AddToCart
            "Средний" -> ./AddToCart
            "Большой" -> ./AddToCart

    state: AddToCart
        script:
            var plant = $client.plant;
            var size = $request.query;
            var price = 0;
            if (plant == "Фикус") {
                if (size == "Маленький") price = 500;
                if (size == "Средний") price = 1000;
                if (size == "Большой") price = 1500;
            } else if (plant == "Кактус") {
                if (size == "Маленький") price = 300;
                if (size == "Средний") price = 600;
                if (size == "Большой") price = 900;
            } else if (plant == "Орхидея") {
                if (size == "Маленький") price = 700;
                if (size == "Средний") price = 1400;
                if (size == "Большой") price = 2100;
            }
            $session.cart.push({plant: plant, size: size, price: price});
        a: Растение добавлено в корзину. Хотите выбрать еще одно растение?
        buttons:
            "Да" -> /ChoosePlant
            "Нет" -> /Checkout

state: Checkout
    script:
        var total = 0;
        var orderDetails = "Ваш заказ:\n";
        $session.cart.forEach(function(item) {
            orderDetails += item.plant + " (" + item.size + ") - " + item.price + " руб.\n";
            total += item.price;
        });
        orderDetails += "Итого: " + total + " руб.";
        $response.replies.push({type: "text", text: orderDetails});
    a: Спасибо за заказ! Наш менеджер свяжется с вами для подтверждения.
    go!: /End

state: End
    q: *
    a: Спасибо за использование нашего бота!
