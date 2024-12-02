state = {}

def manage_plant_shop(state, session):
    if "plant_name" not in state:
        state["plant_name"] = None
        state["price"] = None
        state["message"] = "Приветствую вас в нашем магазине растений! Могу ли я помочь вам выбрать растение?"
    elif state["plant_name"] is None:
        plant_name = session.get("plant_name", "").strip()
        if plant_name:
            state["plant_name"] = plant_name
            price = get_price(plant_name)
            if price:
                state["price"] = price
                state["message"] = f"{plant_name} стоит {price} рублей."
            else:
                state["message"] = f"К сожалению, {plant_name} сейчас недоступен. Может быть, выберете другой цветок?"
        else:
            state["message"] = "Извините, я не понял ваш запрос. Можете повторить название растения?"
    elif state["price"]:
        state["message"] = f"Ваша покупка {state['plant_name']} стоит {state['price']}. Хотите продолжить покупку?"
    else:
        state["message"] = "Спасибо за визит!"

def get_price(plant_name):
    prices = {"роза": 500, "тюльпан": 300}
    return prices.get(plant_name.lower(), None)

manage_plant_shop(state, session)
print(state["message"])