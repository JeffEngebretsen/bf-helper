(ns bf-helper.data
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]
            [clojure.tools.logging :as log]))

(defn load-res
  [path map->record]
  (log/debug (str "Loading all files from " path))
  (into {} (map  #(let [record (-> (str path (.getName %))
                                  io/resource
                                  slurp
                                  edn/read-string)]
                    (log/debug (str "Loading " (:name (second record))))
                    [(first record) (map->record (second record))])
                 (filter #(.isFile %)
                         (file-seq (io/file (io/resource path)))))))
