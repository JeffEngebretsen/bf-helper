(ns bf-helper.alexa.routes.amazon-router
  (:require [bf-helper.alexa.router :refer :all])
  (:import (com.amazon.speech.ui PlainTextOutputSpeech Reprompt)
           (com.amazon.speech.speechlet SpeechletResponse)))

(defmethod route "AMAZON.HelpIntent"
  [_ _]
  (SpeechletResponse/newAskResponse
   (doto (PlainTextOutputSpeech.)
     (.setText (str "You can ask about spells and rules or roll a new character. "
                    "You can say, tell me about Magic Mouth, or, roll an Elf.")))
   (doto (Reprompt.)
     (.setOutputSpeech
      (doto (PlainTextOutputSpeech.)
        (.setText (str "You can say things like, explain charging, or you can say exit... Now, what can I do for you?")))))))

(defmethod route "AMAZON.CancelIntent"
  [_ _]
  (SpeechletResponse/newTellResponse
   (doto (new PlainTextOutputSpeech)
     (.setText "Goodbye"))))

(defmethod route "AMAZON.StopIntent"
  [_ _]
  (SpeechletResponse/newTellResponse
   (doto (new PlainTextOutputSpeech)
     (.setText "Goodbye"))))
