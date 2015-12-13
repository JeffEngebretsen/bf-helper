(ns bf-helper.core-test
  (:require [clojure.test :refer :all]
            [bf-helper.core :refer :all]))

(run-all-tests)

;(map #(ns-unmap *ns* %) (keys (ns-interns *ns*)))
