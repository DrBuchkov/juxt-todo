(ns pro.juxt.todo.ajax
  (:require [re-frame.core :as rf]
            [ajax.core :as ajax]
            [ajax.edn :refer [edn-request-format edn-response-format]]
            [pro.juxt.config :as config]
            [pro.juxt.utils :as utils]))

;; Create
(rf/reg-event-fx
  :todo/create
  (fn [_ [_ val]]
    {:http-xhrio {:method          :post
                  :uri             (str (config/uri) "/todo")
                  :params          val
                  :headers         {"Authorization" "Bearer secret"}
                  :timeout         5000
                  :format          (edn-request-format)
                  :response-format (edn-response-format)
                  :on-success      [:todo/create-success]
                  :on-failure      [:todo/create-fail]}}))

(rf/reg-event-db
  :todo/create-success
  (fn [db [_ result]]
    (println "Successfully created TODO")
    (utils/redirect-page! "#/todo/list")
    db))

(rf/reg-event-db
  :todo/create-fail
  (fn [db [_ result]]
    (println "Failed to create TODO")
    db))

;; Browse

(rf/reg-event-fx
  :todo/browse
  (fn [_ [_ val]]
    {:http-xhrio {:method          :get
                  :uri             (str (config/uri) "/todo")
                  :headers         {"Authorization" "Bearer secret"}
                  :timeout         5000
                  :format          (edn-request-format)
                  :response-format (edn-response-format)
                  :on-success      [:todo/browse-success]
                  :on-failure      [:todo/browse-fail]}}))

(rf/reg-event-db
  :todo/browse-success
  (fn [db [_ data]]
    (assoc db :todos data)))

(rf/reg-event-db
  :todo/browse-fail
  (fn [db [_ result]]
    (println "Failed to fetch TODOs")
    db))

(rf/reg-sub
  :todos
  (fn [db _]
    (-> db :todos)))

(rf/reg-event-fx
  :todo/delete
  (fn [_ [_ val]]
    {:http-xhrio {:method          :delete
                  :uri             (str (config/uri) "/todo/" val)
                  :headers         {"Authorization" "Bearer secret"}
                  :timeout         5000
                  :format          (edn-request-format)
                  :response-format (edn-response-format)
                  :on-success      [:todo/delete-success]
                  :on-failure      [:todo/delete-fail]}}))

(rf/reg-event-db
  :todo/delete-success
  (fn [db [_ data]]
    (rf/dispatch [:todo/browse])
    db))

(rf/reg-event-db
  :todo/delete-fail
  (fn [db [_ result]]
    (println "Failed to fetch TODOs")
    db))