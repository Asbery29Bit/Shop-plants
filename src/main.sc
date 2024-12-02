{
  "intents": {
    "choose_plant": {
      "examples": [
        "Посоветуй растение",
        "Что выбрать?",
        "Помоги подобрать цветы"
      ]
    },
    "plant_care": {
      "examples": [
        "Как ухаживать за растением?",
        "Расскажи об уходе",
        "Что делать с цветком после покупки?"
      ]
    }
  },
  "scenarios": [
    {
      "intent": "choose_plant",
      "script": [
        {
          "type": "text",
          "content": "Для чего вам нужно растение? Дом, офис или подарок?"
        },
        {
          "type": "button",
          "buttons": ["Дом", "Офис", "Подарок"]
        },
        {
          "type": "input",
          "action": "store",
          "variable": "plant_purpose"
        },
        {
          "type": "text",
          "content": "Какие у вас предпочтения по цвету?"
        },
        {
          "type": "input",
          "action": "store",
          "variable": "plant_color"
        },
        {
          "type": "text",
          "content": "Какие у вас предпочтения по уходу? Минимальный или стандартный?"
        },
        {
          "type": "button",
          "buttons": ["Минимальный", "Стандартный"]
        },
        {
          "type": "input",
          "action": "store",
          "variable": "plant_care"
        },
        {
          "type": "text",
          "content": "Спасибо за информацию! Рекомендую следующие растения: {plant_recommendation}."
        },
        {
          "type": "function",
          "name": "recommend_plants",
          "args": {
            "purpose": "{{plant_purpose}}",
            "color": "{{plant_color}}",
            "care": "{{plant_care}}"
          },
          "result": "plant_recommendation"
        }
      ]
    },
    {
      "intent": "plant_care",
      "script": [
        {
          "type": "text",
          "content": "Растения требуют разного ухода. Напишите название растения, и я подскажу, что делать."
        }
      ]
    }
  ],
  "functions": {
    "recommend_plants": {
      "code": "function recommend_plants(args) { const plants = [{name: 'Фикус', purpose: 'Дом', color: 'зелёный', care: 'Минимальный'}, {name: 'Орхидея', purpose: 'Подарок', color: 'белый', care: 'Стандартный'}]; return plants.filter(p => p.purpose === args.purpose && p.color.includes(args.color) && p.care === args.care).map(p => p.name).join(', '); }"
    }
  }
}
