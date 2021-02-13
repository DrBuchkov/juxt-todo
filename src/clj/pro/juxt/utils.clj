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

