(ns alexa.routes.create-race-character-router
  (:require [alexa.router :refer :all]
            [bf-helper.character-generator :as c-gen]
            [alexa.util.formatter :as formatter]
            [alexa.util.slot :as slot]
            [clojure.data.generators :as gen]
            [clojure.tools.logging :as log])
  (:import (java.util Random)))

(defmethod route "CreateRaceCharacterIntent"
  [request]
  (binding [gen/*rnd* (Random.)]
    (if-let [race (get-in request [:slots :race])]
      (if-let [character (c-gen/make-character-race (slot/str->keyword race))]
        {:text (format "I rolled up your %s. I sent you a card with the stats."
                       (name (:race character)))
         :card {:title "Your character:"
                :content (formatter/character character)}}
        {:text (format "I'm sorry, I don't know how to make %s %s."
                       (formatter/article race)
                       race)})
      {:text "I'm sorry. I don't know how to do that."})))
