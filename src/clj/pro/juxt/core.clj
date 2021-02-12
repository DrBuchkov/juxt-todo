(ns pro.juxt.core
  (:require [mount.core :as mount]
            [ring.adapter.jetty :refer [run-jetty]]
            [pro.juxt.http.core :refer [handler]])
  (:gen-class))

(defn -main [& args]
  (mount/start))