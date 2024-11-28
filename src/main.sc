// Определение намерения "Привет"
intent('Привет', (p) => {
  p.play('Привет!');
});

// Стартовое состояние диалога
state('start', (p) => {
  p.play('Здравствуйте Как я могу вам помочь?');
  p.transition('await_greeting');
});

// Состояние ожидания приветствия
state('await_greeting', (p) => {
  p.match('Привет', (p) => {
    p.play('Привет!');
    p.transition('start'); // Возвращаемся в начальное состояние
  });
});