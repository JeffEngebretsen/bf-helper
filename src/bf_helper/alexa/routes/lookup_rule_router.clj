(ns bf-helper.alexa.routes.lookup-rule-router
  (:require [bf-helper.alexa.router :refer :all]
            [bf-helper.core :refer [rules]]
            [bf-helper.alexa.util.slot :as slot])
  (:import (com.amazon.speech.ui PlainTextOutputSpeech SimpleCard)
           (com.amazon.speech.speechlet SpeechletResponse)))

(defmethod route "LookupRuleIntent"
  [_ slots]
  (if-let [rule-key (slot/get slots "Rule")]
    (if-let [rule (rules (slot/str->keyword rule-key))]
      (SpeechletResponse/newTellResponse
       (doto (PlainTextOutputSpeech.)
         (.setText (format "%s ... %s" (:name rule) (:description rule))))
         (doto (SimpleCard.)
           (.setTitle (:name rule))
           (.setContent (:description rule))))
      (SpeechletResponse/newTellResponse
       (doto (new PlainTextOutputSpeech)
         (.setText (format "I'm still learning this gaming system. Try asking about %s again later."
                           rule-key)))))
    (SpeechletResponse/newTellResponse
       (doto (new PlainTextOutputSpeech)
         (.setText "I'm sorry. I don't know how to do that.")))))
