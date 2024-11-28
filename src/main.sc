from jaicp import Bot, State, Session

# Создаем экземпляр бота
bot = Bot()

@bot.stateless_handler(intent='Приветствие')
def handle_greeting(session):
    session.send_response('Здравствуйте! Чем могу помочь вам сегодня?')

@bot.stateful_handler(intent='Поиск растения', state=State.START)
def handle_search_plant(session):
    session.send_response('Какое растение вы ищете?')
    
    # Переход к следующему состоянию
    return State.WAITING_FOR_PLANT_NAME

@bot.stateful_handler(state=State.WAITING_FOR_PLANT_NAME)
def handle_get_plant_name(session):
    plant_name = session.last_user_input.text
    session.set_slot('plant_name', plant_name)
    
    session.send_response(f'Отличный выбор! {plant_name} — прекрасное растение.')
    
    # Переход к следующему состоянию
    return State.SHOW_PLANT_INFO

@bot.stateful_handler(state=State.SHOW_PLANT_INFO)
def handle_show_plant_info(session):
    plant_name = session.get_slot('plant_name')
    info = get_plant_info(plant_name)  # Функция для получения информации о растении
    
    if info:
        session.send_response(info)
    else:
        session.send_response(f'К сожалению, у нас нет информации о растении "{plant_name}". Может быть, попробуете другое название?')
        
    # Возвращаемся в начальное состояние
    return State.START

# Запускаем бота
if __name__ == "__main__":
    bot.run(debug=True)