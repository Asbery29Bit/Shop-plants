// Интенты
intent("choose_plant") {
    examples = [
        "Посоветуй растение",
        "Что выбрать?",
        "Помоги подобрать цветы"
    ]
}

intent("plant_care") {
    examples = [
        "Как ухаживать за растением?",
        "Расскажи об уходе",
        "Что делать с цветком после покупки?"
    ]
}

// Сценарий выбора растения
+ choose_plant {
    // Приветствие
    #say "Для чего вам нужно растение? Дом, офис или подарок?"
    buttons {
        "Дом" -> {
            #setContext("purpose", "Дом")
        }
        "Офис" -> {
            #setContext("purpose", "Офис")
        }
        "Подарок" -> {
            #setContext("purpose", "Подарок")
        }
    }

    // Спрашиваем цвет
    #say "Какие у вас предпочтения по цвету?"
    $color = input()

    // Спрашиваем уход
    #say "Какие у вас предпочтения по уходу? Минимальный или стандартный?"
    buttons {
        "Минимальный" -> {
            #setContext("care", "Минимальный")
        }
        "Стандартный" -> {
            #setContext("care", "Стандартный")
        }
    }

    // Рекомендация растений
    #exec recommendPlants purpose=$purpose color=$color care=$care -> $result
    #say "Спасибо за информацию! Рекомендую следующие растения: $result."
}

// Сценарий ухода за растением
+ plant_care {
    #say "Растения требуют разного ухода. Напишите название растения, и я подскажу, что делать."
    $plantName = input()
    #say "Рекомендации по уходу за растением '$plantName' отправлены на ваш email."
}

// Функция для рекомендации растений
$function recommendPlants(purpose, color, care) {
    var plants = [
        {"name": "Фикус", "purpose": "Дом", "color": "зелёный", "care": "Минимальный"},
        {"name": "Орхидея", "purpose": "Подарок", "color": "белый", "care": "Стандартный"}
    ]
    var result = plants.filter(p => p.purpose == purpose && p.color.contains(color) && p.care == care)
    return result.map(p => p.name).join(", ")
}
