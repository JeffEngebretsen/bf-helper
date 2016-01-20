(ns alexa.router-test
  (:require [clojure.test :refer :all]
            [alexa.router :as r]
            [alexa.speechlet]))

(def not-nil? (complement nil?))

(deftest can-route-intents
  (are [x] (let [response (r/route x)]
             (and (contains? response :text)
                  (contains? response :card)))
           {:intent "CreateRaceCharacterIntent"
            :slots {:race "halfling"}}
           {:intent "CreateClassCharacterIntent"
            :slots {:class "thief"}}
           {:intent "CreateCharacterIntent"
            :slots {:character "character"}}
           {:intent "LookupRuleIntent"
            :slots {:rule "attack"}}
           {:intent "LookupSpellIntent"
            :slots {:spell "light"}}))

(deftest can-handle-bad-slot
  (is (let [response (r/route {:intent "CreateRaceCharacterIntent"
                               :slots {:race "bad race"}})]
        (and (contains? response :text)
             (not (contains? response :card))))))
