(ns bf-helper.core
  (:gen-class)
  (:require [clojure.java.io :as io]
            [clojure.pprint :refer :all]
            [clojure.data.generators :as gen]
            [bf-helper.character-generator :as c-gen]
            [bf-helper.data :refer :all]))

(set! *warn-on-reflection* true)

(defrecord BfSpell [name range duration magic-user-level cleric-level description inverse])
(defrecord BfRule [name description])

(def spells (load-res "spells/" map->BfSpell))
(def rules (load-res "rules/" map->BfRule))

(defn -main []
  (binding [gen/*rnd* (java.util.Random.)]
    (-> (c-gen/make-character)
        pprint)))
