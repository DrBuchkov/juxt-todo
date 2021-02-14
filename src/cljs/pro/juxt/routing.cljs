(ns pro.juxt.routing
  (:require [re-frame.core :as rf]
            [reitit.frontend.controllers :as rfc]
            [reitit.frontend.easy :as rfe]))


(rf/reg-event-db
  :nav/navigate
  (fn [db [_ match]]
    (let [old-match (:nav/route db)
          new-match (assoc match :controllers
                                 (rfc/apply-controllers (:controllers old-match) match))]
      (assoc db :nav/route new-match))))

(rf/reg-event-fx
  :nav/navigate!
  (fn [_ [_ url-key params query]]
    {:nav/navigate-fx! [url-key params query]}))

(rf/reg-fx
  :nav/navigate-fx!
  (fn [[k & [params query]]]
    (rfe/push-state k params query)))

(rf/reg-sub
  :nav/route
  (fn [db _]
    (-> db :nav/route)))

(rf/reg-sub
  :nav/page
  :<- [:nav/route]
  (fn [route _]
    (-> route :data :view)))
