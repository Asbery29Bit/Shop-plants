// Описание интентов
intent("choose_plant", "Посоветуй растение", "Что выбрать?", "Помоги подобрать цветы") 
intent("plant_care", "Как ухаживать за растением?", "Расскажи об уходе", "Что делать с цветком после покупки?")

// Сценарий выбора растения
+ choose_plant
    // Ввод начального вопроса
    // Спрашиваем цель выбора
    var plantPurpose = ""
    var plantColor = ""
    var plantCare = ""

    // Приветствие
    "Для чего вам нужно растение? Дом, офис или подарок?"
    buttons("Дом", "Офис", "Подарок")

    $plantPurpose = input()
    "Какие у вас предпочтения по цвету?"
    $plantColor = input()
    "Какие у вас предпочтения по уходу? Минимальный или стандартный?"
    buttons("Минимальный", "Стандартный")

    $plantCare = input()

    // Вызываем функцию для получения рекомендации
    var recommendations = recommendPlants($plantPurpose, $plantColor, $plantCare)
    "Спасибо за информацию! Рекомендую следующие растения: " + recommendations

// Сценарий ухода за растением
+ plant_care
    "Растения требуют разного ухода. Напишите название растения, и я подскажу, что делать."

// Функция для рекомендаций
$function recommendPlants(purpose, color, care) {
    // Пример массива с растениями
    var plants = [
        {"name": "Фикус", "purpose": "Дом", "color": "зелёный", "care": "Минимальный"},
        {"name": "Орхидея", "purpose": "Подарок", "color": "белый", "care": "Стандартный"}
    ]
    // Фильтрация данных
    var result = plants.filter(p => p.purpose == purpose && p.color.contains(color) && p.care == care)
    return result.map(p => p.name).join(", ")
}
