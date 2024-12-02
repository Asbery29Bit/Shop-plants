states:
  start:
    component: "System.Say"
    properties:
      text: "Здравствуйте! Чем могу помочь вам сегодня?"
    transitions:
      next: ask_plant

  ask_plant:
    component: "System.Say"
    properties:
      text: "Какое растение вас интересует? У нас есть широкий выбор!"
    transitions:
      next: select_plant

  select_plant:
    component: "System.AskOptions"
    properties:
      options:
        - label: "Комнатные растения"
          value: "indoor_plants"
        - label: "Садовые растения"
          value: "garden_plants"
        - label: "Экзотические растения"
          value: "exotic_plants"
    transitions:
      cases:
        indoor_plants: indoor_plants
        garden_plants: garden_plants
        exotic_plants: exotic_plants

  indoor_plants:
    component: "System.Say"
    properties:
      text: "Отличный выбор! Какие комнатные растения вас интересуют?"
    transitions:
      next: sell_indoor_plants

  garden_plants:
    component: "System.Say"
    properties:
      text: "Прекрасно! Какие садовые растения вы хотели бы приобрести?"
    transitions:
      next: sell_garden_plants

  exotic_plants:
    component: "System.Say"
    properties:
      text: "Замечательно! Какие экзотические растения вас привлекают?"
    transitions:
      next: sell_exotic_plants

  sell_indoor_plants:
    component: "System.Say"
    properties:
      text: "Мы можем предложить вам различные виды комнатных растений. Какой именно вид вас интересует?"
    transitions:
      next: confirm_order

  sell_garden_plants:
    component: "System.Say"
    properties:
      text: "У нас большой ассортимент садовых растений. Что конкретно вы ищете?"
    transitions:
      next: confirm_order

  sell_exotic_plants:
    component: "System.Say"
    properties:
      text: "Мы предлагаем уникальные экзотические растения. Какой вид вас интересует?"
    transitions:
      next: confirm_order

  confirm_order:
    component: "System.Say"
    properties:
      text: "Вы выбрали {{ $session.selectedPlant }}. Хотите оформить заказ?"
    transitions:
      yes: order_confirmation
      no: cancel_order

  order_confirmation:
    component: "System.Say"
    properties:
      text: "Ваш заказ подтвержден. Спасибо за покупку!"
    transitions:
      next: end

  cancel_order:
    component: "System.Say"
    properties:
      text: "Нет проблем, если передумаете, мы всегда готовы помочь."
    transitions:
      next: end

  end:
    component: "System.Say"
    properties:
      text: "Если у вас возникнут вопросы или потребуется помощь, обращайтесь к нам снова. До свидания!"