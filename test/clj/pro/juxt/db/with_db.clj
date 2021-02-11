(ns pro.juxt.db.with-db
  (:require [crux.api :as crux]))

(def ^:dynamic *node* nil)

(defn fresh-db [] (crux/start-node {}))

(defn with-db [f]
  (binding [*node* (fresh-db)]
    (f)))
