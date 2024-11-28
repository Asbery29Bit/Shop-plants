theme: magazin_rastenij

  intent: Привет

  state: start
    a: Здравствуйте Как я могу вам помочь?
    go: await_greeting

  state: await_greeting
    q: Привет
      a: Привет!
      go: start