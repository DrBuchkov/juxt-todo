(ns pro.juxt.http.middleware
  (:require [muuntaja.middleware :as middleware]))


(defn wrap-middleware [handler]
  (-> handler
      middleware/wrap-format))
