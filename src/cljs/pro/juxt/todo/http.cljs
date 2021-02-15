(ns pro.juxt.todo.http
  (:require [re-frame.core :as rf]
            [ajax.core :as ajax]
            [ajax.edn :refer [edn-request-format edn-response-format]]
            [pro.juxt.config :as config]
            [malli.edn :as edn]))


(rf/reg-event-fx
  ::http-post
  (fn [_ [_ val]]
    (println "Hello World")
    {:http-xhrio {:method          :post
                  :uri             (str (config/uri) "/todo")
                  :params          val
                  :headers         {"Authorization" "Bearer secret"}
                  :timeout         5000
                  :format          (edn-request-format)
                  :response-format (edn-response-format)
                  :on-success      [::success-post-result]
                  :on-failure      [::failure-post-result]}}))

(rf/reg-event-db
  ::success-post-result
  (fn [db [_ result]]
    (println "Success")
    (println result)
    db))

(rf/reg-event-db
  ::failure-post-result
  (fn [db [_ result]]
    (println "Failed")
    (println result)
    db))
