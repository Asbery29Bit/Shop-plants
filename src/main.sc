{
  "project" : {
    "id" : "organizatsionnoe-1000009678-dVM",
    "name" : "organizatsionnoe-1000009678-dVM",
    "folder" : "/jaicp"
  },
  "settings" : {
    "language" : "ru",
    "spellingCorrection" : false,
    "classificationAlgorithm" : "sts",
    "timezone" : "Europe/Moscow",
    "extendedSettings" : {
      "tokenizerEngine" : "mystem",
      "useShared" : false
    },
    "shared" : false
  },
  "intents" : [ {
    "id" : 4793550,
    "path" : "/пока",
    "enabled" : true,
    "shared" : false,
    "phrases" : [ {
      "text" : "пока"
    } ],
    "priority" : 0
  }, {
    "id" : 4793551,
    "path" : "/привет",
    "enabled" : true,
    "shared" : false,
    "phrases" : [ {
      "text" : "привет"
    } ],
    "priority" : 0
  }, {
    "id" : 5200759,
    "path" : "/что ты умеешь",
    "enabled" : true,
    "shared" : false,
    "phrases" : [ {
      "text" : "что ты умеешь?"
    }, {
      "text" : "чо умеешь?"
    }, {
      "text" : "зачем ты нужен?"
    }, {
      "text" : "какой у тебя функционал"
    } ],
    "patterns" : [ ],
    "priority" : 0
  } ],
  "entities" : [ ],
  "enabledSystemEntities" : [ "duckling.number", "duckling.time", "duckling.duration", "duckling.phone-number", "duckling.email", "duckling.url" ],
  "spellerDictionary" : [ ],
  "systemEntities" : [ {
    "name" : "mystem.geo",
    "enabled" : false
  }, {
    "name" : "mystem.persn",
    "enabled" : false
  }, {
    "name" : "mystem.obsc",
    "enabled" : false
  }, {
    "name" : "mystem.patrn",
    "enabled" : false
  }, {
    "name" : "mystem.famn",
    "enabled" : false
  }, {
    "name" : "pymorphy.romn",
    "enabled" : false
  }, {
    "name" : "pymorphy.latn",
    "enabled" : false
  }, {
    "name" : "pymorphy.numb",
    "enabled" : false
  }, {
    "name" : "pymorphy.intg",
    "enabled" : false
  }, {
    "name" : "pymorphy.abbr",
    "enabled" : false
  }, {
    "name" : "pymorphy.name",
    "enabled" : false
  }, {
    "name" : "pymorphy.surn",
    "enabled" : false
  }, {
    "name" : "pymorphy.patr",
    "enabled" : false
  }, {
    "name" : "pymorphy.geox",
    "enabled" : false
  }, {
    "name" : "pymorphy.orgn",
    "enabled" : false
  }, {
    "name" : "duckling.number",
    "version" : "v2",
    "enabled" : true
  }, {
    "name" : "duckling.ordinal",
    "version" : "v2",
    "enabled" : false
  }, {
    "name" : "duckling.amount-of-money",
    "version" : "v2",
    "enabled" : false
  }, {
    "name" : "duckling.distance",
    "version" : "v2",
    "enabled" : false
  }, {
    "name" : "duckling.time",
    "version" : "v2",
    "enabled" : true
  }, {
    "name" : "duckling.date",
    "version" : "v2",
    "enabled" : false
  }, {
    "name" : "duckling.time-of-day",
    "version" : "v2",
    "enabled" : false
  }, {
    "name" : "duckling.duration",
    "version" : "v2",
    "enabled" : true
  }, {
    "name" : "duckling.phone-number",
    "version" : "v2",
    "enabled" : true
  }, {
    "name" : "duckling.email",
    "version" : "v2",
    "enabled" : true
  }, {
    "name" : "duckling.url",
    "version" : "v2",
    "enabled" : true
  }, {
    "name" : "duckling.interval",
    "version" : "v2",
    "enabled" : false
  }, {
    "name" : "mlps-obscene.obscene",
    "enabled" : false
  }, {
    "name" : "zb.datetime",
    "enabled" : false
  }, {
    "name" : "zb.number",
    "enabled" : false
  } ]
}