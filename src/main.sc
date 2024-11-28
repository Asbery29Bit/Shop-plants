{
  "intents": [
    {
      "name": "Привет",
      "utterances": [
        "Привет",
        "Здравствуйте",
        "Хай"
      ]
    }
  ],
  "states": [
    {
      "name": "start",
      "message": "Здравствуйте Как я могу вам помочь?",
      "transition": "await_greeting"
    },
    {
      "name": "await_greeting",
      "match": "Привет",
      "response": "Привет!",
      "transition": "start"
    }
  ]
}