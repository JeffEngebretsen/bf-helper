(ns alexa.routes.amazon-router
  (:require [alexa.router :refer :all])
  (:import (com.amazon.speech.ui PlainTextOutputSpeech Reprompt)
           (com.amazon.speech.speechlet SpeechletResponse)))

(defmethod route "AMAZON.HelpIntent"
  [_]
  {:text (str "BF Helper is a companion skill for Basic Fantasy RGP. "
              "You can ask about spells and rules or roll a new character. "
              "You can say, tell me about Magic Mouth, or, roll an Elf. "
              "What can I do for you?")
   :reprompt (str "You can say things like, explain charging, or you can say exit... Now, what can I do for you?")})

(defmethod route "AMAZON.CancelIntent"
  [_]
  {:text "May all your dice rolls be critical hits."})

(defmethod route "AMAZON.StopIntent"
  [_]
  {:text "May all your dice rolls be critical hits."})
