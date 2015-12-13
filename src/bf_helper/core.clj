(ns bf-helper.core
  (:gen-class)
  (:require [bf-helper.gen-character :refer :all]
            [bf-helper.formatter :as formatter]
            [clojure.data.generators :as gen]))

  (defn -main []
    (binding [gen/*rnd* (java.util.Random.)]
      (-> (make-character-class :thief)
        (formatter/character)
        println)))
