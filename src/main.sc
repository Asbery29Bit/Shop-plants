require: slotfilling/slotFilling.sc
  module = sys.zb-common
theme: /

    state: Start
        q!: $regex</start>
        a: Начнём.

    state: Hello
        intent!: /привет
        a: привет

    state: Bye
        intent!: /пока
        a:  пока

    state: NoMatch
        event!: noMatch
        a: Я не понял.: {{$request.query}}

    state: Match
        event!: match
        a: {{$context.intent.answer}}
