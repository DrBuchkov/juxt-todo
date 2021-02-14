(ns pro.juxt.utils
  (:require [clojure.edn :as edn]
            [clojure.walk :refer [walk]])
  (:import (java.util UUID)))

(defn gen-uuid [] (UUID/randomUUID))

(defn str->uuid [s]
  (UUID/fromString s))

(defn query-params->edn-params [query-params]
  (walk (fn [[k v]]
          [(keyword k) (edn/read-string v)])
        identity
        query-params))

(defn remove-ns-from-coll [x]
  (let [remove-ns-from-map (partial clojure.walk/walk (fn [[k v]] [(name k) v])
                                    identity)]
    (cond (map? x) (remove-ns-from-map x)
          (seq? x) (map remove-ns-from-map x)
          :else x)))