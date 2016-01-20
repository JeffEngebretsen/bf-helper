(ns alexa.routes.create-character-router
  (:require [alexa.router :refer :all]
            [bf-helper.character-generator :as c-gen]
            [alexa.util.formatter :as formatter]
            [alexa.util.slot :as slot]
            [clojure.data.generators :as gen])
  (:import (java.util Random)))

(defn- make-character []
  (binding [gen/*rnd* (Random.)]
        (let [character (c-gen/make-character)]
          {:text (format "I rolled up Your %s. I sent you a card with the stats."
                         (formatter/character-race-class character))
           :card {:title "Your character:"
                  :content (formatter/character character)}})))

(defmethod route "CreateCharacterIntent"
  [request]
  (let [character (get-in request [:slots :character])]
    (if (= character "character")
      (make-character)
      {:text (format "I'm sorry, I don't know how to make %s %s."
              (formatter/article character)
              character)})))
