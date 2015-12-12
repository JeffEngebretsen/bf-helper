(ns bf-helper.core
  (:gen-class)
  (:require [bf-helper.gen-character :refer :all]
            [bf-helper.formatter :refer :all]
            [clojure.data.generators :as gen]))

  (defn -main []
    (binding [gen/*rnd* (java.util.Random.)]
      (-> (make-character-class :thief)
        (format-character)
        println)))
