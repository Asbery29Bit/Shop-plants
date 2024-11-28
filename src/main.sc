from jaicp import Bot, State, Session

# Определение сущностей
PLANT_NAMES = ['фикус', 'папоротник', 'спатифиллум']
LIGHT_CONDITIONS = ['тенистый', 'полутень', 'яркий свет']
WATERING_FREQUENCY = ['часто', 'редко', 'умеренно']

# Определение намерений
INTENT_GREETINGS = 'greetings'
INTENT_SEARCH_PLANT = 'search_plant'
INTENT_GET_PLANT_INFO = 'get_plant_info'
INTENT_ORDER_PLANT = 'order_plant'

# Создание экземпляра бота
bot = Bot()

# Обработчики для различных состояний и намерений

@bot.stateless_handler(intent=INTENT_GREETINGS)
def handle_greetings(session):
    session.send_response('Здравствуйте! Чем я могу помочь вам сегодня?')

@bot.stateful_handler(intent=INTENT_SEARCH_PLANT, state=State.START)
def handle_search_plant(session):
    session.send_response('Какое растение вы ищете?')
    return State.WAITING_FOR_PLANT_NAME

@bot.stateful_handler(state=State.WAITING_FOR_PLANT_NAME)
def handle_get_plant_name(session):
    plant_name = session.last_user_input.text.lower().strip()
    if plant_name not in PLANT_NAMES:
        session.send_response(f'Извините, но у нас нет информации о растении "{plant_name}". Может быть, попробуйте другое название?')
        return State.START
    else:
        session.set_slot('plant_name', plant_name)
        session.send_response(f'Отличный выбор! {plant_name.capitalize()} — прекрасное растение. Что бы вы хотели узнать о нем?')
        return State.SHOW_PLANT_INFO

@bot.stateful_handler(state=State.SHOW_PLANT_INFO)
def handle_show_plant_info(session):
    plant_name = session.get_slot('plant_name')
    session.send_response(get_plant_info(plant_name))  # Здесь должна быть функция, возвращающая информацию о растении
    return State.START

@bot.stateful_handler(intent=INTENT_GET_PLANT_INFO, state=State.START)
def handle_get_plant_info(session):
    plant_name = session.last_user_input.text.lower().strip()
    if plant_name not in PLANT_NAMES:
        session.send_response(f'Извините, но у нас нет информации о растении "{plant_name}". Может быть, попробуйте другое название?')
        return State.START
    else:
        session.set_slot('plant_name', plant_name)
        session.send_response(get_plant_info(plant_name))  # Здесь должна быть функция, возвращающая информацию о растении
        return State.START

@bot.stateful_handler(intent=INTENT_ORDER_PLANT, state=State.START)
def handle_order_plant(session):
    plant_name = session.last_user_input.text.lower().strip()
    if plant_name not in PLANT_NAMES:
        session.send_response(f'Извините, но у нас нет в наличии растения "{plant_name}". Может быть, выберите другое?')
        return State.START
    else:
        session.set_slot('plant_name', plant_name)
        session.send_response(f'Отлично! Мы можем оформить заказ на {plant_name}. Сколько экземпляров вы хотите приобрести?')
        return State.GET_QUANTITY

@bot.stateful_handler(state=State.GET_QUANTITY)
def handle_get_quantity(session):
    quantity = session.last_user_input.text.strip()
    try:
        quantity = int(quantity)
        if quantity <= 0:
            raise ValueError
    except ValueError:
        session.send_response('Пожалуйста, укажите корректное количество.')
        return State.GET_QUANTITY
    plant_name = session.get_slot('plant_name')
    session.send_response(f'Спасибо за заказ! Вы приобрели {quantity} экземпляр{plural_form(quantity, "а", "ов", "")} растения {plant_name}.')
    return State.START

# Вспомогательные функции

def plural_form(number, one, few, many):
    """Возвращает правильную форму множественного числа для русского языка."""
    number = abs(number) % 100
    if 11 <= number <= 19:
        return many
    number %= 10
    if number == 1:
        return one
    elif 2 <= number <= 4:
        return few
    else:
        return many

def get_plant_info(plant_name):
    """Функция для получения информации о растении. Замените на реальную реализацию."""
    return f'Вот информация о растении {plant_name}: ...'

# Запуск бота
if __name__ == "__main__":
    bot.run(debug=True)