(ns bf-helper.alexa.routes.lookup-rule-router
  (:require [bf-helper.alexa.router :refer :all]
            [bf-helper.alexa.util.slot :as slot])
  (:import (com.amazon.speech.ui PlainTextOutputSpeech)
           (com.amazon.speech.speechlet SpeechletResponse)))

(defmethod route "LookupRuleIntent"
  [_ slots]
  (SpeechletResponse/newTellResponse
   (doto (new PlainTextOutputSpeech)
     (.setText (format "I'm still learning this gaming system. Try asking about %s again later."
                       (slot/get slots "Rule"))))))
