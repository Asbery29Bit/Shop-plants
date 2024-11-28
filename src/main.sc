# Define the intent for 'Привет'
intent: Привет

# Define the start state
state: start
  a: Здравствуйте Как я могу вам помочь?
  go: await_greeting

# Define the await_greeting state
state: await_greeting
  q: Привет
    a: Привет!
    go: start