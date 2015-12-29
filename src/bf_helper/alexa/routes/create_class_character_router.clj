(ns bf-helper.alexa.routes.create-class-character-router
  (:require [bf-helper.alexa.router :refer :all]
            [bf-helper.character-generator :as c-gen]
            [bf-helper.alexa.util.formatter :as formatter]
            [bf-helper.alexa.util.slot :as slot]
            [clojure.data.generators :as gen]
            [clojure.tools.logging :as log])
  (:import (com.amazon.speech.ui PlainTextOutputSpeech SimpleCard)
           (com.amazon.speech.speechlet SpeechletResponse)))

(defn- bundle-character
  [c]
  (let [speech (doto (new PlainTextOutputSpeech)
                  (.setText (format "I rolled up Your %s. I sent you a card with the stats." (:name c))))
        card   (doto (new SimpleCard)
                  (.setTitle "Your character:")
                  (.setContent (formatter/character c)))]
    (SpeechletResponse/newTellResponse speech card)))

(defn- error-response
  [clazz]
  (SpeechletResponse/newTellResponse
   (doto (new PlainTextOutputSpeech)
     (.setText
      (format "I'm sorry, I don't know how to make %s %s."
              (formatter/article clazz)
              clazz)))))

(defmethod route "CreateClassCharacterIntent"
  [_ slots]
  (binding [gen/*rnd* (java.util.Random.)]
    (let [clazz (slot/get slots "Class")]
      (if-let [character (c-gen/make-character-class (slot/str->keyword clazz))]
        (bundle-character character)
        (error-response clazz)))))
