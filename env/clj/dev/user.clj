(ns user
  (:require [clojure.tools.namespace.repl :as tn]
            [pro.juxt.core :refer [start-app! stop-app!]]
            [pro.juxt.http.core :as http]
            [pro.juxt.db.core :as db]
            [pro.juxt.seed :refer [seed-db!]]
            [clojure.tools.logging :as log]))

(defn refresh-ns
  "Refresh/reloads all the namespace"
  []
  (tn/refresh-all))

(defn start
  "Mount starts life cycle of runtime state"
  []
  (start-app!))

(defn stop
  "Mount stops life cycle of runtime state"
  []
  (stop-app!))

(defn restart-dev
  []
  (stop)
  (refresh-ns)
  (start))