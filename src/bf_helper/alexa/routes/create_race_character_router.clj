(ns bf-helper.alexa.routes.create-race-character-router
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
  [race]
  (SpeechletResponse/newTellResponse
   (doto (new PlainTextOutputSpeech)
     (.setText
      (format "I'm sorry, I don't know how to make %s %s."
              (formatter/article race)
              race)))))

(defmethod route "CreateRaceCharacterIntent"
  [_ slots]
  (binding [gen/*rnd* (java.util.Random.)]
    (let [race (slot/get slots "Race")]
      (if-let [character (c-gen/make-character-race (slot/str->keyword race))]
        (bundle-character character)
        (error-response race)))))
